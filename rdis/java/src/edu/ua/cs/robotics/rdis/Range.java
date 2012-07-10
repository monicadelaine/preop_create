package edu.ua.cs.robotics.rdis;

import org.python.util.PythonInterpreter;

/**
 * Models a 1D distance to an obstacle.
 */
public class Range extends DomainAdapter {

	/**
	 * Constructs a Range message given a distance.
	 * @param distance a distance in meters
	 */
	public Range(float distance) {
		put(Range.DISTANCE, new Float(distance));
	}
	
	public float getDistance() {
		return ((Float) mMap.get(Range.DISTANCE)).floatValue();
	}
	
	public static final String DISTANCE = "distance";
	
	public static void main(String args[]) {
		Range r = new Range(30.0f);
		PythonInterpreter py = new PythonInterpreter();
		
		r.inject(py);
		
		System.out.println( r.getFloat(DISTANCE) );
		
		py.exec("print 'distance =', distance");
	}
}
