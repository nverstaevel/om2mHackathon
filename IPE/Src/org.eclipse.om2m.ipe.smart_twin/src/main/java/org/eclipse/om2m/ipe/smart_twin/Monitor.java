package org.eclipse.om2m.ipe.smart_twin;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.eclipse.om2m.core.service.CseService;
import org.eclipse.om2m.ipe.smart_twin.model.Building;
import org.eclipse.om2m.ipe.smart_twin.model.Room;
import org.eclipse.om2m.ipe.smart_twin.model.Window;
import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Monitor {

	private static final Log LOGGER = LogFactory.getLog(Activator.class);

	private CseService cse;
	private RequestSender requestSender = new RequestSender();

	private Building building;


	public Monitor() {

	}

	public void setCse(CseService cse) {
		this.cse = cse;
		this.requestSender.setCseService(this.cse);
	}

	private void loadConfiguration() {
		String configFilePath = System.getProperty("org.eclipse.om2m.ipe.ttn.config", "ttn_config.json");
		JSONParser parser = new JSONParser();

		try {
			JSONObject configObject = (JSONObject) parser.parse(new FileReader(configFilePath));
			if (configObject.containsKey("broker")) {

			}

			if (configObject.containsKey("applications")) {

			}

		} catch (FileNotFoundException e) {
			LOGGER.error("No configuration file for SMART_TWIN at: " + configFilePath, e);
			throw new IllegalArgumentException("No configuration file for SMART_TWIN IPE at: " + configFilePath, e);
		} catch (ParseException | IOException e) {
			LOGGER.error("Error on parsing configuration file.", e);
			throw new RuntimeException("Error on parsing configuration file.", e);
		}
	}

	public void start() {
		try {
			this.building = new Building("DigitalTwin", requestSender);
			Room test = new Room("203", this.getBuilding(), requestSender);
			test.addDevice(new Window("window", test, requestSender));
			this.building.addRoom(test);
		} catch (Exception e) {

		}
	}

	public void stop() {

	}

	public Building getBuilding() {
		return this.building;
	}

}
