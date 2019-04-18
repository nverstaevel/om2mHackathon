package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.commons.constants.ResponseStatusCode;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

/**
 * This class describes a generic device.
 * 
 * @author Dr. Nicolas Verstaevel - nicolasv@uow.edu.au
 * @version 0.1
 *
 * @param <T> the type of the device states.
 */
public abstract class Device<T> {

	private String name;
	private T state;
	private Room myRoom;

	private RequestSender requestSender;

	/**
	 * Constructor to create a new device.
	 * 
	 * @param name          The name of the device.
	 * @param myRoom        The room in which the device is located.
	 * @param requestSender A reference to a RequestSender object.
	 */
	public Device(String name, Room myRoom, RequestSender requestSender) {
		this.name = name;
		this.myRoom = myRoom;
		this.requestSender = requestSender;
		this.createCNT();
	}

	/**
	 * Get the container(CNT) prefix.
	 * 
	 * @return The link to the container.
	 */
	public String getCNTPrefix() {
		return myRoom.getCSEPrefix() + "/" + this.getName();
	}

	/**
	 * Set the state of the device to a new state and create a new CIN.
	 * 
	 * @param state The new state.
	 * @return The result of the creation of the new CIN.
	 */
	protected ResponsePrimitive setState(T state) {
		this.state = state;
		ContentInstance contentInstance = new ContentInstance();
		contentInstance.setContent(this.toString());
		return requestSender.createContentInstance(this.getCNTPrefix(), contentInstance);
	}

	/**
	 * Get the last state of the device.
	 * 
	 * @return The result of the GET /la request.
	 */
	public ResponsePrimitive getLA() {
		System.out.println(getCNTPrefix() + "/la");
		return requestSender.getRequest(getCNTPrefix() + "/la");
	}

	public String toString() {
		return "{\"room\":\"" + this.getRoom().getName() + "\", \"device\":\"" + this.getName() + "\", \"state\":\""
				+ this.getStringState() + "\"}";
	}

	/**
	 * Get the room in which the device is.
	 * 
	 * @return The reference to the room.
	 */
	public Room getRoom() {
		return this.myRoom;
	}

	/**
	 * Perform a new operation on the device.
	 * 
	 * @param op The string representation of the operation to perform.
	 * @return The result of the operation.
	 */
	public ResponsePrimitive newOperation(String op) {
		return this.setState(this.stateFromString(op));
	}

	/**
	 * Create the container (CNT) of the device.
	 */
	private void createCNT() {
		ResponsePrimitive response = requestSender.getRequest(this.getCNTPrefix());
		if (!response.getResponseStatusCode().equals(ResponseStatusCode.OK)) {
			Container container = new Container();
			container.setName(this.getName());
			requestSender.createContainer(this.myRoom.getCSEPrefix(), container);
		}
	}

	/**
	 * Return the type associated to the string representation of a device's state.
	 * 
	 * @param state The string representation of the device state.
	 * @return An object modelling the state of the device.
	 */
	abstract T stateFromString(String state);

	/**
	 * Get a string description of the device state.
	 * 
	 * @return A string description the current device state.
	 */
	abstract String getStringState();

	/**
	 * Get the current state of the device.
	 * 
	 * @return The current state of the device.
	 */
	public T getState() {
		return this.state;
	}

	/**
	 * Get the device name.
	 * 
	 * @return The name of the device.
	 */
	public String getName() {
		return name;
	}
}
