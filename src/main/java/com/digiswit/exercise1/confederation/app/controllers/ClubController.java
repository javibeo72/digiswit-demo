package com.digiswit.exercise1.confederation.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.digiswit.exercise1.confederation.app.models.service.IClubService;
import com.digiswit.exercise1.confederation.app.models.service.IFederationService;
import com.digiswit.exercise1.confederation.app.models.service.IPlayerService;
import com.digiswit.exercise1.confederation.app.models.service.IUserClubService;
import com.digiswit.exercise1.confederation.app.models.entity.Club;
import com.digiswit.exercise1.confederation.app.models.entity.ClubInfo;
import com.digiswit.exercise1.confederation.app.models.entity.Federation;
import com.digiswit.exercise1.confederation.app.models.entity.Player;
import com.digiswit.exercise1.confederation.app.models.entity.UserClub;

@Validated
@RestController
public class ClubController {
	
	@Autowired
	private IClubService clubService;
	
	@Autowired
	private IUserClubService userService;
	
	@Autowired
	private IPlayerService playerService;
	
	@Autowired
	private IFederationService federationService;
	
	@GetMapping("/club")
	public List<ClubInfo> listAllClubs() {
			 
		 return clubService.findAll().stream()
				 .filter(club -> club.getPublicDetails().equals(Boolean.TRUE))
				 .map(club -> {
				 ClubInfo clubMinDetails = new ClubInfo(club.getOfficialName(), 
						                                club.getPopularName(),
						                                club.getFederation().getAcronym());
				 return clubMinDetails;
		 }).collect(Collectors.toList()); 
		
	}
	
	@GetMapping("/club/{id}")
	public ResponseEntity<?> getClubDetails(@PathVariable Long id) {
		
		Club club = null;
		
		Map<String,Object> response = new HashMap<>();
		
		try {
			club = clubService.findById(id);
		} catch (DataAccessException e) {
			response.put("msg_error", "Error when retrieving information from database: ");
			response.put("error_details", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (club == null) {
			response.put("msg_error", "Club with id ".concat(id.toString().concat(" does not exist! Please check it.")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			
			System.out.println(club);
			
			if (!club.getPublicDetails().booleanValue()) {
				response.put("msg_error", "Club with id ".concat(id.toString().concat(" does not publish public information.")));
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			}
		}
		
		// Retrieve the number of players
		List<Player> players = playerService.findByClubId(id);
		club.setNumberOfPlayers(players.size());
		
		return new ResponseEntity<Club>(club, HttpStatus.OK);
		
	}

	@PostMapping("/club")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createClub(@RequestBody Club club ) {	
		
		Map<String,Object> response = new HashMap<>();
		
		Federation federationDb = null;
		
		// Handle any kind of problem with federation
		try {
			federationDb = federationService.findByAcronym(club.getFederation().getAcronym());
		} catch (DataAccessException e) {
			response.put("msg_error", "Error when retrieving information from database: ");
			response.put("error_details", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (federationDb == null) {
			response.put("msg_error", "Federation ".concat(club.getFederation().getAcronym().concat(" does not exist! Please check it.")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Encrypt password before saving user
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encryptPassword = passwordEncoder.encode(club.getUser().getPassword());
		club.getUser().setPassword(encryptPassword);
		
		UserClub userDb = userService.save(club.getUser());
		club.setUser(userDb);
		
		// Update Federation
		Federation federationUpdate = new Federation();
		federationUpdate.setId(federationDb.getId());
		federationUpdate.setName(federationDb.getName());
		federationUpdate.setAcronym(club.getFederation().getAcronym());
		
		club.setFederation(federationUpdate);
		
		// Assign default values when creating a new Club.
		if (club.getPublicDetails() == null) {
			club.setPublicDetails(Boolean.TRUE);
		}
		
		club.setNumberOfPlayers(0);
		club.setPlayers(new ArrayList<Player>());
		
		return new ResponseEntity<Club>(clubService.save(club), HttpStatus.CREATED);
		
	}
	
	@PutMapping("/club")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> saveClub(@RequestBody Club club) {
		
		UserClub userClubDb = null;
		
		Club clubDb = null;
		
		Federation federationDb = null;
		
		Map<String,Object> response = new HashMap<>();
		
		// Handle any kind of problem with user
		try {
			userClubDb = userService.findByUserName(club.getUser().getUserName());
		} catch (DataAccessException e) {
			response.put("msg_error", "Error when retrieving information from database: ");
			response.put("error_details", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (userClubDb == null) {
			response.put("msg_error", "User ".concat(club.getUser().getUserName().concat(" does not exist! Please check it.")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// Handle any kind of problem with club
		try {
			clubDb = clubService.findByUserId(userClubDb.getId());
		} catch (DataAccessException e) {
			response.put("msg_error", "Error when retrieving information from database: ");
			response.put("error_details", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (clubDb == null) {
			response.put("msg_error", "Club with id ".concat(userClubDb.getId().toString().concat(" does not exist! Contact db administrator")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		} else {
					
			if (!clubDb.getPublicDetails().booleanValue()) {
				response.put("msg_error", "Club ".concat(clubDb.getOfficialName()).concat(" does not publish public information."));
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NON_AUTHORITATIVE_INFORMATION);
			}
		}
		
		// Handle any kind of problem with federation
		try {
			federationDb = federationService.findByAcronym(club.getFederation().getAcronym());
		} catch (DataAccessException e) {
			response.put("msg_error", "Error when retrieving information from database: ");
			response.put("error_details", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (federationDb == null) {
			response.put("msg_error", "Federation ".concat(club.getFederation().getAcronym().concat(" does not exist! Please check it.")));
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		// ---------------------------------------------------
		// Update the information from the Body request Object
		// ---------------------------------------------------
		
		// Updates for the user.
		UserClub userClubUpdated = new UserClub();
		
		userClubUpdated.setId(userClubDb.getId());
		userClubUpdated.setUserName(club.getUser().getUserName());
				
		// Encrypt password for user
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encryptPassword = passwordEncoder.encode(club.getUser().getPassword());
		userClubUpdated.setPassword(encryptPassword);
			
		// Updates for Federation
		Federation federationUpdate = new Federation();
		federationUpdate.setId(federationDb.getId());
		federationUpdate.setName(federationDb.getName());
		federationUpdate.setAcronym(club.getFederation().getAcronym());
		
		// Updates for the Club
		Club clubUpdated = new Club();
		
		clubUpdated.setId(clubDb.getId());
		clubUpdated.setOfficialName(club.getOfficialName());
		clubUpdated.setPopularName(club.getPopularName());
		clubUpdated.setPublicDetails(club.getPublicDetails());
		clubUpdated.setNumberOfPlayers(club.getNumberOfPlayers());
		
		clubUpdated.setUser(userClubUpdated);
		clubUpdated.setFederation(federationUpdate);
		
		return new ResponseEntity<Club>(clubService.save(clubUpdated), HttpStatus.OK);
		
	}
	
	@DeleteMapping("/club/{id}")
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ResponseEntity<Map<String,Object>> deleteClub(Long id) {
	
		Map<String,Object> response = new HashMap<>();
		
		response.put("msg_error", "Its not possible to delete user / club, action not allowed.");
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.FORBIDDEN);
		
	}

}
