package com.example.regfood.service.imp;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.dto.PostDTO;

public interface PostServiceImp {
	List<PostDTO> getAllPosts();
	boolean addPost(int userId, String title, String content, MultipartFile file);
	boolean editPost(int postId,int userId, String title, String content, MultipartFile file);
	boolean deletePost(int id);
	PostDTO getPostById(int id);
}
