package org.eclipse.om2m.ipe.smart_twin.model;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

/**
 * @author Dr. Nicolas Verstaevel - nicolasv@uow.edu.au
 *
 */
public class Room {

	private Map<String, Device> deviceMap = new HashMap<String, Device>();

	private String name;
	private RequestSender requestSender;
	private Building building;

	public Room(String name, Building building, RequestSender requestSender) {
		this.name = name;
		this.building = building;
		this.requestSender = requestSender;
		this.createCNT();
	}

	private void createCNT() {
		ResponsePrimitive response = requestSender.getRequest(this.building.getAEPrefix() + "/" + this.getName());
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			Container container = new Container();
			container.setName(this.getName());
			requestSender.createContainer(this.building.getAEPrefix(), container);
		}
	}

	public boolean addDevice(Device<?> device) {
		if (deviceMap.containsKey(device.getName())) {
			return false;
		} else {
			deviceMap.put(device.getName(), device);
		}
		return true;
	}

	public String getName() {
		return this.name;
	}

	public Device<?> getDevice(String name) throws DeviceNotFoundException {
		if (!this.deviceMap.containsKey(name)) {
			throw new DeviceNotFoundException(name);
		}
		return deviceMap.get(name);
	}

	public String getCSEPrefix() {
		return this.building.getAEPrefix() + "/" + this.getName();
	}

}
