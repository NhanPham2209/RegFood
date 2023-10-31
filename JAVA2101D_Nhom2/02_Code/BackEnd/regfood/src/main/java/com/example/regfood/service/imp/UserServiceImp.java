package com.example.regfood.service.imp;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.dto.UserDTO;
import com.example.regfood.payload.request.ChangePassWordRequest;

public interface UserServiceImp {
	UserDTO getDetailUserDTO(String email);
	boolean changeAvatar(String email, MultipartFile file);
	boolean changePassWord(ChangePassWordRequest changePassWordRequest);
	List<UserDTO> getAllCustommer();
}
