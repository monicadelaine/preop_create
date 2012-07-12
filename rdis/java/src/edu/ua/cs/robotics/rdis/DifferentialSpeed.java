package edu.ua.cs.robotics.rdis;


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
}
