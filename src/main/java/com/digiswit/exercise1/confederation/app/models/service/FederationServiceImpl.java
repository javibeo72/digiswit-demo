package com.digiswit.exercise1.confederation.app.models.service;

import com.digiswit.exercise1.confederation.app.models.dao.FederationDao;
import com.digiswit.exercise1.confederation.app.models.entity.Federation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FederationServiceImpl implements IFederationService {

	@Autowired
	private FederationDao federationDao;

	@Override
	public Federation findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Federation save(Federation federation) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Federation findByAcronym(String acronym) {
		return federationDao.findByAcronym(acronym);
	}
	

	

}
