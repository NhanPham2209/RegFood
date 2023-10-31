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

import com.example.regfood.dto.AddressDTO;
import com.example.regfood.dto.CategoryDTO;
import com.example.regfood.payload.ResponseData;
import com.example.regfood.payload.request.AddressRequest;
import com.example.regfood.service.imp.AddressServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("/address")
public class AddressController {
	@Autowired
	AddressServiceImp addressServiceImp;
	
	@GetMapping("/{userId}")
	public ResponseEntity<?> findFoodById(@PathVariable("userId") int userId){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(addressServiceImp.getAllAddressByUserId(userId));
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	@PostMapping("/add-address")
	public ResponseEntity<?> addNewAddress(@RequestBody AddressRequest addressRequest){
		ResponseData responseData = new ResponseData();
		responseData.setData(addressServiceImp.addAdress(addressRequest));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@PostMapping("/edit-address/{id}")
	public ResponseEntity<?> editCategory(@PathVariable("id") int addressId , @RequestBody AddressDTO addressDTO){
		ResponseData responseData = new ResponseData();
		responseData.setData(addressServiceImp.editAdress(addressDTO, addressId));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@PostMapping("/delete-address/{addressId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("addressId") int addressId){
		ResponseData responseData = new ResponseData();
		
		responseData.setData(addressServiceImp.deleteAddress(addressId));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
}
