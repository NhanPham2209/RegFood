package com.example.regfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regfood.payload.ResponseData;
import com.example.regfood.payload.request.RatingFoodRequest;
import com.example.regfood.payload.request.RatingRestaurantRequest;
import com.example.regfood.service.imp.RatingRestaurantServiceImp;

@CrossOrigin("*")
@RestController
@RequestMapping("/rating-restaurant")
public class RatingRestaurantController {
	@Autowired
	RatingRestaurantServiceImp ratingRestaurantServiceImp;
	@GetMapping("/{restaurantId}")
	public ResponseEntity<?> findRatingRestaurantByUserId(@PathVariable("restaurantId") int userId){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(ratingRestaurantServiceImp.getAllRatingRestaurantByUserId(userId));		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	@GetMapping("")
	public ResponseEntity<?> getAll(){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(ratingRestaurantServiceImp.getAllRatingRestaurant());
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	
	@PostMapping("/add-ratingrestaurant")
	public ResponseEntity<?> AddRatingFood(@RequestBody RatingRestaurantRequest ratingRestaurantRequest){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(ratingRestaurantServiceImp.addRatingRestaurant(ratingRestaurantRequest));
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
}
