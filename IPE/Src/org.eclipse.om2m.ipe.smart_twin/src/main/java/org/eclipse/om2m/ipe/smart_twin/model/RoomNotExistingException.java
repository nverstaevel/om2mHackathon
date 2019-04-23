package org.eclipse.om2m.ipe.smart_twin.model;

/**
 * This exception is thrown when a user try to access to a room that is not
 * existing.
 * 
 * @author Dr. Nicolas Verstaevel - nicolasv@uow.edu.au
 *
 */
public class RoomNotExistingException extends Exception {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = -2556105774084904105L;

	/**
	 * Constructor.
	 * 
	 * @param name The name of the room.
	 */
	public RoomNotExistingException(String name) {
		super("The room {" + name + "} does not existing.");
	}

}
