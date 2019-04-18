package org.eclipse.om2m.ipe.smart_twin.model;

import org.eclipse.om2m.ipe.smart_twin.util.RequestSender;

/**
 * This class describes a Light. A ligh can be controlled by its intensity.
 * 
 * @author Dr. Nicolas Verstaevel - nicolasv@uow.edu.au
 * @version 0.1
 */
public class Light extends Device<Double> {

	private Double minValue = 0.0;
	private Double maxValue = 100.0;

	public Light(String id, Room room, RequestSender requestSender) {
		super(id, room, requestSender);
		this.setState(0.0);
	}

	@Override
	Double stateFromString(String state) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	String getStringState() {
		// TODO Auto-generated method stub
		return null;
	}

}
