package com.app.customException;

public class ResourceNotFoundException extends RuntimeException {	
	private static final long serialVersionUID = -4965918306942501966L;

	public ResourceNotFoundException(String mesg) {
		super(mesg);
	}
}
