package com.example.regfood.service.imp;

import java.util.List;

import com.example.regfood.dto.RatingFoodDTO;
import com.example.regfood.entities.RatingFood;
import com.example.regfood.payload.request.RatingFoodRequest;

public interface RatingFoodServiceImp {
	List<RatingFoodDTO> getAllRatingFoodByUserId(int userId);
	boolean addRatingFood(RatingFoodRequest ratingFoodRequest);
	List<RatingFood> getAllRatingFood();
}
