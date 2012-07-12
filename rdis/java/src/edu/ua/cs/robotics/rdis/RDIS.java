package edu.ua.cs.robotics.rdis;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.json.JSONException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;


/**
 * RDIS model interpreter for Java.
 */
public class RDIS {

	/** Callback interface for triggered Domain Outputs */
	private RDIS.Callback mCallback;

	/** Maps model component names to the model component objects. */
	private HashMap 
			mPrimitives = new HashMap(),
			mConnections = new HashMap(),
			mInterfaces = new HashMap(),
			mStateVariables = new HashMap(),
			mDomainOutputs = new HashMap(),
			mDomainInterfaces = new HashMap();

	/** Name of the model. */
	private String mName;

	/** Name of the modeler. */
	private String mAuthor;
	
	/** Logging handler. */
	// Default behavior: output warnings and above to stderr.
	private Log mLog = new Log(Log.WARN) {
		public void onMessage(int msgLevel, String code, String msg) {
			Timestamp stmp = new Timestamp(System.currentTimeMillis());
			System.err.println(stmp + " " + code + " (" + msgLevel + "): " + msg);
		}
	};

	/**
	 * RDIS cannot be instantiated directly. Use RDIS.load(String).
	 */
	protected RDIS() { }

	/**
	 * Registers a callback to handle messages from Domain Outputs.
	 * 
	 * @param callback the callback
	 */
	public void setCallback(RDIS.Callback callback) {
		mCallback = callback;
	}
	
	/**
	 * Sets the log level of the current logging handler.
	 * @param logLevel new log level
	 */
	public void setLogLevel(int logLevel) {
		mLog.setLogLevel(logLevel);
	}
	
	/**
	 * Returns the author of this description.
	 * @return name of author
	 */
	public String getAuthor() {
		return mAuthor;
	}
	
	/**
	 * Returns the name of this model.
	 * @return name of model
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * Gets the Log object.
	 * @return a log object
	 */
	protected Log getLog() {
		return mLog;
	}

	/**
	 * Calls a Domain Interface for this model.
	 * 
	 * Domain Interfaces are the standard interfaces by which a framework can
	 * deliver a message to the robot. The model will handle the contruction and
	 * routing of the message to the connection.
	 * 
	 * @param name the name of the Domain Interface to call
	 * @param domainAdapter the message to send
	 * @throws RDISException if there was a problem communicating with the robot.
	 */
	public void callDomainInterface(String name, DomainAdapter adapter) throws RDISException {
		DomainInterface domainInterface = (DomainInterface) mDomainInterfaces.get(name);
		if (domainInterface == null){
			mLog.error("Called nonexistant domain interface.");
			//TODO: Error handling
			return;
		}
		domainInterface.call(adapter);
	}
	
	/**
	 * Calls a {@link Primitive}.
	 * 
	 * @param name the name of the Primitive to call
	 * @param arguments the arguments to give the Primitive
	 * @throws RDISException if there was a communication problem
	 */
	protected void callPrimitive(String name, PyObject arguments[]) throws RDISException {
		Primitive p = (Primitive) mPrimitives.get(name);
		if (p == null){
			mLog.error("Called nonexistent primitive: `" + name + "`");
			return;
		}
		
		int argCount = (arguments == null) ? 0 : arguments.length;
		
		// Verifies Lists[] are parallel (same size)
		if(p.parameterCount() != argCount){
			throw new RDISException("Argument length mismatch for Primitive.");
		}
		
		p.call(arguments);
	}

	
	/**
	 * Calls a {@link DomainOutput}.
	 * @param name the name of the Domain Output to call
	 */
	protected void callDomainOutput(String name) {
		DomainOutput domainOutput = getDomainOutput(name);
		if(domainOutput == null){
			mLog.error("Called nonexistant domain output: `" + name + "`.");
			//TODO: Handle Error
			return;
		}
		domainOutput.call();
	}

	/**
	 * Calls an {@link Interface}.
	 * @param interfaceName the name of the Interface to call
	 * @param arguments arguments for the call
	 * @throws RDISException if there was a communication error
	 */
	protected void callInterface(String interfaceName, PyObject[] arguments) throws RDISException {
		Interface i = (Interface) mInterfaces.get(interfaceName);
		if (i == null){
			mLog.error("Called nonexistant local interface.");
			//TODO: Handle Error
			return;
		}	
		i.call(arguments);
	}
	
