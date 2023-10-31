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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.payload.ResponseData;
import com.example.regfood.payload.request.FoodRequest;
import com.example.regfood.service.imp.FileServieImp.FileServiceImp;
import com.example.regfood.service.imp.FoodServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("/admin")
public class FoodController {	
	@Autowired
	FoodServiceImp foodServiceImp;
	@Autowired
	FileServiceImp fileServiceImp;
	@PostMapping("/add-food")
	public ResponseEntity<?> addNewFood(@RequestParam MultipartFile[] files ,@RequestParam String foodName,@RequestParam int categoryId,@RequestParam double price ,@RequestParam String description){
		ResponseData responseData  = new ResponseData();
		boolean isSuccess = foodServiceImp.addFood(files, foodName, categoryId, price, description);
		responseData.setData(isSuccess);
		return new ResponseEntity<>(responseData,HttpStatus.OK);
		
	}
	@PostMapping("/edit-food")
	public ResponseEntity<?> editFood(@RequestParam int foodId,
			@RequestParam(value = "files", required = false) MultipartFile[] files ,
			@RequestParam String foodName,@RequestParam int categoryId,@RequestParam double price ,@RequestParam String description){
		ResponseData responseData  = new ResponseData();
		boolean isSuccess = foodServiceImp.editFood(foodId, files, foodName, categoryId, price, description);
		responseData.setData(isSuccess);
		return new ResponseEntity<>(responseData,HttpStatus.OK);
		
	}
	  @GetMapping("/files/{filename:.+}")
	    
	    public ResponseEntity<?> getFileFood(@PathVariable String filename) {
	      
	    	Resource resouce =  fileServiceImp.loadFile(filename);
	    	return ResponseEntity.ok()
	    	        .header(HttpHeaders.CONTENT_DISPOSITION, 
	    	        		"attachment; filename=\"" + resouce.getFilename() + "\"").body(resouce);
	    }
	  
	  @PostMapping("/delete-food/{foodId}")
	  public ResponseEntity<?> deleteFood(@PathVariable("foodId") int foodId){
		  ResponseData responseData = new ResponseData();
		  responseData.setData(foodServiceImp.deleteFood(foodId));
		  
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		  
	  }
	  @GetMapping("/getAllFood")
	  public ResponseEntity<?> getAllFood(){
		  ResponseData responseData = new ResponseData();
		  responseData.setData(foodServiceImp.getAllFood());
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
	  }
	  
}
