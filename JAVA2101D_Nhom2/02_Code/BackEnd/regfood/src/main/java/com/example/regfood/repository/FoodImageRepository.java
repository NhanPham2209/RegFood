package com.example.regfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.example.regfood.entities.FoodImage;

import jakarta.transaction.Transactional;

public interface FoodImageRepository extends JpaRepository<FoodImage, Integer> {
	@Query(value = "SELECT * FROM food_image WHERE food_id = ?1",nativeQuery = true)
	List<FoodImage> findFoodImagesByFoodId(Integer foodId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM food_image WHERE food_id = ?1",nativeQuery = true)
	void deleteFoodImage(Integer foodId);
	
}
