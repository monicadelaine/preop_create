package edu.ua.cs.robotics.rdis;

import java.io.FileNotFoundException;

import org.json.JSONException;

public class WallFollow implements RDIS.Callback {
	private boolean mCollided = false;
	
	private long mStopBackingUp = 0;
	
	public void go(String rdisFile, String portName) throws FileNotFoundException, JSONException {
		RDIS rdis = RDIS.load(rdisFile);
		rdis.setLogLevel(Log.DEBUG);
		
		rdis.setCallback(this);
		
		try {
			
			rdis.startup(portName);
			
			while(true) {
				rdis.tick();
				handleCollision();
				DifferentialSpeed msg = new DifferentialSpeed(0.2f, 0.0f);
				
				if(backingUp()) {
					msg = new DifferentialSpeed(-0.2f, -0.4f);
				}
				
				rdis.callDomainInterface("set_velocity", msg);
			}
			
		}
		catch(RDISException e) {
			e.printStackTrace();
		}
		finally {
			try {
				rdis.terminate();
			} catch (RDISException e) {
				e.printStackTrace();
			}
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
			System.out.println("Usage: java WallFollow <rdis-file> <port name>");
			System.exit(1);
			return;
		}
		
		WallFollow w = new WallFollow();
		try {
			w.go(args[0], args[1]);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
