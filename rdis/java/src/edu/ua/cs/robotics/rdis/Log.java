package edu.ua.cs.robotics.rdis;

/**
 * Logging mechanism for RDIS.
 * 
 * Subclasses of this class should override {@link Log#onMessage(int, String, String)} to
 * provide custom means to handle logging messages.
 */
public abstract class Log {

	/**
	 * Integer code for the current log level. Any message received at or above this threshold
	 * will be delivered.
	 */
	private int mLogLevel = 0;
	
	/**
	 * Creates a Log object with default log level.
	 */
	public Log() {
		this(Log.WARN);
	}
	
	/**
	 * Creates a Log object with custom log level.
	 * @param logLevel the custom log level
	 */
	public Log(int logLevel) {
		mLogLevel = logLevel;
	}
	
	/**
	 * Logs an error message.
	 * @param msg the message
	 */
	protected void error(String msg) {
		log(Log.ERROR, msg);
	}
	
	/**
	 * Logs a warning.
	 * @param msg the message
	 */
	protected void warn(String msg) {
		log(Log.WARN, msg);
	}
	
	/**
	 * Logs a debug message.
	 * @param msg the message
	 */
	protected void debug(String msg) {
		log(Log.DEBUG, msg);
	}
	
	/**
	 * Logs an informational message.
	 * @param msg the message
	 */
	protected void info(String msg) {
		log(Log.INFO, msg);
	}
	
	/**
	 * Logs a critical error.
	 * @param msg the message
	 */
	protected void critical(String msg) {
		log(Log.CRITICAL, msg);
	}
	
	/**
	 * Logs a message.
	 * @param msgLevel the log level of the message
	 * @param msg the message
	 */
	protected void log(int msgLevel, String msg) {
		if(msgLevel >= mLogLevel) {
			onMessage(msgLevel, Log.getCode(msgLevel), msg);
		}
	}
	
	/**
	 * Maps a log level to a human-readable log code.
	 * @param msgLevel the message log level
	 * @return code associated with log level
	 */
	private static String getCode(int msgLevel) {
		int idx = msgLevel / 10;
		idx = Math.max(0, Math.min(idx, Log.LOG_CODES.length - 1));
		return Log.LOG_CODES[idx];
	}
	
	/** 
	 * Handles a log message which is at or above current log level.
	 * @param msgLevel the message importance level
	 * @param code human-friendly code for the message importance level
	 * @param msg the message
	 */
	public abstract void onMessage(int msgLevel, String code, String msg);
	

	/** Log level constants. */
	public static final int
		ALL = 0,
		DEBUG = 10,
		INFO = 20,
		WARN = 30,
		ERROR = 40,
		CRITICAL = 50;
	
	/** Human-readable log codes. */
	private static final String[] LOG_CODES = new String[] {
		"ALL", "DBG", "INFO", "WARN", "ERR", "CRI"
	};

	/**
	 * Sets the log level.
	 * @param logLevel the new log level
	 */
	public void setLogLevel(int logLevel) {
		mLogLevel = logLevel;
	}
}
