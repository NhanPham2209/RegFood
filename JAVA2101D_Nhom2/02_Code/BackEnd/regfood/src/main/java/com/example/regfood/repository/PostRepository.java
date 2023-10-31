package com.example.regfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.Posts;

import jakarta.transaction.Transactional;

@Repository
public interface PostRepository extends JpaRepository<Posts, Integer> {
	@Query(value = "SELECT * FROM posts WHERE id = ?1",nativeQuery = true)
	Posts findPostById(Integer id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM posts WHERE id = ?1",nativeQuery = true)
	void deletePost(Integer id);
}
