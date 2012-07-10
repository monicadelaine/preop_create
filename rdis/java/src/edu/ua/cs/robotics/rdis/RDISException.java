package edu.ua.cs.robotics.rdis;

public class RDISException extends Exception {

	private static final long serialVersionUID = 4073415693132640393L;
	
	private String mMessage;
	
	private Throwable mCause;
	
	public RDISException(String msg) {
		this(msg, null);
	}
	
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
