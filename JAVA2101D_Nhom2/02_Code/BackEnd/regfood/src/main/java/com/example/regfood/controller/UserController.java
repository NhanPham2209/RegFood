package com.example.regfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.payload.ResponseData;
import com.example.regfood.payload.request.ChangePassWordRequest;
import com.example.regfood.service.imp.RatingFoodServiceImp;
import com.example.regfood.service.imp.UserServiceImp;
@CrossOrigin("*")
@RestController

@RequestMapping("/user")

public class UserController {
	@Autowired 
	UserServiceImp userServiceImp;
	@Autowired
	RatingFoodServiceImp ratingFoodServiceImp;
	
	@GetMapping("")
	public ResponseEntity<?> getDetailUser(@RequestParam String email){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(userServiceImp.getDetailUserDTO(email));
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	@GetMapping("/get-all-custommer")
	public ResponseEntity<?> getAllCustommer(){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(userServiceImp.getAllCustommer());
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	@PostMapping("/change-avatar")
	public ResponseEntity<?> changeAvatar(@RequestParam String email, @RequestParam MultipartFile file){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(userServiceImp.changeAvatar(email, file));
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	
	@GetMapping("/list-rating-food")
	public ResponseEntity<?> getRatingFood(@RequestParam int userId){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(ratingFoodServiceImp.getAllRatingFoodByUserId(userId));
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	@PostMapping("/change-password")
	public ResponseEntity<?> changePassWord(@RequestBody ChangePassWordRequest changePassWordRequest){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(userServiceImp.changePassWord(changePassWordRequest));
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
}
