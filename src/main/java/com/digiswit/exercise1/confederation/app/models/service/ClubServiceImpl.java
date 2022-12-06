package com.digiswit.exercise1.confederation.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiswit.exercise1.confederation.app.models.dao.ClubDao;
import com.digiswit.exercise1.confederation.app.models.dao.FederationDao;
import com.digiswit.exercise1.confederation.app.models.dao.UserClubDao;
import com.digiswit.exercise1.confederation.app.models.entity.Club;
import com.digiswit.exercise1.confederation.app.models.entity.Federation;
import com.digiswit.exercise1.confederation.app.models.entity.UserClub;

@Service
public class ClubServiceImpl implements IClubService {
	
	@Autowired
	private ClubDao clubDao;
	
	@Autowired
	private FederationDao federationDao;
	
	@Autowired
	private UserClubDao userDao;

	@Override
	public List<Club> findAll() {
		return (List<Club>) clubDao.findAll();
	}

	@Override
	public Club findById(Long id) {
		return clubDao.findById(id).orElse(null);
	}

	@Override
	public Club save(Club club) {
		
		UserClub userDb = userDao.save(club.getUser());
		club.setUser(userDb);
		
		Federation federationDb = federationDao.save(club.getFederation());
		club.setFederation(federationDb);
			
		return clubDao.save(club);
	}

	@Override
	public void delete(Long id) {
		clubDao.deleteById(id);
	}

	@Override
	public Club findByUserId(Long id) {
		return clubDao.findByUserId(id);
	}

}
