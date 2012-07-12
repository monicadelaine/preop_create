package edu.ua.cs.robotics.rdis;


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
	
	/**
	 * Casts the distance as a Java float.
	 * @return the distance
	 */
	public float getDistance() {
		return ((Float) mMap.get(Range.DISTANCE)).floatValue();
	}
	
	/** Attribute name constants. */
	public static final String DISTANCE = "distance";
	
}
