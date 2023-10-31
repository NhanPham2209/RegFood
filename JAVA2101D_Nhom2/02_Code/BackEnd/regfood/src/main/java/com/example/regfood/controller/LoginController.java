package com.example.regfood.controller;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.regfood.payload.ResponseData;
import com.example.regfood.payload.request.SignUpRequest;
import com.example.regfood.repository.UserRepository;
import com.example.regfood.service.imp.LoginServiceImp;
import com.example.regfood.utils.JwtUtilsHelper;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
@CrossOrigin("*")
@RestController
@RequestMapping("/login")
public class LoginController {

	@Autowired
	LoginServiceImp loginServiceImp;
	@Autowired
	JwtUtilsHelper jwtUtilsHelper;
	
	@PostMapping("/user-signin")
	public ResponseEntity<?> signIn(@RequestParam String email,@RequestParam String passWord){
		ResponseData responseData = new ResponseData();
		if(loginServiceImp.checkLoginUser(email, passWord)) {
			String token = jwtUtilsHelper.generateToken(passWord);
			
			responseData.setData(token);
		}else {
			responseData.setData(false);
			responseData.setSuccess(false);
		}
		return new ResponseEntity<>(responseData,HttpStatus.OK);
		
	}
	@PostMapping("/user-signup")
	public ResponseEntity<?> signUp(@RequestBody SignUpRequest signUpRequest){
		
		ResponseData responseData = new ResponseData();
		responseData.setData(loginServiceImp.addUser(signUpRequest));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
		
	}
	
	@PostMapping("/admin-signin")
	public ResponseEntity<?> signInAdmin(@RequestParam String email,@RequestParam String passWord){
		ResponseData responseData = new ResponseData();
		if(loginServiceImp.checkLoginAdmin(email, passWord)) {
			String token = jwtUtilsHelper.generateToken(passWord);
			
			responseData.setData(token);
		}else {
			responseData.setData(false);
			responseData.setSuccess(false);
		}
		return new ResponseEntity<>(responseData,HttpStatus.OK);
		
	}
	
	@PostMapping("/admin-signup")
	public ResponseEntity<?> signUpAdmin(@RequestBody SignUpRequest signUpRequest){
		
		ResponseData responseData = new ResponseData();
		responseData.setData(loginServiceImp.AddAdmin(signUpRequest));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
		
	}
	
	 @GetMapping("/user-info/{email}")
	 public ResponseEntity<?> userInfo(@PathVariable("email") String email){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(loginServiceImp.userInfo(email));
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	
}
