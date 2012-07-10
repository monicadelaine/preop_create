package edu.ua.cs.robotics.rdis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.python.util.PythonInterpreter;

public class DomainOutput {
	
	private RDIS mParent;
	
	private String mName;
	
	private HashMap mReturns = new HashMap();
	
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
	
	public static void main(String args[]) {
		RDIS rdis = new RDIS();
		
		rdis.addStateVariable(StateVariable.newFloat("pi", 3.14159f));
		
		DomainOutput domOutput = new DomainOutput(rdis, "piMaker");
		domOutput.addReturns("piOverTwo", "<pi / 2>");
		domOutput.addReturns("piTimesTwo", "<pi * 2>");
		rdis.addDomainOutput(domOutput);
		
		rdis.setCallback(new RDIS.Callback() {
			public void onMessageReceived(String name, DomainAdapter contents) {
				System.out.println("Triggered: " + name);
				System.out.println("Pi over two: " + contents.getFloat("piOverTwo"));
				System.out.println("Pi times two: " + contents.getFloat("piTimesTwo"));
			}
		});
		
		domOutput.call();
	}
}
