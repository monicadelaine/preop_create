package edu.ua.cs.robotics.rdis;

import org.python.util.PythonInterpreter;

/**
 * Represents the desired path for a differential-drive robot. 
 *
 */
public class DifferentialSpeed extends DomainAdapter {
	
	/**
	 * Constructs a differential speed given a linear and angular velocity.
	 * @param linear a linear velocity in meters per second
	 * @param angular an angular velocity in radians per second
	 */
	public DifferentialSpeed(float linear, float angular) {
		put(DifferentialSpeed.LINEAR, new Float(linear));
		put(DifferentialSpeed.ANGULAR, new Float(angular));
	}
	
	/**
	 * Returns the linear velocity.
	 * @return the linear velocity in meters per second
	 */
	public float getLinear() {
		return ((Float) mMap.get(LINEAR)).floatValue();
	}
	
	/**
	 * Returns the angular velocity.
	 * @return the angular velocity in radians per second
	 */
	public float getAngular() {
		return ((Float) mMap.get(ANGULAR)).floatValue();
	}
	
	public static final String
		LINEAR = "linear",
		ANGULAR = "angular";
	
	public static void main(String args[]) {
		DifferentialSpeed ds = new DifferentialSpeed(0.2f, 0.4f);
		
		System.out.println(ds.getLinear());
		System.out.println(ds.getAngular());
		
		PythonInterpreter py = new PythonInterpreter();
		ds.inject(py);
		
		py.exec("print 'linear =', linear");
		py.exec("print 'angular =', angular");
		
		// See: http://chess.eecs.berkeley.edu/eecs149/documentation/differentialDrive.pdf
		py.exec("print 'V_R = ', linear + (0.4 * angular)/2");
		py.exec("print 'V_L = ', linear - (0.4 * angular)/2");
	}
}
