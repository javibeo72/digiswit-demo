package com.digiswit.exercise1.confederation.app.models.dao;

import org.springframework.data.repository.CrudRepository;

import com.digiswit.exercise1.confederation.app.models.entity.UserClub;

public interface UserClubDao extends CrudRepository<UserClub, Long>{
	
	public UserClub findByUserName(String username);

}
