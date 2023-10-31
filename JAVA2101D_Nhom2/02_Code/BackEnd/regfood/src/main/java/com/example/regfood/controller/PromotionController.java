package com.example.regfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.regfood.payload.ResponseData;
import com.example.regfood.service.imp.PromotionServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("/promotion")
public class PromotionController {
	@Autowired
	PromotionServiceImp promotionServiceImp;
	 @GetMapping("")
	 public ResponseEntity<?> getAll(){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(promotionServiceImp.getAllPromotion());
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	 @GetMapping("/getPromotion/{promotionId}")
	 public ResponseEntity<?> getPromotion(@PathVariable("promotionId") int id){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(promotionServiceImp.getPromotion(id));
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	 @PostMapping("/add-promption")
		public ResponseEntity<?> addNewPromotion(@RequestParam int percent, @RequestParam boolean status){
			ResponseData responseData = new ResponseData();
			responseData.setData(promotionServiceImp.addPromoiton(percent, status));
			return new ResponseEntity<>(responseData,HttpStatus.OK);
		}
	 
	 @PostMapping("/edit-promption")
		public ResponseEntity<?> editPromotion(@PathVariable int id, @RequestParam int percent, @RequestParam boolean status){
			ResponseData responseData = new ResponseData();
			responseData.setData(promotionServiceImp.editPromotion(id,percent, status));
			return new ResponseEntity<>(responseData,HttpStatus.OK);
		}
}
