package com.example.regfood.service.imp;

import java.util.List;

import com.example.regfood.dto.UserDTO;
import com.example.regfood.entities.Users;
import com.example.regfood.payload.request.SignUpRequest;

public interface LoginServiceImp {
	List<UserDTO> getAllUser();
    boolean checkLoginUser(String email, String passWord);
    boolean addUser(SignUpRequest signUpRequest);
    boolean checkLoginAdmin(String email, String passWord);
    boolean AddAdmin(SignUpRequest signUpRequest);
    UserDTO  userInfo(String email);
}
