/*******************************************************************************
 * Copyright (c) 2013-2016 LAAS-CNRS (www.laas.fr)
 * 7 Colonel Roche 31077 Toulouse - France
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Initial Contributors:
 *     Thierry Monteil : Project manager, technical co-manager
 *     Mahdi Ben Alaya : Technical co-manager
 *     Samir Medjiah : Technical co-manager
 *     Khalil Drira : Strategy expert
 *     Guillaume Garzone : Developer
 *     François Aïssaoui : Developer
 *
 * New contributors :
 *******************************************************************************/
package org.eclipse.om2m.ipe.smart_twin.util;

import java.math.BigInteger;

import org.eclipse.om2m.commons.constants.Constants;
import org.eclipse.om2m.commons.constants.MimeMediaType;
import org.eclipse.om2m.commons.constants.Operation;
import org.eclipse.om2m.commons.constants.ResourceType;
import org.eclipse.om2m.commons.resource.AE;
import org.eclipse.om2m.commons.resource.AccessControlPolicy;
import org.eclipse.om2m.commons.resource.Container;
import org.eclipse.om2m.commons.resource.ContentInstance;
import org.eclipse.om2m.commons.resource.RequestPrimitive;
import org.eclipse.om2m.commons.resource.Resource;
import org.eclipse.om2m.commons.resource.ResponsePrimitive;
import org.eclipse.om2m.commons.resource.Subscription;
import org.eclipse.om2m.core.service.CseService;

public class RequestSender {
	
	private CseService cse;
	
	public void setCseService(CseService cse) {
		this.cse = cse;
	}

	public ResponsePrimitive createResource(String targetId, Resource resource, int resourceType){
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(targetId);
		request.setResourceType(BigInteger.valueOf(resourceType));
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setReturnContentType(MimeMediaType.JSON);
		request.setContent(resource);
		request.setOperation(Operation.CREATE);
		return cse.doRequest(request);
	}
	
	public ResponsePrimitive createSUB(String targetId, Subscription subscription) {
		RequestPrimitive request = new RequestPrimitive();
		request.setOperation(Operation.CREATE);
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(targetId);
		request.setResourceType(ResourceType.SUBSCRIPTION);
		request.setReturnContentType(MimeMediaType.JSON);
		request.setRequestContentType(MimeMediaType.OBJ);
		request.setContent(subscription);
		return cse.doRequest(request);
	}
	
	public ResponsePrimitive createAE(AE resource){
		return createResource("/" + Constants.CSE_ID, resource, ResourceType.AE);
	}
	
	public ResponsePrimitive createContainer(String targetId, Container resource){
		return createResource(targetId, resource, ResourceType.CONTAINER);
	}
	
	public ResponsePrimitive createContentInstance(String targetId, ContentInstance resource){
		return createResource(targetId, resource, ResourceType.CONTENT_INSTANCE);
	}
	
	public ResponsePrimitive createAccessControlPolicy(String targetId, AccessControlPolicy resource){
		return createResource(targetId, resource, ResourceType.ACCESS_CONTROL_POLICY);
	}

	public ResponsePrimitive getRequest(String targetId){
		RequestPrimitive request = new RequestPrimitive();
		request.setFrom(Constants.ADMIN_REQUESTING_ENTITY);
		request.setTo(targetId);
		request.setReturnContentType(MimeMediaType.JSON);
		request.setOperation(Operation.RETRIEVE);
		request.setRequestContentType(MimeMediaType.OBJ);
		return cse.doRequest(request);
	}
	
}
