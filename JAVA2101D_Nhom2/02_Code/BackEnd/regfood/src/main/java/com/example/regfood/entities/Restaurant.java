package com.example.regfood.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "restaurant")
public class Restaurant {
	@Id
	@Column(name = "restaurant_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "restaurant_name")
	private String restaurantName;
	
	@Column(name = "image_logo")
	private String imageLogo;
	
	@Column(name = "address")
	private String address;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "number_phone")
	private String numberPhone;
	
	@Column(name = "email")
	private String email;
	
	
	@OneToMany(mappedBy = "restaurant")
	private Set<RatingRestaurant> listRatingRestaurants;
	
	
	public Set<RatingRestaurant> getListRatingRestaurants() {
		return listRatingRestaurants;
	}

	public void setListRatingRestaurants(Set<RatingRestaurant> listRatingRestaurants) {
		this.listRatingRestaurants = listRatingRestaurants;
	}

	public int getId() {
		return id;
	}
	

	public void setId(int id) {
		this.id = id;
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

	public String getRestaurantName() {
		return restaurantName;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
}
