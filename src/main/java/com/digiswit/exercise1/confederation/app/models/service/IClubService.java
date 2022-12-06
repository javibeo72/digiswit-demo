package com.digiswit.exercise1.confederation.app.models.service;

import java.util.List;

import com.digiswit.exercise1.confederation.app.models.entity.Club;

public interface IClubService {
	
	public List<Club> findAll();
	
	public Club findById(Long id);
	
	public Club save(Club club);
	
	public void delete(Long id);
	
	public Club findByUserId(Long id);
	

}
