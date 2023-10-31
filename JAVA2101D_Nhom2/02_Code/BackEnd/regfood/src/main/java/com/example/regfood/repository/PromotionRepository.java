package com.example.regfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.Category;
import com.example.regfood.entities.Promotion;
import com.example.regfood.entities.Restaurant;
@Repository
public interface PromotionRepository extends JpaRepository<Promotion, Integer> {
	@Query(value = "SELECT * FROM promotion WHERE status = true",nativeQuery = true)
	List<Promotion> findPromotionByStatus();
	
	@Query(value = "SELECT * FROM promotion WHERE id = ?1",nativeQuery = true)
	Promotion findPromotionById(int id);
	
}