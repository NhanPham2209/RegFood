package com.example.regfood.service.imp;

import java.util.List;

import com.example.regfood.dto.RatingRestaurantDTO;
import com.example.regfood.entities.RatingRestaurant;
import com.example.regfood.payload.request.RatingRestaurantRequest;


public interface RatingRestaurantServiceImp {
	List<RatingRestaurant> getAllRatingRestaurantByUserId(int userId);
	boolean addRatingRestaurant(RatingRestaurantRequest RatingRestaurantRequest);
	List<RatingRestaurantDTO> getAllRatingRestaurant();
}
