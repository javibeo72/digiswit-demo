package com.digiswit.exercise1.confederation.app.security;

public class SecurityConstants {
	
	// Spring Security
	public static final String LOGIN_URL = "/login";
	public static final String CLUB_URL = "/club";
	public static final String[] SWAGGER_UI = {
            // -- Swagger UI v2
            "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
            "/configuration/ui","/configuration/security","/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**", "/swagger-ui/**"
     };
	
	public static final String HEADER_AUTHORIZACION_KEY = "Authorization";
	public static final String TOKEN_BEARER_PREFIX = "Bearer ";

	// JWT
	public static final String ISSUER_INFO = "https://www.digitswit.com/";
	public static final String SUPER_SECRET_KEY = "12345";
	public static final long TOKEN_EXPIRATION_TIME = 864_000_000; // 10 days


}
