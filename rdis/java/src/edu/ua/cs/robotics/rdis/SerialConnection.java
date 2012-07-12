package edu.ua.cs.robotics.rdis;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Represents a connection over the serial port to a robot. This generalizes
 * anything that looks like a serial connection to the hosting computer's
 * operating system.
 *
 */
public class SerialConnection extends Connection {

	/** Baud for the serial connection. */
	private int mBaud;

	/** Instance of the serial connection. */
	private SerialPort mSerialPort;

	/**
	 * Constructs a SerialConnection
	 * @param parent RDIS parent object
	 * @param name name of the connection
	 * @param baud baud for the serial connection
	 */
	public SerialConnection(RDIS parent, String name, int baud) {
		super(parent, name);
		mBaud = baud;
	}

	public void onStartup(String portName) throws RDISException {
		try {
			// Boilerplate setup logic per the Java Communications API.
			CommPortIdentifier portIdentifier = CommPortIdentifier
					.getPortIdentifier(portName);
			CommPort commPort = portIdentifier.open(RDIS.APP_NAME, DEFAULT_TIMEOUT);
			mSerialPort = (SerialPort) commPort;
			
			// TODO: Possibly configurable section alert!
			// Currently we assume a bit width of 8, 1 stop bit, and no parity because these are
			// common settings. Beware, there could be exceptions to this!
			mSerialPort.setSerialPortParams(mBaud, SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

		} catch (NoSuchPortException e) {
			throw new RDISException(String.format("No such port: %s",
					new Object[] { portName }), e);
		} catch (PortInUseException e) {
			throw new RDISException(String.format("Port in use: %s",
					new Object[] { portName }), e);
		} catch (UnsupportedCommOperationException e) {
			throw new RDISException(String.format("Invalid parameters set: %s",
					new Object[] { portName }), e);
		}
	}

	public byte[] onRead(int num) throws RDISException {
		try {
			InputStream is = mSerialPort.getInputStream();
			byte b[] = new byte[num];
			is.read(b);

			mParent.getLog().debug("Read (" + mName + "): " + toHexArray(b));
			return b;
		} catch (IOException e) {
			throw new RDISException("Error reading from connection `" + mName + "`", e);
		}
	}

	public void onWrite(byte bytes[]) throws RDISException {
		try {
			OutputStream os = mSerialPort.getOutputStream();
			os.write(bytes);
		} catch (IOException e) {
			throw new RDISException("Error writing on connection `" + mName + "`", e);
		}
	}

	protected void onKeepalive() {
		// Nothing to do.
	}

	protected void onTerminate() {
		if(mSerialPort != null) {
			mSerialPort.close();
		}
	}
	
	public static final int
		DEFAULT_TIMEOUT = 2000;

}
