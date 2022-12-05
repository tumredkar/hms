package com.bits.hms.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bits.hms.entity.ErrorResponseBean;

@ControllerAdvice
public class GenericExceptionHandler {

	@ExceptionHandler({ Exception.class })
	public ResponseEntity<Object> handleCookingServiceException() {
		ErrorResponseBean response = new ErrorResponseBean(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
