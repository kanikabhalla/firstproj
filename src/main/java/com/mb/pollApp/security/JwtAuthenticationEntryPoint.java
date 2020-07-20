package com.mb.pollApp.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{

	/*
	 * This method is called whenever an exception is thrown due to an unauthenticated 
	 * user trying to access a resource that requires authentication.
	 */
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		
		System.out.println("User not authenticated");
		response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
		
	}

	
}
