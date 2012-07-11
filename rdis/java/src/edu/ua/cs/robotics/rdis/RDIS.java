package edu.ua.cs.robotics.rdis;


import java.io.FileNotFoundException;
import java.io.IOException;
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

	// PMK: I think Java 1.3 does not support generics. We should avoid it.
	private HashMap 
			mPrimitives = new HashMap(),
			mConnections = new HashMap(),
			mInterfaces = new HashMap(),
			mStateVariables = new HashMap(),
			mDomainOutputs = new HashMap(),
			mDomainInterfaces = new HashMap();

	private String mName;

	private String mAuthor;
	
	private Log mLog = new Log(Log.WARN) {
		public void onMessage(int msgLevel, String code, String msg) {
			Timestamp stmp = new Timestamp(System.currentTimeMillis());
			System.err.println(stmp + " " + code + " (" + msgLevel + "): " + msg);
		}
	};

	/**
	 * RDIS cannot be instantiated directly. Use RDIS.load(String).
	 */
	protected RDIS() {
		// TODO: We'll write something later that actually constructs this from the parsed JSON
	}

	/**
	 * Registers a callback to handle messages
	 * 
	 * @param callback
	 */
	public void setCallback(RDIS.Callback callback) {
		mCallback = callback;
	}
	
	public void setLogLevel(int logLevel) {
		mLog.setLogLevel(logLevel);
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
	 * @param name
	 *            the name of the Domain Interface to call
	 * @param domainAdapter
	 * @throws IOException 
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
	
	protected Primitive getPrimitive(String name) {
		return (Primitive) mPrimitives.get(name);
	}
	
	protected void callPrimitive(String name, PyObject pyPosArg[]) throws RDISException {
		Primitive p = getPrimitive(name);
		if (p == null){
			mLog.error("Called nonexistent primitive: `" + name + "`");
			return;
		}
		
		int argCount = (pyPosArg == null) ? 0 : pyPosArg.length;
		
		// Verifies Lists[] are parallel (same size)
		if(p.parameterCount() != argCount){
			mLog.error("Argument length mismatch for Primitive.");
			return;
		}
		
		p.call(pyPosArg);
	}

	protected void callDomainOutput(String name) {
		DomainOutput domainOutput = getDomainOutput(name);
		if(domainOutput == null){
			mLog.error("Called nonexistant domain output: `" + name + "`.");
			//TODO: Handle Error
			return;
		}
		domainOutput.call();
	}

	protected void callInterface(String interfaceName, PyObject[] posArgs) throws RDISException {
		Interface i = (Interface) mInterfaces.get(interfaceName);
		if (i == null){
			mLog.error("Called nonexistant local interface.");
			//TODO: Handle Error
			return;
		}	
		i.call(posArgs);
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
			connection.keepalive();
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
	
	public void addDomainInterface(DomainInterface domInterface) {
		mDomainInterfaces.put(domInterface.getName(), domInterface);
	}
	
	public void addInterface(Interface iface) {
		mInterfaces.put(iface.getName(), iface);
	}
	
	public void addPrimitive(Primitive p) {
		mPrimitives.put(p.getName(), p);
	}
	
	/**
	 * Adds a Connection to the model.
	 * @param connection the connection to add
	 */
	public void addConnection(Connection connection) {
		mConnections.put(connection.getName(), connection);
	}
	
	/**
	 * Loads an RDIS object from an RDIS description.
	 * 
	 * @param rdisFile
	 *            path to file containing the RDIS description
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
	
	public void deliverMessage(String name, DomainAdapter adapter) {
		if(mCallback != null) {
			mCallback.onMessageReceived(name, adapter);
		}
		else {
			mLog.warn("Message received from `" + name + "` but no callback is set.");
		}
	}
}
