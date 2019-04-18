package org.eclipse.om2m.ipe.smart_twin.util;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.ipe.smart_twin.Monitor;

public class Controller implements InterworkingService {

	public static String APOCPath = "DigitalTwin";

	private DataMapperService dms;

	private Monitor monitor;

	public Controller(Monitor monitor) {
		this.monitor = monitor;
		System.out.println("Controller for DigitalTwin created.");
	}

	@Override
	public ResponsePrimitive doExecute(RequestPrimitive request) {
		System.out.println(request.toString());
		ResponsePrimitive response = new ResponsePrimitive(request);
		try {
			if (request.getQueryStrings().containsKey("room")) {
				String room = request.getQueryStrings().get("room").get(0);
				if (request.getQueryStrings().containsKey("device")) {
					String device = request.getQueryStrings().get("device").get(0);
					if (request.getQueryStrings().containsKey("op")) {
						String op = request.getQueryStrings().get("op").get(0);
						switch (op) {
						case "GET":
							return this.monitor.getBuilding().getRoom(room).getDevice(device).getLA();
						default:
							return this.monitor.getBuilding().getRoom(room).getDevice(device).newOperation(op);
						}
					}
				}
			}
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			response.setContent("At least one parameter is missing. Please refer to the documentation.");
			return response;
		} catch (Exception e) {
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			response.setContent(e.getMessage());
			return response;
		}
	}

	@Override
	public String getAPOCPath() {
		return APOCPath;
	}

	public void setDataMapperService(DataMapperService service) {
		this.dms = service;
	}

}
