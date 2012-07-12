package edu.ua.cs.robotics.rdis;

/**
 * Wraps all the exceptions which can be thrown from within the RDIS model.
 * 
 *	Besides being decent practice within Java, this makes practical sense within
 * RDIS. For example, any number of serial port exceptions may be thrown by a
 * SerialConnection and would need to be propagated up to the first available public
 * API function. However, if we're to handle Bluetooth connections as well, we wouldn't
 * want to handle all those exceptions as well. 
 *
 */
public class RDISException extends Exception {

	/** Generated UID */
	private static final long serialVersionUID = 4073415693132640393L;
	
	/** Message associated with the Exception. */
	private String mMessage;
	
	/** The cause of the Exception. */
	private Throwable mCause;
	
	/**
	 * Creates a generic RDISException with no cause.
	 * @param msg message associated with the exception
	 */
	public RDISException(String msg) {
		this(msg, null);
	}
	
	/**
	 * Creates an RDISException which wraps another exception.
	 * @param msg message associated with the exception
	 * @param cause the cause of the exception
	 */
	public RDISException(String msg, Throwable cause) {
		mMessage = msg;
		mCause = cause;
	}
	
	public String getMessage() {
		return mMessage;
	}
	
	public Throwable getCause() {
		return mCause;
	}
	
}
