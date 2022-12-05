package com.bits.hms.handlers;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bits.hms.entity.ErrorResponseBean;
import com.bits.hms.exception.HmsException;


@ControllerAdvice
public class HmsExceptionHandler {

	@ExceptionHandler({ HmsException.class })
	public ResponseEntity<Object> handleCookingServiceException(HmsException cse) {
		ErrorResponseBean response = new ErrorResponseBean(cse.getLocalizedMessage());
		return new ResponseEntity<Object>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
