package com.digiswit.exercise1.confederation.app.models.entity;

import java.io.Serializable;


public class PlayerMinInfo implements Serializable {

	private static final long serialVersionUID = 8761203191347238722L;

	private Long Id;

	private String givenName;

	private String familyName;

	public PlayerMinInfo(Long id, String givenName, String familyName) {
		this.Id = id;
		this.givenName = givenName;
		this.familyName = familyName;
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public String getGivenName() {
		return givenName;
	}

	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	public String getFamilyName() {
		return familyName;
	}

	public void setFamilyName(String familyName) {
		this.familyName = familyName;
	}

	
}
