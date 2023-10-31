package com.example.regfood.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.dto.CategoryDTO;
import com.example.regfood.dto.FoodDTO;
import com.example.regfood.dto.FoodImageDTO;
import com.example.regfood.dto.RestaurantDTO;
import com.example.regfood.entities.Category;
import com.example.regfood.entities.Food;
import com.example.regfood.entities.FoodImage;
import com.example.regfood.entities.RatingFood;
import com.example.regfood.entities.RatingRestaurant;
import com.example.regfood.entities.Restaurant;
import com.example.regfood.repository.CategoryRepository;
import com.example.regfood.repository.RestaurantRepository;
import com.example.regfood.service.imp.FileServieImp.FileServiceImp;
import com.example.regfood.service.imp.RestaurantServiceImp;
@Service
public class RestaurantService implements RestaurantServiceImp{
	@Autowired
	CategoryRepository categoryRepository;
	@Autowired
	
	RestaurantRepository restaurantRepository;
	@Autowired
	FileServiceImp fileServiceImp;
	@Override
	public boolean editRestaurant( String restaurantName,String address,
			String numberPhone, String email, String description) {
		Restaurant restaurant = restaurantRepository.findRestaurantById(1);
		restaurant.setRestaurantName(restaurantName);
		restaurant.setNumberPhone(numberPhone);
		restaurant.setEmail(email);
		restaurant.setAddress(address);
		restaurant.setDescription(description);
		try {
			restaurantRepository.save(restaurant);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addRestaurant(String restaurantName, MultipartFile file, String address, String numberPhone,
			String email, String description) {
		boolean isSaveRestaurantSuccess = false;
		try {
			boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
			if(isSaveFileSuccess) {
				Restaurant restaurant = new Restaurant();
				restaurant.setRestaurantName(restaurantName);
				restaurant.setNumberPhone(numberPhone);
				restaurant.setImageLogo(file.getOriginalFilename());
				restaurant.setEmail(email);
				restaurant.setAddress(address);
				restaurant.setDescription(description);
				restaurantRepository.save(restaurant);
				isSaveRestaurantSuccess = true;
			}
		} catch (Exception e) {
			 System.out.println("Error insert restaurant "+e.getMessage());
		}
		
		return isSaveRestaurantSuccess;
	}

	@Override
	public List<Restaurant> getRestaurants() {
		List<Restaurant> restaurants = restaurantRepository.findAll();
		
		return restaurants;
	}
	

    private double caculatorRatingRestaurant(Set<RatingRestaurant> listRating){
        double totalPoint =0;
        for (RatingRestaurant data : listRating) {
            totalPoint = totalPoint + data.getRatingPoint();
        }
        return totalPoint / listRating.size();
    }
    private double caculatorRatingFood(Set<RatingFood> listRating){
        double totalPoint =0;
        for (RatingFood data : listRating) {
            totalPoint = totalPoint + data.getRatingPoint();
        }
        return totalPoint / listRating.size();
    }
	@Override
	public RestaurantDTO getDetailRestaurant() {
		// Optional giúp kiểm tra và tránh restaurant entity bị null
		Optional<Restaurant> restaurant = restaurantRepository.findById(1);
		List<Category> categoriesList = categoryRepository.findCategoryByStatus();
		RestaurantDTO restaurantDTO = new RestaurantDTO();
		if(restaurant.isPresent()) { // kiểm tra xem restaurant có dữ liệu hay ko
			
			List<CategoryDTO> listCategoryDTOs = new ArrayList<>();
			Restaurant restaurantData = restaurant.get(); 
			restaurantDTO.setId(restaurantData.getId());
			restaurantDTO.setRestaurantName(restaurantData.getRestaurantName());
			restaurantDTO.setImageLogo(restaurantData.getImageLogo());
			restaurantDTO.setAddress(restaurantData.getAddress());
			restaurantDTO.setNumberPhone(restaurantData.getNumberPhone());
			restaurantDTO.setEmail(restaurantData.getEmail());
			restaurantDTO.setDescription(restaurantData.getDescription());
			restaurantDTO.setRatingPoint(caculatorRatingRestaurant(restaurantData.getListRatingRestaurants()));
			
			// duyệt categoriesList
			for (Category category : categoriesList) {
				List<FoodDTO> listFoodDTOs = new ArrayList<>();
				
				CategoryDTO categoryDTO = new CategoryDTO();
				categoryDTO.setId(category.getId());
				categoryDTO.setCategoryName(category.getCategoryName());
				categoryDTO.setCreateDate(category.getCreatedDate());
				categoryDTO.setStatus(category.getStatus());
				//duyệt food
				for( Food food: category.getListFoods() ) {
					List<FoodImageDTO> listFoodImageDTOs = new ArrayList<>(); 
					FoodDTO foodDTO = new FoodDTO();
					foodDTO.setId(food.getId());
					foodDTO.setFoodName(food.getFoodName());
					foodDTO.setPrice(food.getPrice());
					foodDTO.setDescription(food.getDescription());
					foodDTO.setRatingPoint(caculatorRatingFood(food.getListRatingFoods()));
					listFoodDTOs.add(foodDTO);
					
					//duyệt foodImage
					for(FoodImage foodImage : food.getListFoodImages()) {
						FoodImageDTO foodImageDTO = new FoodImageDTO();
						foodImageDTO.setImageName(foodImage.getImage());
						listFoodImageDTOs.add(foodImageDTO);
					}
					foodDTO.setFoodImageDTOs(listFoodImageDTOs);
				}
				categoryDTO.setFoodDTOs(listFoodDTOs); 
				listCategoryDTOs.add(categoryDTO);
			}
			
			restaurantDTO.setCategoryDTOs(listCategoryDTOs);
			
		}
		return restaurantDTO;
	}

	@Override
	public boolean changeAvatarRestaurant(MultipartFile file) {
		Restaurant restaurant = restaurantRepository.findRestaurantById(1);
		if(file != null) {
			boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
			if(isSaveFileSuccess) {
				restaurant.setImageLogo(file.getOriginalFilename());
			}
		}
		try {
			restaurantRepository.save(restaurant);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

}
