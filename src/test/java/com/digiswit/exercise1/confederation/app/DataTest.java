package com.digiswit.exercise1.confederation.app;

import com.digiswit.exercise1.confederation.app.models.entity.Club;
import com.digiswit.exercise1.confederation.app.models.entity.Federation;
import com.digiswit.exercise1.confederation.app.models.entity.UserClub;

public class DataTest {

  public static final Federation FEDERATION_001 = new Federation(1L, "Real Federación Española de Fútbol", "RFEF");	
  public static final Federation FEDERATION_002 = new Federation(1L, "Federazione Italiana Giuoco Calci", "'FIGC");	
  
  public static final UserClub USER_001 = new UserClub(1L, "jbecerra@mail.com","12345678");
  public static final UserClub USER_002 = new UserClub(2L, "user2@mail.com","87654321");
	
  public static final Club CLUB_001 = new Club(1L, USER_001, "Real Madrid C.F.", "Real Madrid",
		                                       FEDERATION_001, Boolean.TRUE, 0);
		  
  public static final Club CLUB_002 = new Club(1L, USER_002, "F.C. Barcelona", "Barça",
          FEDERATION_001, Boolean.TRUE, 0);
	
	
  public static final String testClub = new String("{  \"user\": {    \"userName\": \"jbecerra@mail.com\", "
  	                            	+ "   \"password\": \"12345678\"  },   \"officialName\": \"Málaga C.F.\", "
  	                            	+ "   \"popularName\": \"Málaga\",  \"federation\": {    \"acronym\": \"RFEF\"  },"
  	                            	+ "  \"publicDetails\": true  }");
  
  public static final String testClub2 = new String("{  \"user\": {    \"userName\": \"jbecerra@mail.com\", "
        	+ "   \"password\": \"12345\"  },   \"officialName\": \"Málaga C.F.\", "
        	+ "   \"popularName\": \"Málaga\",  \"federation\": {    \"acronym\": \"RFEF\"  }"
        	+ " }");
  
}
