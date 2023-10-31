package com.example.regfood.dto;

import java.util.List;

public class FoodDTO {
	private int id;
	private String foodName;
	private double price;
	private double ratingPoint;
	private String description;
	CategoryDTO categoryDTO;
	List<FoodImageDTO> foodImageDTOs;
	List<RatingFoodDTO> ratingFoodDTOs;
	
	
	
	public CategoryDTO getCategoryDTO() {
		return categoryDTO;
	}
	public void setCategoryDTO(CategoryDTO categoryDTO) {
		this.categoryDTO = categoryDTO;
	}
	public List<RatingFoodDTO> getRatingFoodDTOs() {
		return ratingFoodDTOs;
	}
	public void setRatingFoodDTOs(List<RatingFoodDTO> ratingFoodDTOs) {
		this.ratingFoodDTOs = ratingFoodDTOs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public double getRatingPoint() {
		return ratingPoint;
	}
	public void setRatingPoint(double ratingPoint) {
		this.ratingPoint = ratingPoint;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<FoodImageDTO> getFoodImageDTOs() {
		return foodImageDTOs;
	}
	public void setFoodImageDTOs(List<FoodImageDTO> foodImageDTOs) {
		this.foodImageDTOs = foodImageDTOs;
	}
	
	
}
