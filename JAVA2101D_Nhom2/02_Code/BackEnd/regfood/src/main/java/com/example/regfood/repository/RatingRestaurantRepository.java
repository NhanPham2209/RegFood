package com.example.regfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.RatingFood;
import com.example.regfood.entities.RatingRestaurant;

@Repository
public interface RatingRestaurantRepository extends JpaRepository<RatingRestaurant, Integer> {
	@Query(value = "SELECT * FROM rating_restaurant WHERE user_id = ?1",nativeQuery = true)
	List<RatingRestaurant> getAllRatingRestaurantByUserId( int userId);
}
