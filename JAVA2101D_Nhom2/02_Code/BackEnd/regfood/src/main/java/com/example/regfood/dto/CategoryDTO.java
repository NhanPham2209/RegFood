package com.example.regfood.dto;

import java.util.Date;
import java.util.List;

public class CategoryDTO {
	private int id;
	private String categoryName;
	private boolean status;
	private Date createDate;
	List<FoodDTO> foodDTOs;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public List<FoodDTO> getFoodDTOs() {
		return foodDTOs;
	}
	public void setFoodDTOs(List<FoodDTO> foodDTOs) {
		this.foodDTOs = foodDTOs;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
