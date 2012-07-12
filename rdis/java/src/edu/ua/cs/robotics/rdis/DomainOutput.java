package edu.ua.cs.robotics.rdis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.python.util.PythonInterpreter;

/**
 * Represents an "output" for the model. 
 *
 * Domain Outputs send {@link DomainAdapter Domain Adapters} as messages
 * back to the software framework.
 *
 */
public class DomainOutput {
	
	/** RDIS parent object. */
	private RDIS mParent;
	
	/** Name of Domain Output. */
	private String mName;
	
	/** Attribute names and expressions returned by this DomainOutput. */
	private HashMap mReturns = new HashMap();
	
	/**
	 * Constructs a Domain Output.
	 * @param parent parent RDIS object
	 * @param name name of Domain Output
	 */
	public DomainOutput(RDIS parent, String name) {
		mParent = parent;
		mName = name;
	}
	
	/**
	 * Returns the name of this domain output.
	 * @return name of domain output
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * Calls this domain output, triggering a message on the RDIS callback.
	 */
	public void call() {
		HashMap copy = (HashMap) mReturns.clone();
		
		Set entries = copy.entrySet();
		Iterator it = entries.iterator();
		PythonInterpreter py = mParent.getPythonInterpreter();
		
		while(it.hasNext()) {
			Map.Entry entry = (Map.Entry) it.next();
			String value = (String) entry.getValue();
			entry.setValue( RDIS.safeEval(value, py) );
		}
		
		mParent.deliverMessage(mName, new DomainAdapter(copy));
	}
	
	/**
	 * Adds an entry to the return object.
	 * @param name name of the value
	 * @param expression a Python expression in terms of state variables
	 */
	protected void addReturns(String name, String expression) {
		mReturns.put(name,expression);
	}
	
}
