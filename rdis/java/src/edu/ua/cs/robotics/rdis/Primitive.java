package edu.ua.cs.robotics.rdis;

import java.io.IOException;
import org.python.core.PyObject;
import org.python.core.PyString;
import org.python.core.PyTuple;
import org.python.modules.struct;
import org.python.util.PythonInterpreter;

public class Primitive {

	private RDIS mParent;
	private String mFormat;
	private String mName;
	private String mPack;
	private String mUnpack;
	private String mRegex;
	private String mConnection;
	private String[] mParameters;
	private String[] mFormatArgs;
	private String[] mPostActions;

	private Primitive(RDIS parent, String name, String parameters[],
			String connection, String formatArgs[], String postActions[]) {
		/*
		 *  TODO: In future versions, Primitive should probably be subclassed
		 *  into FixedWidthPrimitive and AsciiEncodedPrimitive so they can each
		 *  have their own constructors.
		 *  
		 *  mPack and mUnpack should only belong to FixedWidthPrimitive
		 *  
		 *  mFormat and mRegex should only belong to AsciiEncodedPrimitive
		 *  
		 *  Until then, we have to hide the constructors and use static methods as
		 *  factories for primitives.
		 */
		
		mParent = parent;
		mName = name;
		mParameters = parameters;
		mConnection = connection;
		mFormatArgs = formatArgs;
		mPostActions = postActions;
	}
	
	/**
	 * this does stuff
	 * @param arguments
	 * @throws IOException 
	 */
	public void call(PyObject arguments[]) throws RDISException {

		PythonInterpreter env = mParent.createEnvironment(mParameters, arguments);
		
		PyObject values[] = new PyObject[mFormatArgs.length];
		
		for(int i = 0; i < mFormatArgs.length; i++){
			
			values[i] = RDIS.safeEval( mFormatArgs[i], env );
			
		}

		Connection connection = getConnection();
		connection.write(doPack(values));

		PyTuple out = doUnpack(connection);
		env.set("__out__", out);

		if (mPostActions != null) {
			for (int i = 0; i < mPostActions.length; i++) {
				env.exec(mPostActions[i]);
			}
		}

		mParent.updateStateVariablesFrom(env);
	}

	/**
	 * Returns the length of the parameters of the given Primitive
	 * @return
	 */
	public int parameterCount() {
		return mParameters.length;
	}

	/**
	 * Returns the name of this primitive.
	 * @return Primitive name
	 */
	public String getName() {
		return mName;
	}

	/**
     * Returns connection associated with this Primitive.
	 * @return a Connection object
	 */
	private Connection getConnection() {
		return mParent.getConnection(mConnection);
	}

	private byte[] doPack(PyObject[] values) {

		if (mFormat != null) {
			/**
			 * TODO: Develop this section
			 */
			return null;
		} else {
			
			PyObject[] tmp = new PyObject[ values.length + 1 ];

			tmp[0] = new PyString(mPack);

			for(int i = 0; i < values.length; i++){
				tmp[i+1] = values[i];
			}
			
			return struct.pack(tmp).toBytes();
			
		}
	}

	private PyTuple doUnpack( Connection connection ) throws RDISException {

		if ( mRegex != null ) {
			/**
			 * TODO: Develop this section
			 */
			return null;
		}
		else if (mUnpack != null && mUnpack.length() > 0) {
			int sz = struct.calcsize(mUnpack);
			byte[] byteSequence = null;
			byteSequence = connection.read(sz);
			return struct.unpack( mUnpack, new String(byteSequence) );
		}
		return null;
	}
	
	public static Primitive newFixedWidth(RDIS parent, String name, String parameters[],
			String connection, String formatArgs[], String postActions[], String pack, String unpack) {
		
		Primitive p = new Primitive(parent,name,parameters,connection,formatArgs,postActions);
		p.mPack = pack;
		p.mUnpack = unpack;
		
		return p;
	}
	
	public static Primitive newAsciiEncoded(RDIS parent, String name, String parameters[],
			String connection, String formatArgs[], String postActions[], String format, String regex) {
		
		Primitive p = new Primitive(parent,name,parameters,connection,formatArgs,postActions);
		p.mFormat = format;
		p.mRegex = regex;
		
		return p;
	}

	public static void main(String args[]) throws IOException {
		try {
			System.out.print("== INTRO ==\n");
			RDIS rdis = new RDIS();
			rdis.addStateVariable(StateVariable.newInteger("foo", 42));
			rdis.addStateVariable(StateVariable.newInteger("bar", 24));
			rdis.addConnection(new SerialConnection(rdis, "btserial", 57600));
			
			Primitive p = Primitive.newFixedWidth(rdis,
					"some_primitive", 
					new String[] {	"velocity", "radius" },
					"btserial",
					new String[] { "<137>", "<velocity>", "<radius>" },
					null,
					">Bhh",
					"B"); 
					
			
//			/* This broke because now we want PyObject instead of Object */
//			p.call(new Object[]{ new Integer(12), new Integer(32)});
			
			
			// Note that the StateVariables are available in the PythonInterpreter. 
			PythonInterpreter py = rdis.getPythonInterpreter();
			py.exec("print 'foo + bar = ', foo + bar");
			
		}
		finally {
			System.out.print("== FINIS ==\n");
		}
	}
}
