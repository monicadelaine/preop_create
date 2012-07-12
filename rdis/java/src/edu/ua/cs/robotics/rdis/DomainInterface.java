package edu.ua.cs.robotics.rdis;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * Represents a named "input" for the model.
 * 
 * Frameworks make calls to Domain Interfaces which eventually create the messages
 * to control the robot.
 */
public class DomainInterface {
	private RDIS mParent;
	private String mName;
	private Call mLocalInterface;
	private String mParameters[];
	
	/**
	 * Constructs a Domain Interface 
	 * @param parent parent RDIS object
	 * @param name name of Domain Interface
	 * @param localInterface Interface to call
	 * @param parameters formal parameter names for the Domain Interface
	 */
	public DomainInterface(RDIS parent, String name, Call localInterface, String parameters[]){
		mParent = parent;
		mName = name;
		mLocalInterface = localInterface;
		mParameters = parameters;
	}

	/**
	 * Invokes this Domain Interface.
	 * @param domainAdapter the Domain Adapter to receive
	 * @throws RDISException if there was some error along the call
	 */
	public void call(DomainAdapter domainAdapter) throws RDISException {
		PythonInterpreter env = mParent.getPythonInterpreter(); 
		domainAdapter.inject(env);
		PyObject values[] = RDIS.safeEvalAll(mLocalInterface.args, env);
		mParent.callInterface(mLocalInterface.name, values);
	}
	
	/**
	 * Gets the name of the Domain Interface.
	 * @return name of Domain Interface
	 */
	public String getName() {
		return mName;
	}
}
