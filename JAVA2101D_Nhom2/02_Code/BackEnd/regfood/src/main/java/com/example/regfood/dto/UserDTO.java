package com.example.regfood.dto;

import java.util.List;

public class UserDTO {
	private int id;
	private String userName;
	private String email;
	private String numberPhone;
	private String passWord;
	private String avatar;
	List<AddressDTO> addressDTOs;
	List<BillDTO> billDTOs;
	List<RatingFoodDTO> ratingFoodDTOs;
	
	
	public List<BillDTO> getBillDTOs() {
		return billDTOs;
	}
	public void setBillDTOs(List<BillDTO> billDTOs) {
		this.billDTOs = billDTOs;
	}
	public List<RatingFoodDTO> getRatingFoodDTOs() {
		return ratingFoodDTOs;
	}
	public void setRatingFoodDTOs(List<RatingFoodDTO> ratingFoodDTOs) {
		this.ratingFoodDTOs = ratingFoodDTOs;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public List<AddressDTO> getAddressDTOs() {
		return addressDTOs;
	}
	public void setAddressDTOs(List<AddressDTO> addressDTOs) {
		this.addressDTOs = addressDTOs;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNumberPhone() {
		return numberPhone;
	}
	public void setNumberPhone(String numberPhone) {
		this.numberPhone = numberPhone;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	
}