	/**
	 * Called on startup.
	 * 
	 * @param portName
	 *            the port name to use for the connection, e.g., /dev/rfcomm0
	 * @throws RDISException 
	 */
	public void startup(String portName) throws RDISException {
		Collection values = mConnections.values();
		
		for(Iterator it = values.iterator(); it.hasNext();) {
			Connection connection = (Connection) it.next();
			connection.startup(portName);
		}
	}
	
	/**
	 * Overrides a {@link Connection}'s subclass read/write behavior with a pair
	 * of Input/OutputStreams.
	 * @param name the name of the Connection
	 * @param is the InputStream to use
	 * @param os the OutputStream to use
	 */
	public void overrideConnection(String name, InputStream is, OutputStream os) {
		Connection c = (Connection) mConnections.get(name);
		
		if(c == null) {
			mLog.warn("No such connection: `" + name + "`");
			return;
		}
		
		c.override(is,os);
	}

	/**
	 * Periodically called by client code. Triggers keepalive Interfaces, etc.
	 * @throws RDISException 
	 */
	public void tick() throws RDISException {
		Collection values = mConnections.values();
		
		for(Iterator it = values.iterator(); it.hasNext();) {
			Connection connection = (Connection) it.next();
			connection.tick();
		}
	}

	/**
	 * Called by client code to destruct the model. Triggers terminate
	 * Interfaces, etc.
	 * @throws RDISException 
	 */
	public void terminate() throws RDISException {
		Collection values = mConnections.values();
		for(Iterator it = values.iterator(); it.hasNext();) {
			Connection connection = (Connection) it.next();
			connection.terminate();
		}
	}
	
	/**
	 * Grabs variables in env which share names with State Variables and tries
	 * to set the State Variable's value.
	 * @param env the Python environment to update from
	 */
	protected void updateStateVariablesFrom(PythonInterpreter env) {
		Collection svs = mStateVariables.values();
		Iterator it = svs.iterator();
		
		while(it.hasNext()) {
			StateVariable sv = (StateVariable) it.next();
			Object value = env.get(sv.getName(), Object.class);
			
			if(value != null) {
				sv.setValue(value);
			}
		}
	}

	/**
	 * Gets a Python environment where all the state variables are in scope.
	 * @return a Python environment
	 */
	protected PythonInterpreter getPythonInterpreter() {
		PythonInterpreter p = new PythonInterpreter();
		
		Collection svs = mStateVariables.values();
		Iterator it = svs.iterator();
		
		// Add all state variables to Python environment.
		while(it.hasNext()) {
			StateVariable sv = (StateVariable) it.next();
			p.set(sv.getName(), sv.getValue());
		}
		
		return p;
	}
	
	/**
	 * Creates and returns a Python environment with the State Variables in scope, as
	 * well as some local variables.
	 * 
	 * 'parameters' and 'arguments' are expected to be parallel lists. The local variables are
	 * then each of the names in parameters, bound to the corresponding value in arguments.
	 * 
	 * @param parameters formal parameter names
	 * @param arguments values for the formal parameters
	 * @return a Python environment with State Variables and local variables in scope.
	 */
	protected PythonInterpreter createEnvironment(String[] parameters, PyObject pyArguments[]){
		PythonInterpreter p = getPythonInterpreter(); 
		for (int i=0; i < parameters.length; i++){
			p.set(parameters[i], pyArguments[i]);			
		}
		return p;
	}

	/**
	 * Adds a StateVariable to the model.
	 * @param sv the state variable to add
	 */
	public void addStateVariable(StateVariable sv) {
		mStateVariables.put(sv.getName(), sv);
	}
	
	/**
	 * Adds a Domain Output to the model.
	 * @param domOutput the domain output to add
	 */
	public void addDomainOutput(DomainOutput domOutput) {
		mDomainOutputs.put(domOutput.getName(), domOutput);
	}
	
	/**
	 * Adds a {@link DomainInterface} to the model.
	 * @param domInterface the Domain Interface to add.
	 */
	public void addDomainInterface(DomainInterface domInterface) {
		mDomainInterfaces.put(domInterface.getName(), domInterface);
	}
	
