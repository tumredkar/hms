package com.bits.hms.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import lombok.Data;

@Entity
@Table(name = "hotel")
@FilterDef(name="filterByCity", parameters={@ParamDef(name="city", type="string")})
@Data
public class Hotel implements Serializable {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "name")
	private String name;
	@Column(name = "city")
	private String city;
	@Column(name = "state")
	private String state;
//	@Column(name="")

}