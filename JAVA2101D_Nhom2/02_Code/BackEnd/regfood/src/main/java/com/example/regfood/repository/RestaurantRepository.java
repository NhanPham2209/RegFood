package com.example.regfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.Category;
import com.example.regfood.entities.Restaurant;
@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer>{
	@Query(value = "SELECT * FROM restaurant WHERE restaurant_id = ?1",nativeQuery = true)
	Restaurant findRestaurantById(Integer id);
}
