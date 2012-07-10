package edu.ua.cs.robotics.rdis;

import java.io.IOException;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import edu.ua.cs.robotics.rdis.RDIS.Callback;

public class DomainInterface {
	RDIS mParent;
	String mName;
	Call mLocalInterface;
	String mParameters[];
	
	public DomainInterface(RDIS parent, String name, Call localInterface, String parameters[]){
		mParent = parent;
		mName = name;
		mLocalInterface = localInterface;
		mParameters = parameters;
		
	}

	public void call(DomainAdapter domainAdapter) throws RDISException {
		PythonInterpreter env = mParent.getPythonInterpreter(); 
		domainAdapter.inject(env);
		PyObject values[] = RDIS.safeEvalAll(mLocalInterface.args, env);
		mParent.callInterface(mLocalInterface.what, values);
	}
	
	public String getName() {
		return mName;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.print("== INTRO ==\n");
		try{
			RDIS rdis = new RDIS();
			
//			DomainInterface dInt = new DomainInterface(
//					rdis,
//					"set_velocity",
//					"drive",
//					new String[]{"<linear>", "<linear/angular if angular != 0 else 32.767>"},
//					new String[]{"angular", "linear"});
//			
//			Interface iface = new Interface(rdis,
//					"drive",
//					null,
//					new String[] {"linear", "radius"},
//					"dom_output",
//					new Call[] {
//						new Call("some_primitive", new String[] {"<linear*1000>", "<radius*1000>"})
//					}
//			);
			
			Primitive p = Primitive.newFixedWidth(rdis,
					"some_primitive", 
					new String[] {	"velocity", "radius" },
					"btserial",
					new String[] { "<137>", "<velocity>", "<radius>" },
					null,
					">Bhh",
					"B"); 		
			
			DomainOutput domOutput = new DomainOutput(rdis, "dom_output");
			
			rdis.addConnection(new SerialConnection(rdis, "btserial", 57600));
//			rdis.addDomainInterface(dInt);
//			rdis.addInterface(iface);
			rdis.addPrimitive(p);
			rdis.addDomainOutput(domOutput);
			domOutput.addReturns("foo", "<3.14 * 2>");
			
			rdis.setCallback(new Callback() {
				public void onMessageReceived(String name, DomainAdapter contents) {
					System.out.println("Triggered: " + name);
					System.out.println("foo: " + contents.getFloat("foo"));
				}
			});
			
			rdis.callDomainInterface("set_velocity", new DifferentialSpeed(2.0f, 0.0f));
		} catch (RDISException e) {
			e.printStackTrace();
		}
		finally{
			System.out.print("\n== FINIS ==");
		}

	}

}
