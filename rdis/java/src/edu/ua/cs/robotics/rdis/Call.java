package edu.ua.cs.robotics.rdis;


/**
 * Lots of things call other things in RDIS. Therefore, it seems logical
 * to make this Call class. A "call" to something consists of the name
 * of the thing we want to call and a list of the arguments.
 * 
 * This call object should be general enough to use with any of the following
 * "calls" within RDIS:
 * 
 * -- When a Domain Interface calls an Interface
 * -- When a Connection calls an Interface
 * -- When an Interface calls a Primitive
 * 
 * @author pkilgo
 *
 */
public class Call implements Comparable {
	
	/**
	 * Constructs a no-args call.
	 * @param name name of thing to call
	 */
	public Call(String name) {
		this(name, new String[0]);
	}
	
	/**
	 * Constructs a call with given arguments.
	 * @param name name of thing to call
	 * @param args arguments for the call
	 */
	public Call(String name, String args[]) {
		this(name, args, 0);
	}
	
	/**
	 * Constructs a call with given arguments and interval.
	 * @param name name of thing to call
	 * @param args arguments for the call
	 * @param interval amount of time to pause in milliseconds after the call.
	 */
	public Call(String name, String args[], int interval) {
		this(name, args, interval, 0);
	}
	
	/**
	 * Constructs a call with given arguments, interval, and priority.
	 * @param name name of thing to call
	 * @param args arguments for the call
	 * @param interval amount of time to pause in milliseconds after call
	 * @param priority priority of the call
	 */
	public Call(String name, String args[], int interval, int priority) {
		this.name = name;
		this.args = args;
		this.interval = interval;
		this.priority = priority;
	}
	
	/** Name of the thing to call. */
	public String name;
	
	/** Arguments (as Python expressions) for the Call. */
	public String args[];
	
	/** Time in milliseconds to pause after the call. */
	public int interval;
	
	/** For use in sorting. */
	public int priority;

	/**
	 * Compares a Call to other objects. If the object is another, call
	 * it will compare by priority. Otherwise, the Call object will be
	 * considered "less than" all other classes.
	 */
	public int compareTo(Object other) {
		if(! (other instanceof Call)) return -1;
		
		Call otherCall = (Call) other;
		return this.priority - otherCall.priority;
	}
	
}
