package com.example.regfood.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.regfood.dto.RatingRestaurantDTO;
import com.example.regfood.dto.UserDTO;
import com.example.regfood.entities.RatingRestaurant;
import com.example.regfood.entities.Restaurant;
import com.example.regfood.entities.Users;
import com.example.regfood.payload.request.RatingRestaurantRequest;
import com.example.regfood.repository.RatingRestaurantRepository;
import com.example.regfood.service.imp.RatingRestaurantServiceImp;

@Service
public class RatingRestaurantService implements RatingRestaurantServiceImp{
	@Autowired
	RatingRestaurantRepository ratingRestaurantRepository;
	@Override
	public List<RatingRestaurant> getAllRatingRestaurantByUserId(int userId) {
		List<RatingRestaurant> listRatingRestaurants = ratingRestaurantRepository.getAllRatingRestaurantByUserId(userId);
		
		return listRatingRestaurants;
	}

	@Override
	public boolean addRatingRestaurant(RatingRestaurantRequest ratingRestaurantRequest) {
		RatingRestaurant ratingRestaurant = new RatingRestaurant();
		ratingRestaurant.setRatingPoint(ratingRestaurantRequest.getPoint());
		Restaurant restaurant = new Restaurant();
		restaurant.setId(ratingRestaurantRequest.getRestaurantId());
		Users user = new Users();
		user.setId(ratingRestaurantRequest.getUserId());
		ratingRestaurant.setContent(ratingRestaurantRequest.getContent());
		
		
		try {
			ratingRestaurantRepository.save(ratingRestaurant);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<RatingRestaurantDTO> getAllRatingRestaurant() {
		List<RatingRestaurant> listRatingRestaurants = ratingRestaurantRepository.findAll();
		List<RatingRestaurantDTO> listRatingRestaurantDTOs = new ArrayList<>();
		
		for(RatingRestaurant ratingRestaurant : listRatingRestaurants) {
			RatingRestaurantDTO ratingRestaurantDTO = new RatingRestaurantDTO();
			ratingRestaurantDTO.setId(ratingRestaurant.getId());
			
			UserDTO userDTO = new UserDTO();
			userDTO.setId(ratingRestaurant.getUsers().getId());
			userDTO.setUserName(ratingRestaurant.getUsers().getFullName());
			userDTO.setAvatar(ratingRestaurant.getUsers().getAvatar());
			ratingRestaurantDTO.setUserDTO(userDTO);
			
			ratingRestaurantDTO.setContent(ratingRestaurant.getContent());
			ratingRestaurantDTO.setRatingPoint(ratingRestaurant.getRatingPoint());
			listRatingRestaurantDTOs.add(ratingRestaurantDTO);
			
		}
		return listRatingRestaurantDTOs;
	}
}
