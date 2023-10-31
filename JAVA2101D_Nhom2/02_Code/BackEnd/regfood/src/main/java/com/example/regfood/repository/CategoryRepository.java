package com.example.regfood.repository;

import org.springframework.stereotype.Repository;

import com.example.regfood.entities.Category;

import jakarta.transaction.Transactional;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
@Repository
public interface CategoryRepository extends JpaRepository<Category,Integer>{
	@Query(value = "SELECT * FROM category WHERE category_id = ?1",nativeQuery = true)
	Category findCategoryById(Integer id);
	
	@Query(value = "SELECT * FROM category WHERE status = 1",nativeQuery = true)
	List<Category> findCategoryByStatus();
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM category WHERE category_id = ?1",nativeQuery = true)
	void deleteCategory(Integer id);
}
