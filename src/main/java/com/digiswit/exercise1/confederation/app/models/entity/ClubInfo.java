package com.digiswit.exercise1.confederation.app.models.entity;

import java.io.Serializable;

public class ClubInfo implements Serializable {
	
	private static final long serialVersionUID = -8787414177130970300L;

	private String officialName;
	
	private String popularName;
		
	private String federation;
		
	public ClubInfo(String officialName, String popularName, String federation) {
		this.officialName = officialName;
		this.popularName = popularName;
		this.federation = federation;
	}

	public String getOfficialName() {
		return officialName;
	}

	public void setOfficialName(String officialName) {
		this.officialName = officialName;
	}

	public String getPopularName() {
		return popularName;
	}

	public void setPopularName(String popularName) {
		this.popularName = popularName;
	}

	public String getFederation() {
		return federation;
	}

	public void setFederation(String federation) {
		this.federation = federation;
	}

}
