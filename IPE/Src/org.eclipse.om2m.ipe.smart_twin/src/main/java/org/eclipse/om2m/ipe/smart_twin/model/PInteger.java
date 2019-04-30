package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
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

	@Override
	public ResponsePrimitive set_auto_on(String value) {
		ResponsePrimitive response = new ResponsePrimitive(); 
		response.setResponseStatusCode(ResponseStatusCode.OPERATION_NOT_ALLOWED);
		return response;
	}

	@Override
	public ResponsePrimitive set_auto_off() {
		ResponsePrimitive response = new ResponsePrimitive(); 
		response.setResponseStatusCode(ResponseStatusCode.OPERATION_NOT_ALLOWED);
		return response;
	}

	@Override
	public ResponsePrimitive set_incr(String value) {
		ResponsePrimitive response = new ResponsePrimitive(); 
		response.setResponseStatusCode(ResponseStatusCode.OPERATION_NOT_ALLOWED);
		return response;
	}

}
