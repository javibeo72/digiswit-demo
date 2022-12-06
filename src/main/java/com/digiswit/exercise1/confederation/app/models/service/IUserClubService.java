package com.digiswit.exercise1.confederation.app.models.service;

import com.digiswit.exercise1.confederation.app.models.entity.UserClub;

public interface IUserClubService {
	
	public UserClub findById(Long id);
	
	public UserClub save(UserClub player);
	
	public void delete(Long id);
	
	public UserClub findByUserName(String userName);
		
}
