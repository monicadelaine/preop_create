package edu.ua.cs.robotics.rdis;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.StreamConnection;

import org.json.JSONException;

public class BluetoothWallFollow implements RDIS.Callback {
	private boolean mCollided = false;
	
	private long mStopBackingUp = 0;
	
	/**
	 * Completes a wall-follow routine for a certain amount of time.
	 * @param rdisFile the rdis file to use to drive the robot
	 * @param macAddress the mac address of the robot
	 * @param ticks number of ticks to perform
	 * @throws JSONException if there was a syntax error
	 * @throws IOException if there was an IO error
	 */
	public void go(String rdisFile, String macAddress, int ticks) throws JSONException, IOException {
		// Requires mac address without the colons.
		macAddress = macAddress.replace(":", "");
		
		// Set up RDIS.
		RDIS rdis = RDIS.load(rdisFile);
		rdis.setLogLevel(Log.DEBUG);
		rdis.setCallback(this);
		
		// Set up Bluetooth connection.
		String url = "btspp://" + macAddress + ":1;authenticate=false;encrypt=false;master=true";
		StreamConnection c = (StreamConnection) Connector.open(url);
		InputStream is = c.openInputStream();
		OutputStream os = c.openOutputStream();
		
		// Override the "btserial" connection in our model with the Input/OutputStream from the
		// bluetooth connection. This is a hack since we don't support Bluetooth connections yet.
		rdis.overrideConnection("btserial", is, os);
		
		try {
			// Startup stuff!
			rdis.startup(macAddress);
			
			for(int i = 0; i < ticks; i++) {
				// Consume any collision messages we may have seen.
				handleCollision();
				
				// By default, drive forward.
				DifferentialSpeed msg = new DifferentialSpeed(0.2f, 0.0f);
				
				// If we are scheduled to back up, back up and turn a little.
				if(backingUp()) {
					msg = new DifferentialSpeed(-0.2f, -0.4f);
				}
				
				// Periodics
				rdis.tick();
				
				// Send the drive message.
				rdis.callDomainInterface("set_velocity", msg);
			}
		}
		catch(RDISException e) {
			// Print the error.
			e.printStackTrace();
		}
		finally {
			try {
				rdis.terminate();
			} catch (RDISException e) {
				e.printStackTrace();
			}
			
			is.close();
			os.close();
			c.close();
		}
	}
	
	private void handleCollision() {
		if(mCollided) {
			System.out.println("Collision detected!");
			mCollided = false;
			mStopBackingUp = System.currentTimeMillis() + 2000;
		}
	}
	
	private boolean backingUp() {
		return System.currentTimeMillis() < mStopBackingUp;
	}

	public void onMessageReceived(String name, DomainAdapter contents) {
		if(name.equals("detect_bump")) {
			float dist = contents.getFloat(Range.DISTANCE);
			if(dist == 0) {
				mCollided = true;
			}
		}
	}
	
	public static void main(String args[]) {
		if(args.length < 2) {
			System.out.println("Usage: java BluetoothWallFollow <rdis-file> <mac address>");
			System.exit(1);
			return;
		}
		
		BluetoothWallFollow w = new BluetoothWallFollow();
		try {
			
			// This is here as a hack. There is an existing issue with the create description that
			// reading the bumper will only work if we start the controller, stop it, and start it
			// again. 
			// So if we start it up with no ticks, this also solves the problem.
			w.go(args[0], args[1], 0);
			
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			// This call actually does the demo.
			w.go(args[0], args[1], 10);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
