package com.bits.hms.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MethodNotSupportedHandler {

	@ExceptionHandler({ HttpRequestMethodNotSupportedException.class })
	public ResponseEntity<Object> handleException() {
		System.out.println("Method not supported");
		return new ResponseEntity<Object>(HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), new HttpHeaders(),
				HttpStatus.METHOD_NOT_ALLOWED);
	}
}
