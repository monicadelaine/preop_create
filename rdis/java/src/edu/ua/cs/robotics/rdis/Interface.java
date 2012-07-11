package edu.ua.cs.robotics.rdis;

import java.io.IOException;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

public class Interface {

	private RDIS mParent;
	private String mName;
	private String mType;
	private String mTriggers;
	private String mParameters[] = null;
	private Call mPrimitiveCalls[];

	public Interface(RDIS parent, String name, String type,
			String[] parameters, String triggers, Call primitiveCalls[]) {
		mParent = parent;
		mName = name;
		mType = type;
		mParameters = parameters;
		mTriggers = triggers;
		mPrimitiveCalls = primitiveCalls;
	}
	
	/**
	 * Returns the name of this Interface.
	 * @return name of interface
	 */
	public String getName() {
		return mName;
	}

	/**
	 * 
	 * @param arguments
	 * @throws IOException 
	 */

	public void call(PyObject arguments[]) throws RDISException {

		PythonInterpreter env = mParent.createEnvironment(mParameters, arguments);

		/*
		 * MAH: We may want to handle null cases differently. 
		 */
		for (int i = 0; mPrimitiveCalls != null && (i < mPrimitiveCalls.length); i++) {
			Call generalCall = mPrimitiveCalls[i];
			PyObject pyArgList[] = RDIS.safeEvalAll(generalCall.args, env);
			mParent.callPrimitive(generalCall.what, pyArgList);

			try {
				Thread.sleep(generalCall.interval);
			} catch (InterruptedException e) {
				throw new RDISException("Interrupted pausing between Primitive calls.", e);
			}

			if (mTriggers != null && mTriggers.length() > 0) {
				mParent.callDomainOutput(mTriggers);
			}

		}

	}

	/**
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			System.out.print("== INTRO ==\n");
			RDIS rdis = new RDIS();
			rdis.addStateVariable(StateVariable.newInteger("foo", 42));
			rdis.addStateVariable(StateVariable.newInteger("bar", 24));
			rdis.addConnection(new SerialConnection(rdis, "btserial", 57600));

//			Interface tmpInterface = new Interface(
//					rdis, // parent
//					"get_bumpers", // name
//					"periodic", // type
//					null, // parameters
//					"detect_bump", // triggers
//					new Call[] { new Call("whatName", null /* no args */, 0, 9) });
			
			System.out.println(" ");
			System.out.println("DO: tmpInterface.call()");
//			tmpInterface.call(new String[] { "1", "5", "3", "0", "9", "2" });

		} finally {
			System.out.print("== FINIS ==\n");
		}

	}
}
