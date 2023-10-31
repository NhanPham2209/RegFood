package com.example.regfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.regfood.payload.ResponseData;
import com.example.regfood.service.imp.FileServieImp.FileServiceImp;
import com.example.regfood.service.imp.FoodServiceImp;
import com.example.regfood.service.imp.PromotionServiceImp;
import com.example.regfood.service.imp.RatingRestaurantServiceImp;
import com.example.regfood.service.imp.RestaurantServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("index")
public class IndexController {
	@Autowired
	RestaurantServiceImp restaurantServiceImp;
	@Autowired
	FileServiceImp fileServiceImp;
	@Autowired
	FoodServiceImp foodServiceImp;
	@Autowired
	PromotionServiceImp promotionServiceImp;
	@Autowired
	RatingRestaurantServiceImp ratingRestaurantServiceImp;
	 @GetMapping("")
	    public ResponseEntity<?> getHomeRestaurants(){
	        ResponseData responseData  = new ResponseData();
	        responseData.setData(restaurantServiceImp.getDetailRestaurant());
	        return new ResponseEntity<>(responseData,HttpStatus.OK);
	    }
	 
	 @GetMapping("/restaurant/{filename:.+}")
	    
	    public ResponseEntity<?> getFileFood(@PathVariable String filename) {
	      
	    	Resource resouce =  fileServiceImp.loadFile(filename);
	    	return ResponseEntity.ok()
	    	        .header(HttpHeaders.CONTENT_DISPOSITION, 
	    	        		"attachment; filename=\"" + resouce.getFilename() + "\"").body(resouce);
	    }
	 @GetMapping("/food")
	 public ResponseEntity<?> findFoodById(@RequestParam int id){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(foodServiceImp.findById(id));
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	 
	 @GetMapping("/promotion")
	 public ResponseEntity<?> getAllPromotion(){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(promotionServiceImp.getAllPromotion());
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	 @GetMapping("/list-rating-restaurant")
	 public ResponseEntity<?> getAllRatingRestaurant(){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(ratingRestaurantServiceImp.getAllRatingRestaurant());
		 return new ResponseEntity<>(responseData,HttpStatus.OK);
	 }
}
