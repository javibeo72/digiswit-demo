package com.digiswit.exercise1.confederation.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.digiswit.exercise1.confederation.app.models.entity.Federation;

public interface FederationDao extends CrudRepository<Federation, Long>{
	
	public Federation findByAcronym(String acronym);

}
