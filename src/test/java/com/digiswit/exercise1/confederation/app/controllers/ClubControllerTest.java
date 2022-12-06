package com.digiswit.exercise1.confederation.app.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;









import com.digiswit.exercise1.confederation.app.DataTest;
import com.digiswit.exercise1.confederation.app.models.entity.Club;
import com.digiswit.exercise1.confederation.app.models.entity.Federation;
import com.digiswit.exercise1.confederation.app.models.entity.UserClub;
import com.digiswit.exercise1.confederation.app.models.service.IClubService;
import com.digiswit.exercise1.confederation.app.models.service.IPlayerService;
import com.digiswit.exercise1.confederation.app.models.service.IUserClubService;


@WebMvcTest(ClubController.class)
class ClubControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private IClubService clubService;

	@MockBean
	private IUserClubService userClubService;

	@MockBean
	private IPlayerService playerService;
	
	
	@Test
	void testCreateClub2() {
		
	}

	@Test
	void testCreateClub() throws Exception {

		// Given
		Federation federation = new Federation(1L, "Real Federación Española de Fútbol", "RFEF");
		UserClub user = new UserClub(null, "jbecerra@mail.com", "12345678");
		
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encryptPassword = passwordEncoder.encode("12345678");

		user.setPassword(encryptPassword);

		when(clubService.save(any())).then(invocation -> {
			Club c = (Club) invocation.getArgument(0);
			c.setUser(user);
			c.setFederation(federation);
			return c;
		});

		// When
		mvc.perform(post("/club").contentType(MediaType.APPLICATION_JSON)
		// Then
		.content(DataTest.testClub2)).andExpect(status().isCreated())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("$.user.userName").value("jbecerra@mail.com"))
		.andExpect(jsonPath("$.user.password").value(encryptPassword))
		.andExpect(jsonPath("$.officialName").value("Málaga C.F."))
		.andExpect(jsonPath("$.popularName").value("Málaga"))
		.andExpect(jsonPath("$.federation.acronym").value("RFEF"))
		.andExpect(jsonPath("$.publicDetails").value(true)).
		andExpect(jsonPath("$.numberOfPlayers").value(0));

		verify(clubService).save(any());

	}
	
/*	@Test
    void testFindClubById() throws Exception {
		
		// TO-DO
		// Mock JWT Token 
		
        // Given
        when(clubService.findById(1L)).thenReturn(DataTest.CLUB_001);
        
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String encryptPassword = passwordEncoder.encode("12345678");

        // When
        mvc.perform(get("/club/1").contentType(MediaType.APPLICATION_JSON))
        // Then
        .andExpect(status().isOk())
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(content().contentType(MediaType.APPLICATION_JSON))
        .andExpect(jsonPath("$.user.userName").value("jbecerra@mail.com"))
		.andExpect(jsonPath("$.user.password").value(encryptPassword))
		.andExpect(jsonPath("$.officialName").value("Málaga C.F."))
		.andExpect(jsonPath("$.popularName").value("Málaga"))
		.andExpect(jsonPath("$.federation.acronym").value("RFEF"))
        
        ;

        verify(clubService).findById(1L);
    }
	*/
	

}
