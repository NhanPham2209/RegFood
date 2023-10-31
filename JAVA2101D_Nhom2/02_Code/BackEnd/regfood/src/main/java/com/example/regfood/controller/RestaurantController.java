package com.example.regfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.payload.ResponseData;
import com.example.regfood.service.imp.FileServieImp.FileServiceImp;
import com.example.regfood.service.imp.RestaurantServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("/restaurant")
public class RestaurantController {
	@Autowired
	FileServiceImp fileServiceImp;
	@Autowired
	RestaurantServiceImp restaurantServiceImp;
	
	@GetMapping("")
	public ResponseEntity<?> getRestaurant(){
		ResponseData responseData = new ResponseData();
		responseData.setData(restaurantServiceImp.getDetailRestaurant());
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@PostMapping("/add-restaurant")
	public ResponseEntity<?> addRestaurant(@RequestParam String restaurantName,@RequestParam MultipartFile file,@RequestParam String address,@RequestParam String numberPhone,
			@RequestParam String email,@RequestParam String description){
			ResponseData responseData = new ResponseData();
			boolean isSuccess = restaurantServiceImp.addRestaurant(restaurantName, file, address, numberPhone, email, description);
			responseData.setData(isSuccess);
			return new ResponseEntity<>(responseData,HttpStatus.OK);
		
	}
	@PostMapping("/edit-restaurant")
	public ResponseEntity<?> addRestaurant(@RequestParam String restaurantName,@RequestParam String address,@RequestParam String numberPhone,
			@RequestParam String email,@RequestParam String description){
			ResponseData responseData = new ResponseData();
			boolean isSuccess = restaurantServiceImp.editRestaurant(restaurantName,  address, numberPhone, email, description);
			responseData.setData(isSuccess);
			return new ResponseEntity<>(responseData,HttpStatus.OK);
		
	}
	@PostMapping("/change-avatar")
	public ResponseEntity<?> changeAvatar(@RequestParam MultipartFile file){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(restaurantServiceImp.changeAvatarRestaurant(file));
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	
	 @GetMapping("/files/{filename:.+}")
	    
	    public ResponseEntity<?> getFileFood(@PathVariable String filename) {
	      
	    	Resource resouce =  fileServiceImp.loadFile(filename);
	    	return ResponseEntity.ok()
	    	        .header(HttpHeaders.CONTENT_DISPOSITION, 
	    	        		"attachment; filename=\"" + resouce.getFilename() + "\"").body(resouce);
	    }
	 
	 
}
