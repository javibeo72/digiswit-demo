package com.digiswit.exercise1.confederation.app.models.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.digiswit.exercise1.confederation.app.models.entity.Club;

public interface ClubDao extends CrudRepository<Club, Long>{
	
	@Query(value= "SELECT * FROM CLUBS c WHERE c.user_id = ?1", nativeQuery = true)
	public Club findByUserId(Long userId);

}
