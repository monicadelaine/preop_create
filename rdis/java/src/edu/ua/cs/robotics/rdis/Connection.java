package edu.ua.cs.robotics.rdis;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

/**
 * Represents an abstraction Connection in the RDIS model.
 *
 */
public abstract class Connection {
	
	/** Name of the connection. */
	protected String mName;
	
	/** RDIS parent object. */
	protected RDIS mParent;
	
	/** Interface called when the connection initializes. */
	protected Call mStartup;
	
	/** Interface which is called periodically. */
	protected Call mKeepalive;
	
	/** Interface called when the connection terminates */
	protected Call mTerminate;
	
	/** Flag for keeping track of if we are initialized. */
	protected boolean mInitialized;

	/** Replaces onRead() if not null. */
	private InputStream mOverrideInputStream;
	
	/** Replaces onWrite() if not null. */
	private OutputStream mOverrideOutputStream;
	
	/**
	 * Constructs an abstraction connection for use by child classes.
	 * @param parent parent RDIS object
	 * @param name name of the connection
	 */
	protected Connection(RDIS parent, String name) {
		mParent = parent;
		mName = name;
	}

	/**
	 * Called by RDIS parent when the connection should initialize.
	 * @param portName name of the port (can be null of name does not make sense in the
	 * 	specific context)
	 * @throws RDISException if the connection could not be initialized
	 */
	public void startup(String portName) throws RDISException {
		if (mInitialized == false){
			mInitialized = true;
		}
		else{
			return;
		}
		
		// Initializes the port if not overridden.
		if(!isOverridden()) {
			onStartup(portName);
		}
		
		// Call startup Interface.
		doCall(mStartup);
	}
	
	/**
	 * Performs any periodic actions associated with this connection (such as calling the
	 * keepalive Interface).
	 * @throws RDISException if there was some error performing the periodic actions
	 */
	public void tick() throws RDISException {
		if(!isOverridden()) {
			onKeepalive();
		}
		doCall(mKeepalive);
	}
	
	/**
	 * Sets the keepalive Interface call.
	 * @param keepalive the keepalive call
	 */
	public void setKeepalive(Call keepalive) {
		mKeepalive = keepalive;
	}
	
	/**
	 * Sets the terminate Interface call.
	 * @param terminate the terminate call
	 */
	public void setTerminate(Call terminate) {
		mTerminate = terminate;
	}
	
	/**
	 * Sets the startup Interface call.
	 * @param startup
	 */
	public void setStartup(Call startup) {
		mStartup = startup;
	}
	
	/**
	 * Overrides the connection's subclass read/write behavior with a set of Input and
	 * Output streams.
	 * @param is the InputStream to use
	 * @param os the OutputStream to use
	 */
	public void override(InputStream is, OutputStream os) {
		if(is == null || os == null) return;
		
		mOverrideInputStream = is;
		mOverrideOutputStream = os;
	}
	
	/**
	 * Performs a generic Interface call.
	 * @param call the Interface call to perform
	 * @throws RDISException if there was a problem calling the Interface
	 */
	private void doCall(Call call) throws RDISException {
		if(call == null) return;
		
		PythonInterpreter env = mParent.getPythonInterpreter();
		PyObject args[] = RDIS.safeEvalAll(call.args, env);
		mParent.callInterface(call.name, args);
		try {
			if(call.interval > 0) {
				Thread.sleep(call.interval);
			}
		}
		catch(InterruptedException e) {
			throw new RDISException("Interrupted during sleep after Interface call.", e);
		}
	}

	/**
	 * Terminates the connection.
	 */
	public final void terminate() {
		try {
			doCall(mTerminate);
		}
		catch(RDISException e) {
			mParent.getLog().warn("Could not close connection `" + mName + "`.");
		}
		finally {
			// Don't call abstract methods if overriden.
			if(!isOverridden()) {
				onTerminate();
			}
		}
	}
	
	/** 
	 * Child classes override this for connection-specific behavior 
	 * @throws RDISException
	 */
	protected abstract void onStartup(String portName) throws RDISException;
	
	/** Child classes override this for connection-specific behavior */
	protected abstract void onKeepalive();
	
	/** Child classes override this for connection-specific behavior */
	protected abstract void onTerminate();
	
	/** Child class behavior for writing. */
	protected abstract void onWrite(byte stuff[]) throws RDISException;
	
	/** Child class behavior for reading. */
	protected abstract byte[] onRead(int num) throws RDISException;
	
	/**
	 * Gets the name of this connection.
	 * @return the name of the connection
	 */
	public String getName() {
		return mName;
	}
	
	/**
	 * Writes an array of bytes to overriden OutputStream or child class implementation of onWrite().
	 * @param stuff the byte array to write
	 * @throws RDISException if there was an I/O error
	 */
	public void write(byte stuff[]) throws RDISException {
		if(mOverrideOutputStream != null) {
			try {
				mOverrideOutputStream.write(stuff);
			} catch (IOException e) {
				throw new RDISException("Error writing to overridden OutputStream.", e);
			}
		}
		else {
			onWrite(stuff);
		}
		
		mParent.getLog().debug("Wrote (" + mName + "): " + toHexArray(stuff));
	}
	
	/**
	 * Reads some bytes from the connection.
	 * @param num number of bytes to read
	 * @return the read bytes
	 * @throws RDISException if there was some problem reading
	 */
	public byte[] read(int num) throws RDISException {
		byte bytes[] = new byte[num];
		if(mOverrideInputStream != null) {
			try {
				mOverrideInputStream.read(bytes);
			} catch (IOException e) {
				throw new RDISException("Error reading from overridden InputStream.", e);
			}
		}
		else {
			bytes = onRead(num);
		}
		
		mParent.getLog().debug("Read (" + mName + "): " + toHexArray(bytes));
		return bytes;
	}
	
	/**
	 * Returns true if this connection's child class behavior for reading and
	 * writing has been overridden.
	 * @return
	 */
	public boolean isOverridden() {
		return mOverrideInputStream != null && mOverrideOutputStream != null;
	}
	
	/**
	 * Converts a byte array to equivalent hexadecimal as a String.
	 * @param stuff the byte array
	 * @return a space-padded String showing the hexadecimal form of the bytes
	 */
	protected String toHexArray(byte stuff[]) {
		String s = "";
		for(int i = 0; i < stuff.length; i++) {
			s += String.format("0x%x", new Object[] { Byte.valueOf(stuff[i]) });
			if(i < stuff.length - 1) {
				s += " ";
			}
		}
		return s;
	}
	
}
