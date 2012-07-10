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

public class SerialConnection extends Connection {

	private int mBaud;

	private SerialPort mSerialPort;

	public SerialConnection(RDIS parent, String name, int baud) {
		super(parent, name);
		mBaud = baud;
	}

	public void onStartup(String portName) throws RDISException {
		try {
			CommPortIdentifier portIdentifier = CommPortIdentifier
					.getPortIdentifier(portName);
			CommPort commPort = portIdentifier.open("RDIS", 2000);
			mSerialPort = (SerialPort) commPort;
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

	public byte[] read(int num) throws RDISException {
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

	public void write(byte bytes[]) throws RDISException {
		try {
			OutputStream os = mSerialPort.getOutputStream();
			os.write(bytes);
			mParent.getLog().debug("Wrote (" + mName + "): " + toHexArray(bytes));
			
		} catch (IOException e) {
			throw new RDISException("Error writing on connection `" + mName + "`", e);
		}
	}

	protected void onKeepalive() {
		// TODO Auto-generated method stub
	}

	protected void onTerminate() {
		mSerialPort.close();
	}

}
