package edu.ua.cs.robotics.rdis;

import org.python.core.PyObject;

/**
 * Class to emulate RDIS State Variables in Java.
 */
public class StateVariable {

	/** Integer type code for state variable: one of the TYPE_* constants. */
	private int mType;
	
	/** Name of the state variable */
	private String mName;
	
	/** Value of the state variable */
	private Object mValue;
	
	/**
	 * Default value alias for StateVariable(int,String,Object)
	 */
	private StateVariable(int type, String name) {
		this(type, name, StateVariable.getDefaultValue(type));
	}
	
	/**
	 * Initializes State Variable with default value for given type.
	 * @param type RDIS type for state variable (one of TYPE_*)
	 * @param name the name for the state variable
	 * @param value the value to initialize to
	 */
	private StateVariable(int type, String name, Object value) {
		mName = name;
		setType(type);
		setValue(value);
	}
	
	/**
	 * Gets name of this state variable.
	 * @return the name of the state variable
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * Sets the type of this State Variable.
	 * @param type integer type code
	 */
	private void setType(int type) {
		StateVariable.assertValidType(type);
		mType = type;
	}
	
	/**
	 * Alias for setValue(Object)
	 * @param i new value for state variable
	 */
	public void setValue(int i) {
		if(mType == TYPE_FLOAT) {
			setValue(new Float(i));
		}
		else {
			setValue(new Integer(i));
		}
	}
	
	/**
	 * Alias for setValue(Object)
	 * @param f new value for state variable
	 */
	public void setValue(float f) {
		setValue(new Float(f));
	}
	
	/**
	 * Alias for setValue(Object)
	 * @param pyObj PyObject to be converted to int, float, or string
	 */
	public void setValue(PyObject pyObj) {
		switch(mType) {
		case TYPE_INT:
			setValue(pyObj.asInt());
			break;
		case TYPE_FLOAT:
			setValue((float)pyObj.asDouble());
			break;
		case TYPE_STRING:
			setValue(pyObj.asString());
			break;
		}
	}
	
	/**
	 * Sets the value of this State variable.
	 * @param value the new value for the state variable
	 */
	public void setValue(Object value) {
		assertValidValue(value);
		mValue = value;
	}
	
	/**
	 * Returns this state variable cast as an int.
	 * @return the state variable as an int
	 * @throws ClassCastException if improper cast
	 */
	public int getInt() {
		return ((Integer) mValue).intValue();
	}
	
	/**
	 * Returns this state variable cast as an float
	 * @return the state variable as a float
	 * @throws ClassCastException if improper cast
	 */
	public float getFloat() {
		return ((Float) mValue).floatValue();
	}
	
	/**
	 * Returns the Java value of this State Variable.
	 * @return Java value
	 */
	public Object getValue() {
		return mValue;
	}

	/**
	 * Returns this state variable cast as a String
	 * @return the state variable as an String
	 * @throws ClassCastException if improper cast
	 */
	public String getString() {
		return ((String) mValue).toString();
	}
	
	/**
	 * Checks if State Variable is an integer.
	 * @return true if state variable is an integer
	 */
	public boolean isInteger() {
		return mType == TYPE_INT;
	}
	
	/**
	 * Checks if state variable is a string.
	 * @return true if state variable is string
	 */
	public boolean isString() {
		return mType == TYPE_STRING;
	}
	
	/**
	 * Checks if state variable is a  float
	 * @return true if state variable is a float.
	 */
	public boolean isFloat() {
		return mType == TYPE_FLOAT;
	}
	
	public String toString() {
		return mValue.toString();
	}
	
	/**
	 * Asserts that value is a valid Java type for this State Variable.
	 * @param value the value to check
	 * @throws IllegalStateException if value is not type-compatible
	 */
	private void assertValidValue(Object value) {
		int type = typeOf(value);
		if(mType != type) {
			throw new IllegalStateException(
				String.format("Attempt to set '%s' (type '%s') to value '%s' (type '%s')",
						new Object[]{mName, nameOfType(mType), value, nameOfType(value)})
			);
		}
	}
	
