package com.example.regfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.regfood.payload.ResponseData;
import com.example.regfood.service.imp.SalesServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("sales")
public class SalesController {
	@Autowired
	SalesServiceImp salesServiceImp;
	@GetMapping("/getRevenueLastMonth")
	public ResponseEntity<?> getAllPost(){
		ResponseData responseData = new ResponseData();
		responseData.setData(salesServiceImp.getRevenueLastMonth());
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@GetMapping("/getRevenueToday")
	public ResponseEntity<?> getRevenueToday(){
		ResponseData responseData = new ResponseData();
		responseData.setData(salesServiceImp.getRevenueToday());
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	@GetMapping("/getRevenueLastSixMonths")
	public ResponseEntity<?> getRevenueLastSixMonths(){
		ResponseData responseData = new ResponseData();
		responseData.setData(salesServiceImp.getRevenueLastSixMonths());
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
}
