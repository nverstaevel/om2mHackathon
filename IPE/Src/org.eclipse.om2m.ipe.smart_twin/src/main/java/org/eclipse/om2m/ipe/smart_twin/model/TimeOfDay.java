package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

public class TimeOfDay extends Param<Integer> implements Runnable {

	private Integer wait = 100;
	private Integer mod = 1440;
	private Thread t;
	private Integer inc = 1;
	private boolean exit = false;

	public TimeOfDay(String name, String init, Building b, RequestSender requestSender) {
		super(name, init, b, requestSender);
		this.newOperation(init);
		t = new Thread((Runnable) this);
		t.start();
	}

	@Override
	Integer paramFromString(String state) {
		try {
			return Integer.parseInt(state);
		} catch (Exception e) {
			throw new IllegalArgumentException("The value {" + state + "} is not valid for a device of type "
					+ this.getClass().toString() + ". Please refer to the documentation.");
		}
	}

	private void increment() {
		this.setParam((this.getParam() + this.inc) % mod);
	}

	@Override
	public void run() {
		while (!exit) {
			try {
				Thread.sleep(wait);
				increment();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public ResponsePrimitive set_auto_on(String value) {
		ResponsePrimitive response = new ResponsePrimitive();
		try {
			if (exit) {
				this.wait = Integer.valueOf(value);
				t.start();
				response.setResponseStatusCode(ResponseStatusCode.OK);
				response.setContent(this.getName() + " is now automatically incremented by {" + this.inc + "} every {"
						+ this.wait + "} ms.");
				return response;
			} else {
				response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
				response.setContent(this.getName() + " is already automatically incremented.");
				return response;
			}
		} catch (Exception e) {
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			response.setContent(value + " is not a good arguement. Please refer to the manual.");
			return response;
		}
	}

	@Override
	public ResponsePrimitive set_auto_off() {
		ResponsePrimitive response = new ResponsePrimitive();
		if (!exit) {
			this.exit = true;
			response.setResponseStatusCode(ResponseStatusCode.OK);
			response.setContent(this.getName() + " is now stopped.");
			return response;
		} else {
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			response.setContent(this.getName() + " is already automatically incremented.");
			return response;
		}
	}

	@Override
	public ResponsePrimitive set_incr(String value) {
		ResponsePrimitive response = new ResponsePrimitive();
		try {
			this.inc = (Integer.valueOf(value));
			response.setResponseStatusCode(ResponseStatusCode.OK);
			response.setContent(this.getName() + " new increment is {" + this.inc + "}");
			return response;
		} catch (Exception e) {
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			response.setContent("The value " + value + " is not a good argurment. Please refer to the manual.");
			return response;
		}

	}

}
