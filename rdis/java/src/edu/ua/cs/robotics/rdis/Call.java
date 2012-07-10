package edu.ua.cs.robotics.rdis;

import java.util.Arrays;

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
	
	public Call(String what) {
		this(what, new String[0]);
	}
	
	public Call(String what, String args[]) {
		this(what, args, 0);
	}
	
	public Call(String what, String args[], int interval) {
		this(what, args, interval, 0);
	}
	
	public Call(String what, String args[], int interval, int priority) {
		this.what = what;
		this.args = args;
		this.interval = interval;
		this.priority = priority;
	}
	
	public String what;
	public String args[];
	public int interval;
	public int priority;

	public static void main(String[] args){
		Call doCall = new Call("What Me");
		
		System.out.println("---- Init ----");
		System.out.println(doCall.what);
		
		System.out.println("\n---- 2 ----");
		doCall.args = new String[]{"this", "little", "light" };
		System.out.println(Arrays.toString(doCall.args));
		System.out.println("\n---- 3 ----");
		System.out.println("========= String List Declaration. . . =========");
		String[] stringList = new String[]{"a", "b","D", "x", "m", "r", "s"};
		
		System.out.println("stringList = null;");
		stringList = null;
		
		System.out.println("    ========= Start For Loop =========");
		for (int i = 0; stringList != null && (i < stringList.length); i++) {
			System.out.println("    " + stringList[i]);

		}

	}

	public int compareTo(Object other) {
		if(! (other instanceof Call)) return -1;
		
		Call otherCall = (Call) other;
		return this.priority - otherCall.priority;
	}
	
}
