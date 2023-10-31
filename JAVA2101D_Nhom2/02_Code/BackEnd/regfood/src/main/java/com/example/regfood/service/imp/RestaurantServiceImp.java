package com.example.regfood.service.imp;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.dto.RestaurantDTO;
import com.example.regfood.entities.Restaurant;

public interface RestaurantServiceImp {
	
	List<Restaurant> getRestaurants();
	boolean editRestaurant(
							String restaurantName, 
							String address, 
							String numberPhone,
							String email,
							String description);
	
	boolean addRestaurant( 
							String restaurantName, 
							MultipartFile file,
							String address, 
							String numberPhone,
							String email,
							String description);
	boolean changeAvatarRestaurant(MultipartFile file);
	RestaurantDTO getDetailRestaurant();
}
