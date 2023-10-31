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
import com.example.regfood.service.imp.RatingFoodServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("/rating-food")
public class RatingFoodController {
	@Autowired
	RatingFoodServiceImp ratingFoodServiceImp;
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> findRatingFoodByUserId(@PathVariable("userId") int userId){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(ratingFoodServiceImp.getAllRatingFoodByUserId(userId));		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	@GetMapping("")
	public ResponseEntity<?> getAll(){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(ratingFoodServiceImp.getAllRatingFood());
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	
	@PostMapping("/add-ratingfood")
	public ResponseEntity<?> AddRatingFood(@RequestBody RatingFoodRequest ratingFoodRequest){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(ratingFoodServiceImp.addRatingFood(ratingFoodRequest));
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	
}
