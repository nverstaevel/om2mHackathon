package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

public class TimeOfDay extends Param<Integer> implements Runnable {

	private Integer wait = 1000;
	private Integer mod = 1440;
	private Thread t;

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
		this.setParam((this.getParam() + 1) % mod);
	}
	
	
	@Override
	public void run() {
		while (true) {
			try {
				Thread.sleep(wait);
				increment();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
