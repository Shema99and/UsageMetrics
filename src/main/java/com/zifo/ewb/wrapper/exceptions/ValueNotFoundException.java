package com.zifo.ewb.wrapper.exceptions;

/**
 * This class is created to give specific exception message while loading
 * properties.
 *
 */
public class ValueNotFoundException extends Exception {

	/**
	 * It is the random generated serialVersion UID
	 */
	private static final long serialVersionUID = 491292994993403558L;

	/**
	 * This method gets the message and sets it as exception message
	 * 
	 * @param message
	 */
	public ValueNotFoundException(final String message) {
		super(message);
	}
}
