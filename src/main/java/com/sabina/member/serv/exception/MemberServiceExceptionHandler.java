package com.sabina.member.serv.exception;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sabina.member.serv.model.Profile;

@RestControllerAdvice
public class MemberServiceExceptionHandler {
	@ExceptionHandler(value = SignUpException.class)
	@ResponseStatus( value = HttpStatus.INTERNAL_SERVER_ERROR)
	public List<Profile> resolveMissingProfile(){
		return new ArrayList<>();
	}
}
