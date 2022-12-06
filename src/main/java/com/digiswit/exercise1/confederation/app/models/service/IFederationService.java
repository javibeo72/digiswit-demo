package com.digiswit.exercise1.confederation.app.models.service;

import com.digiswit.exercise1.confederation.app.models.entity.Federation;

public interface IFederationService {
	
	public Federation findById(Long id);
	
	public Federation save(Federation federation);
	
	public void delete(Long id);
	
	public Federation findByAcronym(String acronym);
		
}
