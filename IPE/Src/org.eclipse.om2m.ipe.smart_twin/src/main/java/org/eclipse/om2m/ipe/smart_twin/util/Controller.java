package org.eclipse.om2m.ipe.smart_twin.util;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.datamapping.service.DataMapperService;
import org.eclipse.om2m.interworking.service.InterworkingService;
import org.eclipse.om2m.ipe.smart_twin.Monitor;
import org.eclipse.om2m.ipe.smart_twin.model.Device;
import org.eclipse.om2m.ipe.smart_twin.model.Param;
import org.eclipse.om2m.ipe.smart_twin.model.Room;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
			if (request.getQueryStrings().containsKey("op")) {
				String op = request.getQueryStrings().get("op").get(0);
				switch (op) {
				case "GET_ALL":
					return this.getAllSensorState();
				}
			}
			if (request.getQueryStrings().containsKey("room")) {
				String room = request.getQueryStrings().get("room").get(0);
				if (request.getQueryStrings().containsKey("device")) {
					String device = request.getQueryStrings().get("device").get(0);
					if (request.getQueryStrings().containsKey("op")) {
						String op = request.getQueryStrings().get("op").get(0);
						switch (op) {
						case "GET":
							return this.monitor.getBuilding().getRoom(room).getDevice(device).getLA();
						case "SET_LIGHT":
							String hex = "";
							String intensity = "";
							if (request.getQueryStrings().containsKey("intensity")) {
								intensity = request.getQueryStrings().get("intensity").get(0);
							}
							if (request.getQueryStrings().containsKey("hex")) {
								hex = '#' + request.getQueryStrings().get("hex").get(0);
							}
							if (hex.equals("")) {
								if (!intensity.equals("")) {
									return this.monitor.getBuilding().getRoom(room).getDevice(device)
											.newOperation(intensity);
								}
							} else {
								if (!intensity.equals("")) {
									return this.monitor.getBuilding().getRoom(room).getDevice(device)
											.newOperation(hex + "-" + intensity);
								} else {
									return this.monitor.getBuilding().getRoom(room).getDevice(device).newOperation(hex);
								}
							}
							return this.monitor.getBuilding().getRoom(room).getDevice(device)
									.newOperation(request.getQueryStrings().get("value").get(0));
						default:
							return this.monitor.getBuilding().getRoom(room).getDevice(device).newOperation(op);
						}
					}
				}
			} else {
				if (request.getQueryStrings().containsKey("param")) {
					String param = request.getQueryStrings().get("param").get(0);
					if (request.getQueryStrings().containsKey("op")) {
						String op = request.getQueryStrings().get("op").get(0);
						switch (op) {
						case "GET":
							return this.monitor.getParams().getParam(param).getLA();
						case "SET":
							if (request.getQueryStrings().containsKey("value")) {
								String value = request.getQueryStrings().get("value").get(0);
								return this.monitor.getParams().getParam(param).newOperation(value);
							}
							break;
						}
					}
				}
			}
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			response.setContent("At least one parameter is missing. Please refer to the documentation.");
			return response;
		} catch (

		Exception e) {
			response.setResponseStatusCode(ResponseStatusCode.BAD_REQUEST);
			response.setContent(e.getMessage());
			return response;
		}
	}

	private ResponsePrimitive getAllSensorState() {
		ResponsePrimitive response = new ResponsePrimitive();
		JSONObject mainJson = new JSONObject();
		JSONArray roomsjson = new JSONArray();
		try {
			for (String room : this.monitor.getBuilding().getRooms().keySet()) {
				Room r = this.monitor.getBuilding().getRoom(room);
				JSONObject devicesjson = new JSONObject();
				devicesjson.put("id", r.getName());
				for (String device : r.getDevices().keySet()) {
					Device d = r.getDevice(device);
					JSONParser parser = new JSONParser();
					JSONObject djson = (JSONObject) parser.parse(d.getStateJSON());
					devicesjson.put(d.getType(), djson);
				}
				roomsjson.add(devicesjson);
			}
			for (String param : this.monitor.getParams().keySet()) {
				Param p = this.monitor.getParams().getParam(param);
				JSONParser parser = new JSONParser();
				mainJson.put(p.getName(), p.toJSONString());
			}
			mainJson.put("rooms", roomsjson);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			response.setResponseStatusCode(ResponseStatusCode.INTERNAL_SERVER_ERROR);
			response.setContent(e.toString());
			return response;
		}
		response.setContent(mainJson.toJSONString());
		response.setResponseStatusCode(ResponseStatusCode.OK);
		return response;
	}

	@Override
	public String getAPOCPath() {
		return APOCPath;
	}

	public void setDataMapperService(DataMapperService service) {
		this.dms = service;
	}

}
