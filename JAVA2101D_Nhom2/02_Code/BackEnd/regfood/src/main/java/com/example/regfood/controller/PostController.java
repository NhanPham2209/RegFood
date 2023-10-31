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
import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.payload.ResponseData;
import com.example.regfood.service.imp.PostServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("/post")
public class PostController {
	@Autowired
	PostServiceImp postServiceImp;
	@GetMapping("")
	public ResponseEntity<?> getAllPost(){
		ResponseData responseData = new ResponseData();
		responseData.setData(postServiceImp.getAllPosts());
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	@GetMapping("getPost")
	public ResponseEntity<?> getPostById(@RequestParam int postId){
		ResponseData responseData = new ResponseData();
		responseData.setData(postServiceImp.getPostById(postId));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@PostMapping("/add-post")
	public ResponseEntity<?> addPost(@RequestParam int userId, @RequestParam String title,@RequestParam String content, @RequestParam MultipartFile file){
		ResponseData responseData = new ResponseData();
		responseData.setData(postServiceImp.addPost(userId, title, content, file));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@PostMapping("/edit-post")
	public ResponseEntity<?> editPost(@RequestParam int postId,@RequestParam int userId, @RequestParam String title,@RequestParam String content, @RequestParam(value = "file", required = false) MultipartFile file){
		ResponseData responseData = new ResponseData();
		responseData.setData(postServiceImp.editPost(postId, userId, title, content, file));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	@PostMapping("/delete-post/{postId}")
	public ResponseEntity<?> deletePost(@PathVariable("postId")int postId){
		ResponseData responseData = new ResponseData();
		responseData.setData(postServiceImp.deletePost(postId));
		return new ResponseEntity<>(responseData,HttpStatus.OK);
	}
	
	
	
	
}
