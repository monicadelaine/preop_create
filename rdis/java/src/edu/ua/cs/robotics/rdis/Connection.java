package edu.ua.cs.robotics.rdis;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Random;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

//import java.io.FileDescriptor;
//import java.io.InputStream;

public abstract class Connection {
	
	//TODO: Threading                 // NOTE: The following examples are deriverd from an instance of RDIS after irobot.rdis.json was read in. 
	protected String mName;		      // Example from Python:  btserial
	protected RDIS mParent;            // Example from Python:  <rdis.RDIS instance at 0x00000000000000_A_0>
	
	/** Interface called when the connection initializes. */
	protected Call mStartup;        // Example from Python:  { 'name': 'startup', 'arguments': [] }
	
	/** Interface which is called periodically. */
	protected Call mKeepalive;      // Example from Python:  { 'interval': 1000, 'name': 'get_bumpers', 'arguments': [] }
	
	/** Interface called when the connection terminates */
	protected Call mTerminate;      // Example from Python:  { 'name': 'shutdown', 'arguments': [] }
	
	protected boolean mInitialized;    // Example from Python:  False
	
	protected Random mRNG = new Random();
	
	private InputStream mOverrideInputStream;
	
	private OutputStream mOverrideOutputStream;
	
	protected Connection(RDIS parent, String name) {
		mParent = parent;
		mName = name;
	}

	public void startup(String portName) throws RDISException {
		if (mInitialized == false){
			mInitialized = true;
		}
		
		// Initializes the port if not overridden.
		if(!isOverridden()) {
			onStartup(portName);
		}
		
		doCall(mStartup);
	}
	
	public void keepalive() throws RDISException {
		if(!isOverridden()) {
			onKeepalive();
		}
		doCall(mKeepalive);
	}
	
	public void setKeepalive(Call keepalive) {
		mKeepalive = keepalive;
	}
	
	public void setTerminate(Call terminate) {
		mTerminate = terminate;
	}
	
	public void setStartup(Call startup) {
		mStartup = startup;
	}
	
	public void override(InputStream is, OutputStream os) {
		if(is == null || os == null) return;
		
		mOverrideInputStream = is;
		mOverrideOutputStream = os;
	}
	
	private void doCall(Call call) throws RDISException {
		if(call == null) return;
		
		PythonInterpreter env = mParent.getPythonInterpreter();
		PyObject args[] = RDIS.safeEvalAll(call.args, env);
		mParent.callInterface(call.what, args);
		try {
			if(call.interval > 0) {
				Thread.sleep(call.interval);
			}
		}
		catch(InterruptedException e) {
			throw new RDISException("Interrupted during sleep.", e);
		}
	}

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
	
	/** Child classes override this for connection-specific behavior 
	 * @throws RDISException */
	protected abstract void onStartup(String portName) throws RDISException;
	
	/** Child classes override this for connection-specific behavior */
	protected abstract void onKeepalive();
	
	/** Child classes override this for connection-specific behavior */
	protected abstract void onTerminate();
	
	protected abstract void onWrite(byte stuff[]) throws RDISException;
	
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
				throw new RDISException("Error writing to overrideen OutputStream.", e);
			}
		}
		else {
			onWrite(stuff);
		}
		
		mParent.getLog().debug("Wrote (" + mName + "): " + toHexArray(stuff));
	}
	
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
	
	public boolean isOverridden() {
		return mOverrideInputStream != null && mOverrideOutputStream != null;
	}
	
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
