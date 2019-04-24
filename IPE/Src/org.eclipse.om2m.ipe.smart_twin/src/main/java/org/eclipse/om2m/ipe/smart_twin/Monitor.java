package org.eclipse.om2m.ipe.smart_twin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.smart_twin.model.Building;
import org.eclipse.om2m.ipe.smart_twin.model.Door;
import org.eclipse.om2m.ipe.smart_twin.model.Light;
import org.eclipse.om2m.ipe.smart_twin.model.Movement;
import org.eclipse.om2m.ipe.smart_twin.model.Param;
import org.eclipse.om2m.ipe.smart_twin.model.ParamAlreadyExistingException;
import org.eclipse.om2m.ipe.smart_twin.model.ParamList;
import org.eclipse.om2m.ipe.smart_twin.model.Room;
import org.eclipse.om2m.ipe.smart_twin.model.RoomAlreadyExistingException;
import org.eclipse.om2m.ipe.smart_twin.model.RoomNotExistingException;
import org.eclipse.om2m.ipe.smart_twin.model.TimeOfDay;
import org.eclipse.om2m.ipe.smart_twin.model.Window;
import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Monitor {

	private CseService cse;
	private RequestSender requestSender = new RequestSender();

	private Building building;
	private ParamList param;

	public Monitor() {

	}

	public void setCse(CseService cse) {
		this.cse = cse;
		this.requestSender.setCseService(this.cse);
	}

	private void loadConfiguration() {
		String configFilePath = System.getProperty("org.eclipse.om2m.ipe.smart_twin.config", "config.json");
		JSONParser parser = new JSONParser();

		try {
			JSONObject configObject = (JSONObject) parser.parse(new FileReader(configFilePath));

			if (configObject.containsKey("building")) {
				JSONObject building = (JSONObject) configObject.get("building");

				String buildingName = (String) building.get("name");
				this.building = new Building(buildingName, requestSender);
				this.param = new ParamList(this.building);

				if (building.containsKey("rooms")) {
					JSONArray rooms = (JSONArray) building.get("rooms");
					for (Object roomObject : rooms) {
						if (roomObject instanceof JSONObject) {
							JSONObject room = (JSONObject) roomObject;

							String roomName = (String) room.get("name");
							this.building.addRoom(new Room(roomName, this.building, requestSender));
							JSONArray devices = (JSONArray) room.get("devices");
							for (Object deviceObject : devices) {
								if (deviceObject instanceof JSONObject) {
									JSONObject device = (JSONObject) deviceObject;

									String deviceId = (String) device.get("id");
									String deviceType = (String) device.get("type");

									switch (deviceType) {
									case "window":
										this.building.getRoom(roomName).addDevice(new Window(deviceId,
												this.getBuilding().getRoom(roomName), requestSender));
										break;
									case "light":
										this.building.getRoom(roomName).addDevice(new Light(deviceId,
												this.getBuilding().getRoom(roomName), requestSender));
										break;
									case "door":
										this.building.getRoom(roomName).addDevice(new Door(deviceId,
												this.getBuilding().getRoom(roomName), requestSender));
										break;
									case "movement":
										this.building.getRoom(roomName).addDevice(new Movement(deviceId,
												this.getBuilding().getRoom(roomName), requestSender));
										break;
									}

								}
							}
						}
					}
				}
				if (building.containsKey("params")) {
					JSONArray params = (JSONArray) building.get("params");
					for (Object roomObject : params) {
						if (roomObject instanceof JSONObject) {
							JSONObject param = (JSONObject) roomObject;
							String paramId = (String) param.get("id");
							String paramType = (String) param.get("type");
							switch (paramType) {
							case "TimeOfDay":
								this.param.addParam(new TimeOfDay("TimeOfDay", this.building, this.requestSender));
								break;
							}
						}
					}
				}
			}

		} catch (FileNotFoundException e) {
			System.out.println("No configuration file for SMART_TWIN at: " + configFilePath);
			throw new IllegalArgumentException("No configuration file for SMART_TWIN IPE at: " + configFilePath, e);
		} catch (ParseException | IOException e) {
			System.out.println("Error on parsing configuration file.");
			throw new RuntimeException("Error on parsing configuration file.", e);
		} catch (RoomAlreadyExistingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RoomNotExistingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParamAlreadyExistingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Configuration loaded!");
	}

	public void start() {
		try {
			this.loadConfiguration();
		} catch (Exception e) {

		}
	}

	public void stop() {

	}

	public Building getBuilding() {
		return this.building;
	}

	public ParamList getParams() {
		return this.param;
	}

}
