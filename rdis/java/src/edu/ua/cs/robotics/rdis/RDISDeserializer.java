package edu.ua.cs.robotics.rdis;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.python.core.PyInteger;
import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class RDISDeserializer {

	public RDIS deserialize(String filename) throws FileNotFoundException, JSONException {
		RDIS rdis = new RDIS();

		FileInputStream is = new FileInputStream(new File(filename));

		JSONObject jsonObject = new JSONObject(new JSONTokener(is));
		
		addStateVariables(jsonObject, rdis);
		addInterfaces(jsonObject, rdis);
		addPrimitives(jsonObject, rdis);
		addConnections(jsonObject, rdis);
		addDomainInterfaces(jsonObject, rdis);
		addDomainOutputs(jsonObject, rdis);

		return rdis;
	}
	
	private void addConnections(JSONObject jsonObject, RDIS rdis) throws JSONException {
		JSONObject conObj = jsonObject.getJSONObject(CONNECTIONS); 
		addSerialConnections(conObj, rdis);
	}

	private void addSerialConnections(JSONObject jsonObject, RDIS rdis) throws JSONException {
		JSONArray serialConnections = jsonObject.getJSONArray(SERIAL_CONNECTIONS);
		
		for(int i = 0; i < serialConnections.length(); i++) {
			addSerialConnection(serialConnections.getJSONObject(i), rdis); 
		}
	}

	private void addSerialConnection(JSONObject jsonObject, RDIS rdis) throws JSONException {
		String name = jsonObject.getString(NAME);
		int baud = jsonObject.getInt(BAUD);
		Call startup = toCallObject(jsonObject.optJSONObject(STARTUP));
		Call keepalive = toCallObject(jsonObject.optJSONObject(KEEPALIVE));
		Call terminate = toCallObject(jsonObject.optJSONObject(TERMINATE));
		
		SerialConnection sc = new SerialConnection(rdis, name, baud);
		
		sc.setStartup(startup);
		sc.setKeepalive(keepalive);
		sc.setTerminate(terminate);
		
		rdis.addConnection(sc);
	}

	private void addDomainOutputs(JSONObject jsonObject, RDIS rdis) throws JSONException {
		JSONArray domainOutput = jsonObject.getJSONArray(DOMAIN_OUTPUTS);

		for(int i = 0; i < domainOutput.length(); i++) {
			addDomainOutput(domainOutput.getJSONObject(i), rdis);
		}
	}
	
	private void addDomainOutput(JSONObject jsonObject, RDIS rdis) throws JSONException {
		String name = jsonObject.getString(NAME);
		JSONObject jReturns = jsonObject.getJSONObject(RETURNS);
		
		Iterator it = jReturns.keys();
		DomainOutput domOutput = new DomainOutput(rdis,name);
							
		while(it.hasNext()) {
			String key = (String) it.next();
			domOutput.addReturns(key, jReturns.getString(key));
		}
		
		rdis.addDomainOutput(domOutput);
	}

	private void addPrimitives(JSONObject jsonObject, RDIS rdis) throws JSONException {
		JSONArray primitives = jsonObject.getJSONArray(PRIMITIVES);
		
		for(int i = 0; i < primitives.length(); i++) {
			JSONObject jPrimitive = primitives.getJSONObject(i);
			addPrimitive(jPrimitive, rdis);
		}
	}

	private void addPrimitive(JSONObject jPrimitive, RDIS rdis) throws JSONException {
		String name = jPrimitive.getString(NAME);
		String parameters[] = toStringArray(jPrimitive.optJSONArray(PARAMETERS));
		String formatArgs[] = toStringArray(jPrimitive.getJSONArray(FORMAT_ARGS));
		String postActions[] = toStringArray(jPrimitive.optJSONArray(POST_ACTIONS));
		String connection = jPrimitive.getString(CONNECTION);
		
		if(jPrimitive.has(PACK)) {
			rdis.addPrimitive(Primitive.newFixedWidth(
					rdis,
					name,
					parameters,
					connection,
					formatArgs,
					postActions,
					jPrimitive.getString(PACK),
					jPrimitive.optString(UNPACK)
				)
			);
		}
		else if(jPrimitive.has(FORMAT)) {
			rdis.addPrimitive(Primitive.newAsciiEncoded(
					rdis,
					name,
					parameters,
					connection,
					formatArgs,
					postActions,
					jPrimitive.getString(FORMAT),
					jPrimitive.optString(REGEX)
				)
			);
		}
		else {
			throw new JSONException("Primitive `" + name + "` must have attribute `pack` or `format`.");
		}
	}
	

	/**
	 * Adds all Interfaces present in the description.
	 * @param jsonObject the object to construct from
	 * @param rdis the model to build
	 * @throws JSONException if there's an error in the syntax
	 */
	private void addInterfaces(JSONObject jsonObject, RDIS rdis) throws JSONException {
		JSONArray interfaces = jsonObject.getJSONArray(INTERFACES);
		
		for(int i = 0; i < interfaces.length(); i++) {
			addInterface(interfaces.getJSONObject(i), rdis);
		}
	}
	
	/**
	 * Adds all Domain Interfaces present in the description.
	 * TODO: Documentation
	 * @param jsonObject
	 * @param rdis
	 * @throws JSONException
	 */
	private void addDomainInterfaces(JSONObject jsonObject, RDIS rdis) throws JSONException {
		JSONArray domainInterfaces = jsonObject.getJSONArray(DOMAIN_INTERFACES);
		
		for(int i = 0; i < domainInterfaces.length(); i++) {
			addDomainInterface(domainInterfaces.getJSONObject(i), rdis);
		}
	}

	/**
	 * Adds a single Interface to the model.
	 * @param jsonObject the interface expressed in JSON
	 * @param rdis the model to build
	 * @throws JSONException if there's an error in the syntax
	 */
	private void addInterface(JSONObject jsonObject, RDIS rdis) throws JSONException {
		// Preliminary grab all the attributes from the JSON.
		String name = jsonObject.getString(NAME);
		String type = jsonObject.getString(TYPE);
		String triggers = jsonObject.optString(TRIGGERS);
		JSONArray primitiveCalls = jsonObject.getJSONArray(PRIMITIVE_CALLS);
		String parameters[] = toStringArray(jsonObject.optJSONArray(PARAMETERS));
		Call calls[] = toCallArray(primitiveCalls);
		
		rdis.addInterface(new Interface(rdis, name, type, parameters, triggers, calls));
	}

	private void addDomainInterface(JSONObject jsonObject, RDIS rdis) throws JSONException {
		// Preliminary grab all the attributes from the JSON.
		String name = jsonObject.getString(NAME);
		Call calls = toCallObject(jsonObject.getJSONObject(CALLS));
		JSONArray domainInterfaceParameters = jsonObject.optJSONArray(PARAMETERS);

		// Constructing the parameters array.
		String parameters[] = toStringArray(domainInterfaceParameters);
		
		rdis.addDomainInterface(new DomainInterface(rdis, name, calls, parameters));
	}
	
	/**
	 * Constructs an array of Strings from a JSONArray.
	 * @param jArray the JSON array
	 * @return an array of strings
	 * @throws JSONException if one of the elements was not a string
	 */
	private String[] toStringArray(JSONArray jArray) throws JSONException {
		if(jArray == null) return new String[0];
		
		String strings[] = new String[jArray.length()];
		for(int i = 0; i < strings.length; i++) {
			strings[i] = jArray.getString(i);
		}
		
		return strings;
	}
	
	/**
	 * Constructs an array of Call objects from a JSON Array.
	 * @param jsonCalls the JSON array
	 * @return an array of Call objects
	 * @throws JSONException on syntax error
	 */
	private Call[] toCallArray(JSONArray jsonCalls) throws JSONException {
		Call calls[] = new Call[jsonCalls.length()];
		
		// Constructing the calls array.
		for(int i = 0; i < calls.length; i++) {
			JSONObject jsonCall = jsonCalls.getJSONObject(i);
			calls[i] = toCallObject(jsonCall);
		}
		
		Arrays.sort(calls);
		
		return calls;
	}
	
	/**
	 * Constructs a Call object from the JSON call object.
	 * @param jsonCall the JSON call object
	 * @return a Call object represented by the JSON
	 * @throws JSONException if there was a syntax error
	 */
	private Call toCallObject(JSONObject jsonCall) throws JSONException {
		if(jsonCall == null) return null;
		String name = jsonCall.getString(NAME);
		int delay = 0;
		
		if(jsonCall.has(DELAY)) {
			delay = jsonCall.getInt(DELAY);
		}
		else if(jsonCall.has(INTERVAL)) {
			delay = jsonCall.getInt(INTERVAL);
		}
		
		int priority = jsonCall.optInt(PRIORITY, 0);
		String arguments[] = toStringArray(jsonCall.optJSONArray(ARGUMENTS));
		return new Call(name, arguments, delay, priority);
	}

	/**
	 * Calls addStateVariable() for each State Variable present in model.
	 * 
	 * @param obj
	 *            Json root object
	 * @param rdis
	 *            RDIS object to construct
	 * @throws JSONException
	 */
	private void addStateVariables(JSONObject obj, RDIS rdis) throws JSONException {
		// State Variables are stored as an array.
		JSONArray stateVars = obj.getJSONArray(STATE_VARIABLES);
		
		// Iterate over the JSONArray and call addStateVariable()
		for (int i = 0; i < stateVars.length(); i++) {
			addStateVariable(stateVars.getJSONObject(i), rdis);
		}
	}

	/**
	 * Adds a single State Variable to the RDIS object.
	 * 
	 * @param jsonStateVariable
	 *           Json representation of State Variable
	 * @param rdis
	 *            RDIS object to construct
	 * @throws JSONException 
	 */
	private void addStateVariable(JSONObject jsonStateVariable, RDIS rdis) throws JSONException {
		String type = jsonStateVariable.getString(TYPE);
		String name = jsonStateVariable.getString(NAME);
		String value = jsonStateVariable.getString(VALUE);

		PythonInterpreter py = new PythonInterpreter();
		StateVariable sv = null;

		// Must initialize the state variable based on type.
		if (type.equals(SV_TYPE_FLOAT)) {
			sv = StateVariable.newFloat(name);
		} else if (type.equals(SV_TYPE_INT)) {
			sv = StateVariable.newInteger(name);
		} else if (type.equals(SV_TYPE_STRING)) {
			sv = StateVariable.newString(name);
		} else {
			throw new JSONException("Unknown State Variable type: " + type);
		}

		// Only override default value if needed.			

		if (value.length() > 0) {
			sv.setValue(RDIS.safeEval(value, py));
		}

		rdis.addStateVariable(sv);
	}

	/** Enumerated values for State Variable types. */
	public static final String
			SV_TYPE_INT = "int",
			SV_TYPE_FLOAT = "float",
			SV_TYPE_STRING = "string";

	/** Member name constants */
	public static final String
			INTERFACES = "interfaces",
			DOMAIN_INTERFACES = "domainInterfaces",
			DOMAIN_OUTPUTS = "domainOutputs",
			LOCAL_INTERFACE = "localInterfaces",
			INTERFACE_ARGUMENTS = "interfaceArguments",
			DOMAIN_OUTPUT = "domainOutput",
			CALLS = "calls",
			CONNECTIONS = "connections",
			TYPE = "type",
			TRIGGERS = "triggers",
			PRIMITIVE_CALLS = "primitiveCalls",
			NAME = "name",
			VALUE = "value",
			DELAY = "delay",
			PRIORITY = "priority",
			PARAMETERS = "parameters",
			ARGUMENTS = "arguments",
			PRIMITIVES = "primitives",
			RETURNS = "returns",
			POST_ACTIONS = "postActions",
			FORMAT_ARGS = "formatArgs",
			CONNECTION = "connection",
			PACK = "pack",
			UNPACK = "unpack",
			FORMAT = "format",
			REGEX = "regex",
			SERIAL_CONNECTIONS = "spp",
			STARTUP = "startup",
			KEEPALIVE = "keepalive",
			TERMINATE = "terminate",
			BAUD = "baud",
			INTERVAL = "interval",
			STATE_VARIABLES = "stateVariables";

	public static void main(String args[]) {
		if (args.length == 0) {
			System.out.println("Usage: java RDISDeserializer <input-file>");
			System.exit(1);
			return;
		}
		
		try {
			RDISDeserializer deserializer = new RDISDeserializer();
			RDIS rdis = deserializer.deserialize(args[0]);
			
//			rdis.addConnection(new SerialConnection(rdis, "btserial", 57600) {
//				public void write(byte bytes[]) {
//					int opcode = bytes[0];
//
//					opcode = (opcode < 0) ? opcode + 256 : opcode;
//
//					int velocity = (bytes[1] << 8) | (bytes[2] & 0xFF);
//					int radius = (bytes[3] << 8) | (bytes[4] & 0xFF);
//
//					System.out.println("write(" + opcode + ", " + velocity + ", " + radius + ")");
//				}
//			});
			
			
			
			rdis.startup("/dev/rfcomm1");
			
			rdis.setCallback(new RDIS.Callback() {
				public void onMessageReceived(String name, DomainAdapter contents) {
					if(name.equals("detect_bump")) {
						float dist = contents.getFloat(Range.DISTANCE);
						System.out.println("Recv'd range " + dist);
					}
				}
			});
			
			rdis.tick();
			
			rdis.callDomainInterface("set_velocity", new DifferentialSpeed(-0.2f,-0.4f));
			rdis.callDomainInterface("set_velocity", new DifferentialSpeed(0.0f,0.0f));
			rdis.callDomainInterface("set_velocity", new DifferentialSpeed(0f,0.2f));
			rdis.callDomainInterface("set_velocity", new DifferentialSpeed(0f,0.2f));
			rdis.callDomainInterface("set_velocity", new DifferentialSpeed(0.2f,0f));
			rdis.callDomainInterface("set_velocity", new DifferentialSpeed(-0.2f,0f));
			
			rdis.terminate();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (RDISException e) {
			e.printStackTrace();
		}

	}

}
