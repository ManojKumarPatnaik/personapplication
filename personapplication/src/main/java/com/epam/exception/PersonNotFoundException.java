package com.epam.exception;

public class PersonNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3840652946209233741L;
	public PersonNotFoundException(String error)
	{
		super(error);
	}

}
