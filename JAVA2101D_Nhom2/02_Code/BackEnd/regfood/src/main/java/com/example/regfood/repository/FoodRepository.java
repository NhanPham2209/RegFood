package com.example.regfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.Food;

import jakarta.transaction.Transactional;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer>{
	@Query(value = "SELECT * FROM food WHERE food_id = ?1",nativeQuery = true)
	Food findFoodById(Integer foodId);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM food WHERE food_id = ?1",nativeQuery = true)
	void deleteFood(Integer foodId);
}
