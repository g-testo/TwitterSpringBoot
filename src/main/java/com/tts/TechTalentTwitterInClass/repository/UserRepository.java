package com.tts.TechTalentTwitterInClass.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.tts.TechTalentTwitterInClass.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long>{
    User findByUsername(String username);
}
