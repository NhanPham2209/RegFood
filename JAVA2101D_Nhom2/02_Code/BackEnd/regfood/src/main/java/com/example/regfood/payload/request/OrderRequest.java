package com.example.regfood.payload.request;

import java.util.Date;
import java.util.List;

public class OrderRequest {
	private int userId;
	private int addressId;
	private double totalAmount;
	private int promotionId;
	private int quantity;
	private String note;
	private int status = 1; // đang xử lí
	private int payment;
	List<ItemFoodRequest> itemFoodRequests;
	
	public List<ItemFoodRequest> getItemFoodRequests() {
		return itemFoodRequests;
	}
	public void setItemFoodRequests(List<ItemFoodRequest> itemFoodRequests) {
		this.itemFoodRequests = itemFoodRequests;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAddressId() {
		return addressId;
	}
	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getPromotionId() {
		return promotionId;
	}
	public void setPromotionId(int promotionId) {
		this.promotionId = promotionId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getPayment() {
		return payment;
	}
	public void setPayment(int payment) {
		this.payment = payment;
	}
	
	
}
