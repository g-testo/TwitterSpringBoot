package com.tts.TechTalentTwitterInClass.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.TechTalentTwitterInClass.model.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{
	Role findByRole(String role);
}
