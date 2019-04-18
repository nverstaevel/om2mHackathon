package org.eclipse.om2m.ipe.smart_twin.model;

/**
 * This exception is thrown when a request is sent to an unknown device.
 * 
 * @author Dr. Nicolas Verstaevel - nicolasv@uow.edu.au
 *
 */
public class DeviceNotFoundException extends Exception {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 4823864375402012177L;

	/**
	 * Constructor.
	 * 
	 * @param message The error message.
	 */
	public DeviceNotFoundException(String message) {
		super("The device {" + message + "} is not existing.");
	}

}
