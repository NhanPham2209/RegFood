package com.example.regfood.dto;

import java.util.Date;
import java.util.List;

public class BillDTO {
	private int id;
	UserDTO userDTO;
	AddressDTO addressDTO;
	private double totalAmount;
	PromotionDTO promotionDTO;
	private int quantity ;
	private String note;
	private int status;
	private int payment;
	private Date createdDate;
	List<BillDetailDTO> billDetailDTOs;
	
	
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public AddressDTO getAddressDTO() {
		return addressDTO;
	}
	public void setAddressDTO(AddressDTO addressDTO) {
		this.addressDTO = addressDTO;
	}
	public double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public PromotionDTO getPromotionDTO() {
		return promotionDTO;
	}
	public void setPromotionDTO(PromotionDTO promotionDTO) {
		this.promotionDTO = promotionDTO;
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

	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public List<BillDetailDTO> getBillDetailDTOs() {
		return billDetailDTOs;
	}
	public void setBillDetailDTOs(List<BillDetailDTO> billDetailDTOs) {
		this.billDetailDTOs = billDetailDTOs;
	}
	
}
