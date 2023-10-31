package com.example.regfood.dto;

public class RatingRestaurantDTO {
	private int id;
	UserDTO userDTO;
	RestaurantDTO restaurantDTO;
	private String content;
	private int ratingPoint;
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
	public RestaurantDTO getRestaurantDTO() {
		return restaurantDTO;
	}
	public void setRestaurantDTO(RestaurantDTO restaurantDTO) {
		this.restaurantDTO = restaurantDTO;
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
