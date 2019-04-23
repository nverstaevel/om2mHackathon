package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

public class TimeOfDay extends Param<Integer> implements Runnable {

	private Integer wait = 1000;
	private int mod = 1440;
	private Thread t;

	public TimeOfDay(String name, Building b, RequestSender requestSender) {
		super(name, b, requestSender);
		this.setParam(0);
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
