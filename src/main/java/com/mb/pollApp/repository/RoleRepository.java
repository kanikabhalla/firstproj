package com.mb.pollApp.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.mb.pollApp.model.Role;
import com.mb.pollApp.model.RoleName;

public interface RoleRepository extends CrudRepository<Role,Long>{
	
	Optional<Role> findByName(RoleName roleName);

}
