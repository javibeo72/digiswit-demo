package com.digiswit.exercise1.confederation.app.models.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.digiswit.exercise1.confederation.app.models.entity.Player;

public interface PlayerDao extends CrudRepository<Player, Long>{
	
	public List<Player> findByClubId(Long club_id);

}
