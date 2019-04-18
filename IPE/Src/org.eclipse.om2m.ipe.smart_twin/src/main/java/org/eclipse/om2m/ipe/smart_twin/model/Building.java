package org.eclipse.om2m.ipe.smart_twin.model;

import java.util.HashMap;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.ipe.smart_twin.util.Controller;
import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

public class Building {

	private HashMap<String, Room> rooms = new HashMap<String, Room>();
	private String name;

	private RequestSender requestSender;
	private String csePrefix = "/" + Constants.CSE_ID + "/" + Constants.CSE_NAME;

	public Building(String name, RequestSender requestSender) {
		this.name = name;
		this.requestSender = requestSender;
		this.createAE();
	}

	public String getAEPrefix() {
		return this.csePrefix + "/" + this.getName();
	}

	private void createAE() {
		ResponsePrimitive response = requestSender.getRequest(this.getAEPrefix());
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			AE ae = new AE();
			ae.setName(this.getName());
			ae.setRequestReachability(true);
			ae.setAppID(this.getName());
			ae.getPointOfAccess().add(Controller.APOCPath);
			requestSender.createAE(ae);
		}
	}

	public String getName() {
		return this.name;
	}

	public void addRoom(Room room) throws RoomAlreadyExistingException {
		if (rooms.containsKey(room.getName())) {
			throw new RoomAlreadyExistingException();
		} else {
			rooms.put(room.getName(), room);
		}
	}

	public Room getRoom(String name) throws RoomNotExistingException {
		if(!this.rooms.containsKey(name)) {
			throw new RoomNotExistingException(name);
		}
		return this.rooms.get(name);
	}
}