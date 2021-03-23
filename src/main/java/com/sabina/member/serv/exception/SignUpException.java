package com.sabina.member.serv.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus( value = HttpStatus.NOT_FOUND)
public class SignUpException extends Exception{
	private String message;
	
	public SignUpException() {}
	
	public SignUpException(String message) {
		this.message=message;
	}
	
	@Override
	public String getMessage() {
		return message;
	}
	
	
}
