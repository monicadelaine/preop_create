package edu.ua.cs.robotics.rdis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.python.core.PyFloat;
import org.python.core.PyInteger;
import org.python.core.PyString;
import org.python.util.PythonInterpreter;

/**
 * Represents a message which can travel to or from the robot model.
 * 
 * Subclasses of this class constrain the possible types of Domain Adapter
 * (see {@link Range} and {@link DifferentialSpeed}). This class is not
 * abstract because currently the textual syntax does not provide a means to
 * map DomainOutputs to the specific type of DomainAdapter they return. As a
 * result, DomainAdapter must be instantiated inside of the DomainOutput, built
 * manually, and returned.
 */
public class DomainAdapter {
	
	/** Stores the Domain Adapter's attributes. */
	protected Map mMap = new HashMap();
	
	/** Subclasses must define their own constructor. */
	protected DomainAdapter() { }
	
	/**
	 * Constructs a DomainAdapter directly from a Map.
	 * @param map the map to construct from
	 */
	protected DomainAdapter(Map map) {
		mMap = map;
	}
	
	/**
	 * Injects the attributes of this Domain Adapter into the given Python environment.
	 * @param py the Python environment to inject into
	 */
	public void inject(PythonInterpreter py) {
		Set entries = mMap.entrySet();
		Iterator it = entries.iterator();
		
		while(it.hasNext()) {
			Map.Entry entry = (Entry) it.next();
			String name = (String) entry.getKey();
			Object value = entry.getValue();
			py.set(name, value);
		}
	}
	
	/**
	 * Returns true if this DomainAdapter has the attribute
	 * named {@code name}.
	 * @param name the name of the attribute
	 * @return true if the DomainAdapter contains the attribute
	 */
	public boolean has(String name) {
		return mMap.containsKey(name);
	}
	
	/**
	 * Returns the value with given name as an integer, or 0 if it does not exist.
	 * @param name the value to look up
	 * @return the value of name, or zero
	 */
	public int getInt(String name) {
		return getInt(name, 0);
	}
	
	/**
	 * Returns the value of name as an integer  or defValue if it does not exist.
	 * @param name name of value to return
	 * @param defValue the default value
	 * @return value of name
	 */
	public int getInt(String name, int defValue) {
		Object out = mMap.get(name);
		if(out == null) {
			return defValue;
		}
		
		if(out instanceof PyInteger)
			return ((PyInteger) out).getValue();
		else
			return ((Integer) out).intValue();
	}
	
	/**
	 * Returns the value with given name as a float, or 0 if it does not exist.
	 * @param name the value to look up
	 * @return the value of name, or zero
	 */
	public float getFloat(String name) {
		return getFloat(name, 0.0f);
	}
	
	/**
	 * Returns the value with given name as a float, or defValue if it does not exist.
	 * @param name the value to look up
	 * @param defValue the default value
	 * @return the value of name, or zero
	 */
	public float getFloat(String name, float defValue) {
		Object out = mMap.get(name);
		if(out == null) {
			return defValue;
		}
		
		if(out instanceof PyFloat)
			return (float) ((PyFloat) out).getValue();
		else if (out instanceof PyInteger) {
			return (float) ((PyInteger) out).getValue();
		}
		else
			return (float) ((Float) out).floatValue();
	}
	
	/**
	 * Returns the value with given name as a float, or null if it does not exist.
	 * @param name the value to look up
	 * @return the value of name, or null
	 */
	public String getString(String name) {
		Object out = mMap.get(name);
		if(out == null) return null;
		
		if(out instanceof PyString)
			return ((PyString) out).asString();
		else
			return ((String) out);
	}
	
	/**
	 * Puts a value in the HashMap.
	 * @param name the name of the value
	 * @param value the value to assign
	 * @return the previous value of name
	 */
	protected Object put(String name, Object value) {
		return mMap.put(name, value);
	}
	
	/**
	 * Puts all the key/values in {@code map} in the attributes table
	 * of the DomainAdapter.
	 * @param map the map to use
	 */
	protected void putAll(Map map) {
		mMap.putAll(map);
	}
}
