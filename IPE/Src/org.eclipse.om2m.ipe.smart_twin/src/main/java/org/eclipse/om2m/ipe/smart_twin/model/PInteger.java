package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

public class PInteger extends Param<Integer> {

	public PInteger(String name, String init, Building b, RequestSender requestSender) {
		super(name, init, b, requestSender);
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

}
