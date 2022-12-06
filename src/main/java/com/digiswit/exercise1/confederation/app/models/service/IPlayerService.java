package com.digiswit.exercise1.confederation.app.models.service;

import java.util.List;

import com.digiswit.exercise1.confederation.app.models.entity.Player;

public interface IPlayerService {
	
	public List<Player> findByClubId(Long club_id);
	
	public Player findById(Long id);
	
	public Player save(Player player);
	
	public void delete(Long id);
	

}