	/**
	 * Adds an {@link Interface} to the model.
	 * @param iface the Interface to add
	 */
	public void addInterface(Interface iface) {
		mInterfaces.put(iface.getName(), iface);
	}

	/**
	 * Adds a {@link Primitive} to the model.
	 * @param p
	 */
	public void addPrimitive(Primitive p) {
		mPrimitives.put(p.getName(), p);
	}
	
	/**
	 * Adds a {@link Connection} to the model.
	 * @param connection the connection to add
	 */
	public void addConnection(Connection connection) {
		mConnections.put(connection.getName(), connection);
	}
	
	/**
	 * Loads an RDIS object from an RDIS description.
	 * 
	 * @param rdisFile path to file containing the RDIS description
	 * @return the loaded RDIS model
	 * @throws JSONException 
	 * @throws FileNotFoundException 
	 */
	public static RDIS load(String rdisFile) throws FileNotFoundException, JSONException {
		return (new  RDISDeserializer()).deserialize(rdisFile);
	}
	
	/**
	 * Interface the client must implement to handle messages coming from
	 * triggered Domain Outputs.
	 */
	public static interface Callback {
		/**
		 * Called in client code when a Domain Output is triggered.
		 * 
		 * @param name
		 *            the name of triggered Domain Output
		 * @param contents
		 *            the message produced by the Domain Output
		 */
		public void onMessageReceived(String name, DomainAdapter contents);
	}

	/**
	 * Returns a connection by name.
	 * @param name the name of the connection
	 * @return the Connection object associated with name
	 */
	protected Connection getConnection(String name) {
		return (Connection) mConnections.get(name);
	}
	
	/**
	 * Returns a domain output by name.
	 * @param name the name of the domain output
	 * @return the domain output associated with that name
	 */
	protected DomainOutput getDomainOutput(String name) {
		return (DomainOutput) mDomainOutputs.get(name);
	}

	/**
	 * Evaluates expression as a Python expression if the first and last character are '<' and '>'.
	 * Otherwise, interprets expression as a Python string.
	 * @param expression the expression to possibly evaluate
	 * @param env a Python environment
	 * @return the result of the evaluation
	 */
	public static PyObject safeEval(String expression, PythonInterpreter env){
		if (expression.length() == 0){
			return new PyString(expression);
		}
		
		// By default return 'expression' as a Python string if it shouldn't be evaluated.
		PyObject returnValue = new PyString(expression);
		
		// Check if expression begins and ends with '<' and '>'. If so, evaluate it as Python.
		if (expression.charAt(0) == '<' && expression.charAt( expression.length() - 1 ) == '>'){
			String subExpr = expression.substring(1, expression.length() - 1 );
			returnValue = env.eval( subExpr );
		}
		
		return returnValue;
	}
	
	/**
	 * Calls {@link #safeEval(String, PythonInterpreter)} on a set of Python expressions
	 * and returns the result as an array. 
	 * @param expressions the Python expressions
	 * @param env the PythonInterpreter to use
	 * @return an array of the expressions
	 */
	public static PyObject[] safeEvalAll(String[] expressions, PythonInterpreter env) {
		
		if (expressions != null) {
			PyObject pyObjectList[] = new PyObject[expressions.length];
			for (int i = 0; i < expressions.length; i++) {
				String expression = expressions[i];
				PyObject pyArg = RDIS.safeEval(expression, env);
				pyObjectList[i] = pyArg;
		
			}			
			return pyObjectList;
		}
		// Return  null  when no expressions exist to be evaluated.
		return null;
	}
	
	/**
	 * Delivers a message to the registered {@link RDIS.Callback}.
	 * 
	 * @param name the issuing {@link DomainOutput}
	 * @param adapter the outgoing {@link DomainAdapter}
	 */
	public void deliverMessage(String name, DomainAdapter adapter) {
		if(mCallback != null) {
			mCallback.onMessageReceived(name, adapter);
		}
		else {
			mLog.warn("Message received from `" + name + "` but no callback is set.");
		}
	}
	
	public static final String
		APP_NAME = "RDIS";
}