	/**
	 * Asserts that type is a valid RDIS type code.
	 * @param type the type code to check
	 * @throws IllegalStateException if type is not a valid RDIS type
	 */
	private static void assertValidType(int type) {
		if(type < TYPE_MAP.length && type != 0) return;
		throw new IllegalStateException("Invalid type: " + type);
	}
	
	/**
	 * Gets the default value for given type.
	 * @param type
	 * @return the default value for type
	 * @throws IllegalStateException if type is not a valid RDIS type
	 */
	public static Object getDefaultValue(int type) {
		StateVariable.assertValidType(type);
		return TYPE_MAP[type][3];
	}
	
	/**
	 * Gets the integer type code for value.
	 * @param value the to get the RDIS type of
	 * @return RDIS integer type code
	 */
	public static int typeOf(Object value) {
		for(int i = 1; i < TYPE_MAP.length; i++) {
			Class cls = (Class) TYPE_MAP[i][1];
			if(cls.isInstance(value)) {
				return i;
			}
		}
		return 0;
	}
	
	/**
	 * Gets pretty name of given RDIS type.
	 * @param type the type to look up
	 * @return the pretty name of the RDIS type
	 */
	public static String nameOfType(int type) {
		if(type < TYPE_MAP.length) return (String) TYPE_MAP[type][0];
		return (String) TYPE_MAP[0][0];
	}
	
	/**
	 * Gets pretty RDIS name of given Java value.
	 * @param value the value to look up
	 * @return the pretty name of the Java value
	 */
	public static String nameOfType(Object value) {
		int type = typeOf(value);
		if(type < TYPE_MAP.length) return (String) TYPE_MAP[type][0];
		return (String) TYPE_MAP[0][0];
	}
	
	public static StateVariable newInteger(String name) {
		return newInteger(name, 0);
	}
	
	public static StateVariable newInteger(String name, int value) {
		return new StateVariable(TYPE_INT, name, new Integer(value));
	}
	
	public static StateVariable newString(String name) {
		return newString(name, "");
	}
	
	public static StateVariable newString(String name, String value) {
		return new StateVariable(TYPE_STRING, name, value);
	}
	
	public static StateVariable newFloat(String name) {
		return newFloat(name, 0.0f);
	}
	
	public static StateVariable newFloat(String name, float value) {
		return new StateVariable(TYPE_FLOAT, name, new Float(value));
	}
	
	public static void main(String args[]) {
		StateVariable sv[] = {
			StateVariable.newInteger("a"),
			StateVariable.newFloat("b"),
			StateVariable.newString("c"),
			StateVariable.newInteger("d", 50),
			StateVariable.newFloat("f", 0.15f),
			StateVariable.newString("e", "Hello, world!")
		};
		
		for(int i = 0; i < sv.length; i++) {
			System.out.print(sv[i].getName() + " = ");
			System.out.println(sv[i]);
			
			int which = i%3;
			
			switch(which) {
			case 0: sv[i].setValue(42); break;
			case 1: sv[i].setValue(3.14159f); break;
			case 2: sv[i].setValue("Goodbye, world!"); break;
			}
			
			System.out.print(sv[i].getName() + " = ");
			System.out.println(sv[i]);
		}
	}

	public static final int
		TYPE_NOTDEFINED = 0,
		TYPE_INT = TYPE_NOTDEFINED + 1,
		TYPE_STRING = TYPE_NOTDEFINED + 2,
		TYPE_FLOAT = TYPE_NOTDEFINED + 3;
	
	/** This array maps RDIS types to Java types */
	private static final Object
		TYPE_MAP[][] =
		{
			{"not_defined", Object.class, new Object()},
			{"int", Integer.class, new Integer(0)},
			{"string", String.class, new String()},
			{"float", Float.class, new Float(0.0f)}
		};
}
