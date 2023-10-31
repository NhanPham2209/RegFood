package com.example.regfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.Users;


@Repository
public interface UserRepository extends JpaRepository<Users,Integer> {
	@Query(value = "SELECT * FROM users WHERE email = ?1",nativeQuery = true)
    Users findByEmail(String email);
	
	@Query(value = "SELECT * FROM users WHERE role_id = 2",nativeQuery = true)
    List<Users> getAllCustommer();
	
    
}
