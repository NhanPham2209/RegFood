package com.example.regfood.dto;

import java.util.List;

public class RestaurantDTO {
	private int id;
	private String restaurantName;
	private String imageLogo;
	private String address;
	private String numberPhone;
	private String email;
	private String description;
	private double ratingPoint;
	List<CategoryDTO> categoryDTOs;
	public String getRestaurantName() {
		return restaurantName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getImageLogo() {
		return imageLogo;
	}
	public void setImageLogo(String imageLogo) {
		this.imageLogo = imageLogo;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getNumberPhone() {
		return numberPhone;
	}
	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getRatingPoint() {
		return ratingPoint;
	}
	public void setRatingPoint(double ratingPoint) {
		this.ratingPoint = ratingPoint;
	}
	public List<CategoryDTO> getCategoryDTOs() {
		return categoryDTOs;
	}
	public void setCategoryDTOs(List<CategoryDTO> categoryDTOs) {
		this.categoryDTOs = categoryDTOs;
	}
	
	
}
