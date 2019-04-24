package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

/**
 * This class describes a Window. A door has two possible states : - false: the
 * window is closed - true: the window is opened
 * 
 * @author Dr. Nicolas Verstaevel - nicolasv@uow.edu.au
 * @version 0.1
 */
public class Window extends Device<Boolean> {

	public Window(String id, Room room, RequestSender requestSender) {
		super(id, room, requestSender);
		this.setState(false);
	}

	@Override
	Boolean stateFromString(String state) {
		switch (state) {
		case "SET_OPEN":
			return true;
		case "SET_CLOSE":
			return false;
		default:
			throw new IllegalArgumentException("The value {" + state + "} is not valid for a device of type "
					+ this.getClass().toString() + ". Please refer to the documentation.");
		}
	}
	
	@Override
	public String getType() {
		return "window";
	}

	@Override
	String getStringState() {
		if (this.getState()) {
			return "OPENED";
		} else {
			return "CLOSED";
		}
	}

}
