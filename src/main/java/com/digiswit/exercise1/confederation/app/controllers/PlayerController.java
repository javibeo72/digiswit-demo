package com.digiswit.exercise1.confederation.app.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.digiswit.exercise1.confederation.app.models.service.IClubService;
import com.digiswit.exercise1.confederation.app.models.service.IPlayerService;
import com.digiswit.exercise1.confederation.app.models.entity.Club;
import com.digiswit.exercise1.confederation.app.models.entity.Player;
import com.digiswit.exercise1.confederation.app.models.entity.PlayerMinInfo;

@RestController
public class PlayerController {
	
	@Autowired
	private IPlayerService playerService;
	
	@Autowired
	private IClubService clubService;
		
	@GetMapping("/club/{clubId}/player")
	public ResponseEntity<?> listAllClubPlayers(@PathVariable Long clubId) {
		
		// Check if could be any problem with the club
		Map<String,Object> response = CheckClub(clubId);
		
		if (!response.isEmpty()) {
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		return new ResponseEntity<List<PlayerMinInfo>>(
     		playerService.findByClubId(clubId).stream().map(player -> {
				 PlayerMinInfo playerMinInfo = new PlayerMinInfo(player.getId(), player.getGivenName(), 
						                                         player.getFamilyName());
				 return playerMinInfo;
		 }).collect(Collectors.toList()), HttpStatus.OK); 
		
	}
	
	@GetMapping("/club/{clubId}/player/{playerId}")
	public ResponseEntity<?> getPlayerDetails(@PathVariable Long clubId, @PathVariable Long playerId) {
		
		// Check if could be any problem with the club
		Map<String,Object> response = CheckClub(clubId);
		
		if (!response.isEmpty()) {
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
				
		// Check if could be any problem with the player (it should exists).	
		Player playerDb = new Player();
		
		response = checkPlayer(clubId, playerId);
		
		// Avoid delete in case the player do not exist at DB.		
		if (!(response.get("player") instanceof Player)) {
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			playerDb = (Player) response.get("player");
	    }	
		
		return new ResponseEntity<Player>(playerDb, HttpStatus.OK);
			
	}
	
	@PostMapping("/club/{clubId}/player")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> createPlayer(@RequestBody @Valid Player player, BindingResult result, @PathVariable Long clubId ) {
		
		Map<String,Object> response = null;
		
		if (result.hasErrors()) {
						
			List<Map<String, String>> errors = new ArrayList<>();
			
			result.getAllErrors().forEach(error -> {
				
				Map<String, String> transformedError = new HashMap<>();
				
				String fieldName = ((FieldError) error).getField();
				transformedError.put("field",fieldName );
				
				String errorMessage = error.getDefaultMessage();
				transformedError.put("error", errorMessage);
				
				errors.add(transformedError);
			
			});

		    response = new HashMap<>();
		    response.put("errors", errors);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
			
		}			
				
		
		// Check if could be any problem with the club
		response = CheckClub(clubId);
		
		if (!response.isEmpty()) {
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		Club club = clubService.findById(clubId);
		player.setClub(club);
				
		return new ResponseEntity<Player>(playerService.save(player), HttpStatus.CREATED);
	}
	
	
	
	@PutMapping("/club/{clubId}/player/{playerId}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> savePlayer(@RequestBody @Valid Player player, BindingResult result,
			                            @PathVariable Long clubId, @PathVariable Long playerId) {
		
		Map<String,Object> response = null;
		
		// Handle error messages		
		if (result.hasErrors()) {
						
			List<Map<String, String>> errors = new ArrayList<>();
			
			result.getAllErrors().forEach(error -> {
				
				Map<String, String> transformedError = new HashMap<>();
				
				String fieldName = ((FieldError) error).getField();
				transformedError.put("field",fieldName );
				
				String errorMessage = error.getDefaultMessage();
				transformedError.put("error", errorMessage);
				
				errors.add(transformedError);
			
			});

		    response = new HashMap<>();
		    response.put("errors", errors);
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.BAD_REQUEST);
			
		}			
				
		// Check if could be any problem with the club
		response = CheckClub(clubId);
		
		if (!response.isEmpty()) {
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}

		// Check if could be any problem with the player (it should exists).	
		Player playerDb = new Player();
		
		response = checkPlayer(clubId, playerId);
		
		// Avoid delete in case the player do not exist at DB.		
		if (!(response.get("player") instanceof Player)) {
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			playerDb = (Player) response.get("player");
	    }	
		
		
		// Inform current player at database with the new values from the body request
		playerDb.setGivenName(player.getGivenName());
		playerDb.setFamilyName(player.getFamilyName());
		playerDb.setNationality(player.getNationality());
		playerDb.setEmail(player.getEmail());
		playerDb.setDateOfBirth(player.getDateOfBirth());
		playerDb.setClub(clubService.findById(clubId));
				
		return new ResponseEntity<Player>(playerService.save(playerDb), HttpStatus.CREATED);
		
	}	


	@DeleteMapping("/club/{clubId}/player/{playerId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public ResponseEntity<?> deletePlayerForClub( @PathVariable Long clubId, @PathVariable Long playerId) {
		
		Map<String,Object> response = null;
		
		response = checkPlayer(clubId, playerId);
		
		// Avoid delete in case the player do not exist at DB.		
		if (!(response.get("player") instanceof Player)) {
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		playerService.delete(playerId);
		
		// Assume the player has been deleted.
		response = new HashMap<>();
		response.put("msg", "Player with id ".concat(playerId.toString().concat(" has been removed succesfully")));
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
	}
	
	
	
	
	private Map<String,Object> CheckClub(Long clubId) {
		
		Club club = null;
		
		Map<String,Object> response = new HashMap<>();
		
		try {
			club = clubService.findById(clubId);
		} catch (DataAccessException e) {
			response.put("msg_error", "Error when retrieving information from database: ");
			response.put("error_details", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
			return response;
		}
		
		if (club == null) {
			response.put("msg_error", "Club with id ".concat(clubId.toString().concat(" does not exist! Please review it.")));
			return response;
		} else {
			
			if (!club.getPublicDetails()) {
				response.put("msg_error", "Club with id ".concat(clubId.toString().concat(" does not publish public information.")));
				return response;
			}
		}
		
		return response;
		
	}
	
	private  Map<String,Object> checkPlayer(Long clubId, Long playerId) {
		
		
		Player playerDb = new Player();
		
		Map<String,Object> response = new HashMap<>();
		
		try {
			playerDb = playerService.findById(playerId);
		} catch (DataAccessException e) {
			response.put("msg_error", "Error when retrieving information from database: ");
			response.put("error_details", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
		    return response;
			// return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		}
		
		if (playerDb == null) {
			response.put("msg_error", "Player with id ".concat(playerId.toString().concat(" does not exist! Please review it.")));
			return response;
			// return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
		} else {
			
			if (!playerDb.getClub().getId().equals(clubId)) {
				response.put("msg_error", "Player with id ".concat(playerId.toString().
						concat(" does not belong to club with id ").concat(clubId.toString().concat(", review the id's"))));
				return response;
				// return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
				
			}
		}
		
		response.put("player", playerDb);
		
		return response;
		
		
	}
	
}
