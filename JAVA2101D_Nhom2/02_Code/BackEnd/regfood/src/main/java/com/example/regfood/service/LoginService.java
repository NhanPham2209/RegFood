package com.example.regfood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.regfood.dto.UserDTO;
import com.example.regfood.entities.Roles;
import com.example.regfood.entities.Users;
import com.example.regfood.payload.request.SignUpRequest;
import com.example.regfood.repository.UserRepository;
import com.example.regfood.service.imp.LoginServiceImp;
@Service
public class LoginService implements LoginServiceImp{
	@Autowired
	UserRepository userRepository;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public List<UserDTO> getAllUser() {
		List<Users> listUser = userRepository.findAll();
        List<UserDTO> listUserDTO = new ArrayList<>();
        for (Users users : listUser) {
            UserDTO userDTO = new UserDTO();
            userDTO.setId(users.getId());
            userDTO.setUserName(users.getFullName());
            userDTO.setEmail(users.getEmail());
            userDTO.setNumberPhone(users.getNumberPhone());
            userDTO.setPassWord(users.getPassWord());
            

            listUserDTO.add(userDTO);
            
        }
        return listUserDTO;
	}

	@Override
	public boolean checkLoginUser(String email, String passWord) {
		Users users = userRepository.findByEmail(email);
//		System.out.println(users.getEmail() + " " + users.getPassWord());
		return passwordEncoder.matches(passWord, users.getPassWord()); //kiểm tra xem password người dùng nhập có giống với password trên database hay ko
		
	}

	@Override
	public boolean addUser(SignUpRequest signUpRequest) {
		Users users = new Users();
		Roles roles = new Roles();
		if(userRepository.findByEmail(signUpRequest.getEmail()) != null) {
			return false;
		}
		roles.setId(2);
		users.setFullName(signUpRequest.getfullName());
		users.setEmail(signUpRequest.getEmail());
		users.setNumberPhone(signUpRequest.getNumberPhone());
		users.setAvatar("avatardefault.jpg");
		// mã hóa mật khẩu người dùng nhập thành bcrypt rồi lưu vào database
		String encodedPassword = passwordEncoder.encode(signUpRequest.getPassWord()); 
		users.setPassWord(encodedPassword);
		users.setRoles(roles);
		
		try {
			userRepository.save(users);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean checkLoginAdmin(String email, String passWord) {
		Users users = userRepository.findByEmail(email);
		if(users.getRoles().getId()==1 && passwordEncoder.matches(passWord, users.getPassWord())) {
			return true;
		}else {
			return false;
		}
	}

	@Override
	public boolean AddAdmin(SignUpRequest signUpRequest) {
		Users users = new Users();
		Roles roles = new Roles();
		if(userRepository.findByEmail(signUpRequest.getEmail()) != null) {
			return false;
		}
		roles.setId(1);
		users.setFullName(signUpRequest.getfullName());
		users.setEmail(signUpRequest.getEmail());
		users.setAvatar("avatardefault.jpg");
		
		users.setNumberPhone(signUpRequest.getNumberPhone());
		String encodedPassword = passwordEncoder.encode(signUpRequest.getPassWord()); 
		users.setPassWord(encodedPassword);
		users.setRoles(roles);
		
		try {
			userRepository.save(users);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public UserDTO userInfo(String email) {
		Users user = userRepository.findByEmail(email);
		UserDTO userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setEmail(user.getEmail());
		userDTO.setUserName(user.getFullName());
		return userDTO;
	}

	

}
