package com.example.regfood.service.imp;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.dto.FoodDTO;
import com.example.regfood.entities.Food;
import com.example.regfood.payload.request.FoodRequest;

public interface FoodServiceImp {
	boolean addFood(MultipartFile[] file, String foodName,int categoryId,double price , String description);
	boolean editFood(int foodId,MultipartFile [] file, String foodName,int categoryId,double price , String description);
	boolean deleteFood(int foodId);
	FoodDTO findById(int id);
	List<FoodDTO> getAllFood();
	
}
