package com.mb.pollApp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mb.pollApp.model.User;

public interface UserRepository extends CrudRepository<User,Long>{

	Optional<User> findByEmail(String email);
	
	Optional<User> findByUsernameOrEmail(String username,String email);
	
	List<User> findByIdIn(List<Long> userIds);
	
	Optional<User> findByUsername(String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByEmail(String email);
	
	
}
