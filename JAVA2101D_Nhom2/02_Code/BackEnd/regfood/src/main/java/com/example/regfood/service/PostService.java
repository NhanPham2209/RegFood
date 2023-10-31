package com.example.regfood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.dto.PostDTO;
import com.example.regfood.dto.UserDTO;
import com.example.regfood.entities.Posts;
import com.example.regfood.entities.Restaurant;
import com.example.regfood.entities.Users;
import com.example.regfood.repository.PostRepository;
import com.example.regfood.service.imp.FileServieImp.FileServiceImp;
import com.example.regfood.service.imp.PostServiceImp;
@Service
public class PostService implements PostServiceImp{
	@Autowired
	PostRepository postRepository;
	@Autowired
	FileServiceImp fileServiceImp;
	@Override
	public List<PostDTO> getAllPosts() {
		List<Posts> listPosts = postRepository.findAll();
		List<PostDTO> listPosstDtos = new ArrayList<>();
		
		for(Posts post : listPosts) {
			PostDTO postDTO = new PostDTO();
			postDTO.setId(post.getId());
				UserDTO userDTO = new UserDTO();
				userDTO.setId(post.getUsers().getId());
				userDTO.setUserName(post.getUsers().getFullName());
				userDTO.setEmail(post.getUsers().getEmail());
			postDTO.setUserDTO(userDTO);
			postDTO.setTitle(post.getTitle());
			postDTO.setContent(post.getContent());
			postDTO.setImage(post.getImage());
			listPosstDtos.add(postDTO);
		}
		return listPosstDtos;
	}

	@Override
	public boolean addPost(int userId, String title, String content, MultipartFile file) {
		
		boolean isSavePostSuccess = false;
		try {
			boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
			if(isSaveFileSuccess) {
				Posts posts = new Posts();
				Users user = new Users();
				user.setId(userId);
				posts.setUsers(user);
				posts.setTitle(title);
				posts.setContent(content);
				posts.setImage(file.getOriginalFilename());
				postRepository.save(posts);
				isSavePostSuccess = true;
			}
		} catch (Exception e) {
			 System.out.println("Error insert restaurant "+e.getMessage());
		}
		
		
		return isSavePostSuccess;
	}

	@Override
	public boolean editPost(int postId ,int userId, String title, String content, MultipartFile file) {
		Posts posts = postRepository.findPostById(postId);
		Users user = new Users();
		user.setId(userId);
		posts.setUsers(user);
		posts.setTitle(title);
		posts.setContent(content);
		if(file!=null) {
			boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
			if(isSaveFileSuccess) {
				posts.setImage(file.getOriginalFilename());
			}
		}
		try {
			postRepository.save(posts);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean deletePost(int id) {
		try {
			postRepository.deletePost(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public PostDTO getPostById(int id) {
		Posts post = postRepository.findPostById(id);
		PostDTO postDTO = new PostDTO();
		postDTO.setId(post.getId());
			UserDTO userDTO = new UserDTO();
			userDTO.setId(post.getUsers().getId());
			userDTO.setUserName(post.getUsers().getFullName());
			userDTO.setEmail(post.getUsers().getEmail());
		postDTO.setUserDTO(userDTO);
		postDTO.setTitle(post.getTitle());
		postDTO.setContent(post.getContent());
		postDTO.setImage(post.getImage());
		return postDTO;
	}

}
