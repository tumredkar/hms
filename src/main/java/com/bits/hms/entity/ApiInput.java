package com.bits.hms.entity;

import javax.persistence.Transient;

import lombok.Data;

@Data
public class ApiInput {

	@Transient
	private String username;
	
	@Transient
	private String pasword;
}
