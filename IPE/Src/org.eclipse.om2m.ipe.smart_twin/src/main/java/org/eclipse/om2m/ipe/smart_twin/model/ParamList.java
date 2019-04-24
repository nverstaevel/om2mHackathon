package org.eclipse.om2m.ipe.smart_twin.model;

import java.util.HashMap;
import java.util.Set;

import org.eclipse.om2m.commons.resource.ResponsePrimitive;

public class ParamList {

	private HashMap<String, Param> paramList;
	private Building b;
	
	public ParamList(Building b) {
		this.paramList = new HashMap<String, Param>();
;	}
	
	public void addParam(Param p) throws ParamAlreadyExistingException {
		if (paramList.containsKey(p.getName())) {
			throw new ParamAlreadyExistingException();
		} else {
			paramList.put(p.getName(), p);
		}
	}

	public Param getParam(String name) throws ParamNotExistingException {
		if(!this.paramList.containsKey(name)) {
			throw new ParamNotExistingException(name);
		}
		return this.paramList.get(name);
	}
	
	public  Set<String> keySet() {
		return this.paramList.keySet(); 
	}

}
