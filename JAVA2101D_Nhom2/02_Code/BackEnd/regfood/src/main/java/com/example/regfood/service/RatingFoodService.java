package com.example.regfood.service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.regfood.dto.FoodDTO;
import com.example.regfood.dto.FoodImageDTO;
import com.example.regfood.dto.RatingFoodDTO;
import com.example.regfood.entities.Food;
import com.example.regfood.entities.FoodImage;
import com.example.regfood.entities.RatingFood;
import com.example.regfood.entities.Users;
import com.example.regfood.payload.request.RatingFoodRequest;
import com.example.regfood.repository.RatingFoodRepository;
import com.example.regfood.service.imp.RatingFoodServiceImp;
@Service
public class RatingFoodService implements RatingFoodServiceImp{
	@Autowired
	RatingFoodRepository ratingFoodRepository;
	@Override
	public List<RatingFoodDTO> getAllRatingFoodByUserId(int userId) {
		List<RatingFood> listRatingFoods = ratingFoodRepository.getAllRatingFoodByUserId(userId);
		List<RatingFoodDTO> listRatingFoodDTOs = new ArrayList<>();
		for(RatingFood ratingFood : listRatingFoods) {
			RatingFoodDTO ratingFoodDTO = new RatingFoodDTO();
			ratingFoodDTO.setId(ratingFood.getId());
				Food food = ratingFood.getFood();
				FoodDTO foodDTO = new FoodDTO();
				foodDTO.setId(food.getId());
				foodDTO.setFoodName(food.getFoodName());
				List<FoodImageDTO> listFoodImageDTOs =  new ArrayList<>();
				for(FoodImage foodImage : food.getListFoodImages()) {
					FoodImageDTO foodImageDTO = new FoodImageDTO();
					foodImageDTO.setImageName(foodImage.getImage());
					listFoodImageDTOs.add(foodImageDTO);
				}
				foodDTO.setFoodImageDTOs(listFoodImageDTOs);
			ratingFoodDTO.setFoodDTO(foodDTO);
			ratingFoodDTO.setContent(ratingFood.getContent());
			ratingFoodDTO.setRatingPoint(ratingFood.getRatingPoint());
			SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
	        String formattedDate = sdf.format(ratingFood.getCreatedDate());
			ratingFoodDTO.setCreatedDate(formattedDate);
			
			
			listRatingFoodDTOs.add(ratingFoodDTO);
		}
		return listRatingFoodDTOs;
	}

	@Override
	public boolean addRatingFood(RatingFoodRequest ratingFoodRequest) {
		RatingFood ratingFood = new RatingFood();
		ratingFood.setRatingPoint(ratingFoodRequest.getPoint());
		Food food = new Food();
		food.setId(ratingFoodRequest.getFoodId());
		Users user = new Users();
		user.setId(ratingFoodRequest.getUserId());
		ratingFood.setContent(ratingFoodRequest.getContent());
		ratingFood.setUsers(user);
		ratingFood.setFood(food);
		Date currentDate = new Date();
		Timestamp timestamp = new Timestamp(currentDate.getTime());
		ratingFood.setCreatedDate(timestamp);
		
		try {
			ratingFoodRepository.save(ratingFood);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<RatingFood> getAllRatingFood() {
		return ratingFoodRepository.findAll();
	}

}
