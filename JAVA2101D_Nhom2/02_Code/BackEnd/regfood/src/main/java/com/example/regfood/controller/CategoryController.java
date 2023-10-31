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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.regfood.dto.CategoryDTO;
import com.example.regfood.payload.ResponseData;
import com.example.regfood.service.imp.CategoryServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryServiceImp categoryServiceImp;
	
	@GetMapping("")
	public ResponseEntity<?> getCategory(){
		ResponseData responseData = new ResponseData();
		responseData.setData(categoryServiceImp.getAllCategories());
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	@GetMapping("/getCategory")
	public ResponseEntity<?> getCategoryById(@RequestParam int categoryId){
		ResponseData responseData = new ResponseData();
		responseData.setData(categoryServiceImp.getCategoryById(categoryId));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@PostMapping("/add-category")
	public ResponseEntity<?> addNewCategory(@RequestBody CategoryDTO categoryDTO){
		ResponseData responseData = new ResponseData();
		responseData.setData(categoryServiceImp.addCategory(categoryDTO));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@PostMapping("/edit-category")
	public ResponseEntity<?> editCategory(@RequestParam int categoryId , @RequestBody CategoryDTO categoryDTO){
		ResponseData responseData = new ResponseData();
		responseData.setData(categoryServiceImp.editCategory(categoryId,categoryDTO));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@PostMapping("/delete-category/{categoryId}")
	public ResponseEntity<?> deleteCategory(@PathVariable("categoryId") int categoryId){
		ResponseData responseData = new ResponseData();
		System.out.println(categoryId);
		responseData.setData(categoryServiceImp.deleteCategory(categoryId));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
}
