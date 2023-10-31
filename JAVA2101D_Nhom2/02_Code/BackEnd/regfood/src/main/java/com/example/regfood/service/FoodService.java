package com.example.regfood.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.dto.CategoryDTO;
import com.example.regfood.dto.FoodDTO;
import com.example.regfood.dto.FoodImageDTO;
import com.example.regfood.dto.RatingFoodDTO;
import com.example.regfood.dto.UserDTO;
import com.example.regfood.entities.Category;
import com.example.regfood.entities.Food;
import com.example.regfood.entities.FoodImage;
import com.example.regfood.entities.RatingFood;
import com.example.regfood.entities.RatingRestaurant;
import com.example.regfood.entities.Users;
import com.example.regfood.payload.request.FoodRequest;
import com.example.regfood.repository.FoodImageRepository;
import com.example.regfood.repository.FoodRepository;
import com.example.regfood.service.imp.FileServieImp.FileServiceImp;
import com.example.regfood.service.imp.FoodServiceImp;
@Service
public class FoodService implements FoodServiceImp  {
	@Autowired
	FoodImageRepository foodImageRepository;
	@Autowired
	FoodRepository foodRepository;
	@Autowired 
	FileServiceImp fileServiceImp;
	@Override
	public boolean addFood(MultipartFile[] file, String foodName,int categoryId,double price , String description) {
		
		Food food = new Food();
		Category category = new Category();
		category.setId(categoryId);
		food.setFoodName(foodName);
		food.setPrice(price);
		food.setDescription(description);
		food.setCategory(category);
		boolean isSaveFoodSuccess = false; 
		try {
			Food foodsaved = foodRepository.save(food);
			if(foodsaved !=null ) {
				for (MultipartFile multipartFile : file) {
					boolean isSaveFileSuccess = fileServiceImp.saveFile(multipartFile);
					if (isSaveFileSuccess) {
						FoodImage foodImage = new FoodImage();
						foodImage.setFood(foodsaved);
						foodImage.setImage(multipartFile.getOriginalFilename());
						foodImageRepository.save(foodImage);
						isSaveFoodSuccess = true;
					}
				}
			}
			
			
		} catch (Exception e) {
			System.out.println("Error insert restaurant "+e.getMessage());
		}
		return isSaveFoodSuccess;
	}
	@Override
	public boolean editFood(int foodId,MultipartFile[] file, String foodName, int categoryId, double price, String description) {
		Food food = foodRepository.findFoodById(foodId);
		Category category = new Category();
		category.setId(categoryId);
		food.setFoodName(foodName);
		food.setPrice(price);
		food.setDescription(description);
		food.setCategory(category);
		boolean isUpdateFoodSuccess = false; 
		try {
			Food foodsaved = foodRepository.save(food);
			if(file != null && file.length > 1) { // kiểm tra xem người dùng có chọn ảnh ko
				System.out.println("file:"+file);
				foodImageRepository.deleteFoodImage(foodId);
				for (MultipartFile multipartFile : file) {
					boolean isSaveFileSuccess = fileServiceImp.saveFile(multipartFile);
					if (isSaveFileSuccess) {
						FoodImage foodImage = new FoodImage();
						foodImage.setFood(foodsaved);
						foodImage.setImage(multipartFile.getOriginalFilename());
						foodImageRepository.save(foodImage);
						
					}
				}

			}
			isUpdateFoodSuccess = true;
			
		} catch (Exception e) {
			System.out.println("Error insert food "+e.getMessage());
		}
		return isUpdateFoodSuccess;
	}
	@Override
	public boolean deleteFood(int foodId) {
		try {
			foodRepository.deleteFood(foodId);
			return true;
		} catch (Exception e) {
			System.out.println("Error delete food "+e.getMessage());
			return false;
			// TODO: handle exception
		}
	}
	
	  private double caculatorRating(Set<RatingFood> listRating){
	        double totalPoint =0;
	        for (RatingFood data : listRating) {
	            totalPoint = totalPoint + data.getRatingPoint();
	        }
	        return totalPoint / listRating.size();
	    }
	@Override
	public FoodDTO findById(int id) {
		Food food= foodRepository.findFoodById(id);
		FoodDTO foodDTO = new FoodDTO();
		foodDTO.setId(food.getId());
		foodDTO.setFoodName(food.getFoodName());
		foodDTO.setPrice(food.getPrice());
		foodDTO.setDescription(food.getDescription());
		foodDTO.setRatingPoint(caculatorRating(food.getListRatingFoods()));
		List<FoodImageDTO> listFoodImageDTOs = new ArrayList<>();
		for (FoodImage foodImage :food.getListFoodImages() ) {
			FoodImageDTO foodImageDTO = new FoodImageDTO();
			foodImageDTO.setImageName(foodImage.getImage());
			listFoodImageDTOs.add(foodImageDTO);
		}
		
		List<RatingFoodDTO> listRatingFoodDTOs = new ArrayList<>();
		for (RatingFood ratingFood : food.getListRatingFoods()) {
			RatingFoodDTO ratingFoodDTO = new RatingFoodDTO();
			ratingFoodDTO.setId(ratingFood.getId());
			ratingFoodDTO.setContent(ratingFood.getContent());
			ratingFoodDTO.setRatingPoint(ratingFood.getRatingPoint());
			
			UserDTO userDTO = new UserDTO();
			userDTO.setId(ratingFood.getUsers().getId());
			userDTO.setAvatar(ratingFood.getUsers().getAvatar());
			userDTO.setUserName(ratingFood.getUsers().getFullName());
			
			ratingFoodDTO.setUserDTO(userDTO);
			 
		    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		    String formattedDate = dateFormat.format(ratingFood.getCreatedDate());
			ratingFoodDTO.setCreatedDate(formattedDate);
			listRatingFoodDTOs.add(ratingFoodDTO);
			
		}
		foodDTO.setRatingFoodDTOs(listRatingFoodDTOs);
		foodDTO.setFoodImageDTOs(listFoodImageDTOs);
		CategoryDTO categoryDTO = new CategoryDTO();
		categoryDTO.setId(food.getCategory().getId());
		categoryDTO.setCategoryName(food.getCategory().getCategoryName());
		categoryDTO.setStatus(food.getCategory().getStatus());
		foodDTO.setCategoryDTO(categoryDTO);
		return foodDTO;
	}
	@Override
	public List<FoodDTO> getAllFood() {
		List<Food> listFoods =  foodRepository.findAll();
		List<FoodDTO> listFoodDTOs = new ArrayList<>();
		for(Food food : listFoods) {
			FoodDTO foodDTO = new FoodDTO();
			foodDTO.setId(food.getId());
			foodDTO.setFoodName(food.getFoodName());
			foodDTO.setPrice(food.getPrice());
				List<FoodImageDTO> listFoodImageDTOs = new ArrayList<>();
				for(FoodImage foodImage : food.getListFoodImages()) {
					FoodImageDTO foodImageDTO = new FoodImageDTO();
					foodImageDTO.setImageName(foodImage.getImage());
					listFoodImageDTOs.add(foodImageDTO);
				}
				CategoryDTO categoryDTO = new CategoryDTO();
				categoryDTO.setCategoryName(food.getCategory().getCategoryName());
				categoryDTO.setId(food.getCategory().getId());
			foodDTO.setFoodImageDTOs(listFoodImageDTOs);
			foodDTO.setCategoryDTO(categoryDTO);
			listFoodDTOs.add(foodDTO);
		}
		return listFoodDTOs;
	}
	
}
