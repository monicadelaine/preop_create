package edu.ua.cs.robotics.rdis;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * Abstracts a call to one or more {@link Primitive Primitives}.
 * 
 * An Interface is a slightly more developer-friendly way to call
 * Primitives. Generally it offers slightly higher-level control of the robot and uses
 * world units as opposed to machine units.
 */
public class Interface {

	/** Parent RDIS object. */
	private RDIS mParent;
	
	/** Name of the Interface. */
	private String mName;
	
	/** Type of Interface ({@code adhoc} or {@code periodic}). */
	// TODO: Eventually this may need to be done away with.
	// In replacement of this, one could use multiple keepalives as there does not appear
	// to be a difference between the two.
	private String mType;
	
	/** Name of the {@link DomainOutput} this Interface triggers (or {@code null}, empty string if none). */
	private String mTriggers;
	
	/** Formal parameter names for this Interface. */
	private String mParameters[] = null;
	
	/** Calls to Primitives. */
	private Call mPrimitiveCalls[];

	/**
	 * Constructs an Interface
	 * @param parent RDIS parent object
	 * @param name name of Interface
	 * @param type type of Interface
	 * @param parameters formal parameter names of the Interface
	 * @param triggers name of triggered Domain Output
	 * @param primitiveCalls primitive calls by this Interface
	 */
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
	 * Invokes this Interface.
	 * @param arguments arguments for the call
	 * @throws RDISException if there was a communication problem
	 */
	public void call(PyObject arguments[]) throws RDISException {

		PythonInterpreter env = mParent.createEnvironment(mParameters, arguments);

		/*
		 * MAH: We may want to handle null cases differently. 
		 */
		for (int i = 0; mPrimitiveCalls != null && (i < mPrimitiveCalls.length); i++) {
			Call generalCall = mPrimitiveCalls[i];
			PyObject pyArgList[] = RDIS.safeEvalAll(generalCall.args, env);
			mParent.callPrimitive(generalCall.name, pyArgList);

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

}
