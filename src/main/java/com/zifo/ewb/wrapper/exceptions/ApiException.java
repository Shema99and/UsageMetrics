package com.zifo.ewb.wrapper.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
 
public class ApiException extends Exception {
 
	
	private static final long serialVersionUID = -7633352480352928607L;
	final String message;
	final HttpStatusCode code;
	final transient Object data;
 
	public ApiException(String message) {
		this.message = message;
		this.code = HttpStatus.INTERNAL_SERVER_ERROR;
		this.data = null;
	}
 
	public ApiException(String message, HttpStatusCode code) {
		this.message = message;
		this.code = code;
		this.data = null;
	}
 
	public ApiException(String message, HttpStatusCode code, Object data) {
		this.message = message;
		this.code = code;
		this.data = data;
	}
 
	@Override
	public String getMessage() {
		return message;
	}
 
	public HttpStatusCode getCode() {
		return code;
	}
 
	public Object getData() {
		return data;
	}
 
	@Override
	public String toString() {
		return ("Exception Occurred : " + this.message);
	}
}