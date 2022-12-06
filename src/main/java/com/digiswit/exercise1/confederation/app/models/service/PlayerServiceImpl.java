package com.digiswit.exercise1.confederation.app.models.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.digiswit.exercise1.confederation.app.models.dao.PlayerDao;
import com.digiswit.exercise1.confederation.app.models.entity.Player;

@Service
public class PlayerServiceImpl implements IPlayerService {
	
	@Autowired
	private PlayerDao playerDao;

	@Override
	public List<Player> findByClubId(Long club_id) {
		return playerDao.findByClubId(club_id);
	}

	@Override
	public Player findById(Long id) {
		return playerDao.findById(id).orElse(null);
	}

	@Override
	public Player save(Player player) {
		return playerDao.save(player);
	}

	@Override
	public void delete(Long id) {
		playerDao.deleteById(id);
	}

	

}
