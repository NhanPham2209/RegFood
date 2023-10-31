package com.example.regfood.dto;

import java.util.List;

public class BillDetailDTO {
	private int id;
	private int quantity;
	
	FoodDTO foodDTOs;
	
	public FoodDTO getFoodDTOs() {
		return foodDTOs;
	}
	public void setFoodDTOs(FoodDTO foodDTOs) {
		this.foodDTOs = foodDTOs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
	
	
}
