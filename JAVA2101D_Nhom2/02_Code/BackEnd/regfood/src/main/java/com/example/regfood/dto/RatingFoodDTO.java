package com.example.regfood.dto;

import java.util.Date;

public class RatingFoodDTO {
	private int id;
	UserDTO userDTO;
	FoodDTO foodDTO;
	private String content;
	private int ratingPoint;
	private String createdDate;
	
	public String getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(String createdDate) {
		this.createdDate = createdDate;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public FoodDTO getFoodDTO() {
		return foodDTO;
	}
	public void setFoodDTO(FoodDTO foodDTO) {
		this.foodDTO = foodDTO;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRatingPoint() {
		return ratingPoint;
	}
	public void setRatingPoint(int ratingPoint) {
		this.ratingPoint = ratingPoint;
	}
	
}
