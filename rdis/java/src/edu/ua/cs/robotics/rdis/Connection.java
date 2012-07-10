package edu.ua.cs.robotics.rdis;


import java.io.IOException;
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
	
	protected Connection(RDIS parent, String name) {
		mParent = parent;
		mName = name;
	}

	public void startup(String portName) throws RDISException {
		if (mInitialized == false){
			mInitialized = true;
		}
		
		// Initializes the port.
		onStartup(portName);
		
		doCall(mStartup);
	}
	
	public void keepalive() throws RDISException {
		onKeepalive();
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
			onTerminate();
		}
	}
	
	/** Child classes override this for connection-specific behavior 
	 * @throws RDISException */
	protected abstract void onStartup(String portName) throws RDISException;
	
	/** Child classes override this for connection-specific behavior */
	protected abstract void onKeepalive();
	
	/** Child classes override this for connection-specific behavior */
	protected abstract void onTerminate();
	
	/**
	 * Gets the name of this connection.
	 * @return the name of the connection
	 */
	public String getName() {
		return mName;
	}
	
	public void write(byte stuff[]) throws RDISException {
		/*
		 * Dummy method for writing to an abstract connection.
		 */

		String hexArray = toHexArray(stuff);
		System.out.println("write( [" + hexArray + "] )");
	}
	
	public byte[] read(int num) throws RDISException {
		/*
		 * Dummy method for reading bytes.
		 */
		
		System.out.println("SUPER");
		byte bytes[] = new byte[ num ];
		mRNG.nextBytes(bytes);
		
		System.out.println("read( [" + toHexArray(bytes) + "] )");
		
		return bytes;
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
