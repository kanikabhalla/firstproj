package com.mb.pollApp.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.mb.pollApp.model.User;
import com.mb.pollApp.repository.UserRepository;

public class CustomUserDetails implements UserDetailsService{
	
	@Autowired
	private UserRepository userrepo;

	@Override
	public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
		Optional<User> user = userrepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
		
		if(!user.isPresent()) {
			throw new UsernameNotFoundException("Not found : " + usernameOrEmail);
		}
		
		return PrincipalUser.create(user.get());
		
	}

}
