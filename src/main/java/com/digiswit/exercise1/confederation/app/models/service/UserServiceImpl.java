package com.digiswit.exercise1.confederation.app.models.service;

import static java.util.Collections.emptyList;

import com.digiswit.exercise1.confederation.app.models.dao.UserClubDao;
import com.digiswit.exercise1.confederation.app.models.entity.UserClub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService, IUserClubService {

	@Autowired
	private UserClubDao userDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserClub userClub = userDao.findByUserName(username);
		
		if (userClub == null) {
			throw new UsernameNotFoundException(username);
		}
		
		return new User(userClub.getUserName(), userClub.getPassword(), emptyList());
		
	}
	
	public UserClub save(UserClub user) {
		return userDao.save(user);
	}

	@Override
	public UserClub findById(Long id) {
		return null;
	}

	@Override
	public void delete(Long id) {
	
	}

	@Override
	public UserClub findByUserName(String userName) {
		return userDao.findByUserName(userName);
	}
	

}
