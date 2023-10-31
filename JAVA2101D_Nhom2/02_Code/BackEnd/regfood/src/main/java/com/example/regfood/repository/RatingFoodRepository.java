package com.example.regfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.RatingFood;

@Repository
public interface RatingFoodRepository extends JpaRepository<RatingFood, Integer>{
	
	@Query(value = "SELECT * FROM rating_food WHERE user_id = ?1",nativeQuery = true)
	List<RatingFood> getAllRatingFoodByUserId( int userId);
	
}
