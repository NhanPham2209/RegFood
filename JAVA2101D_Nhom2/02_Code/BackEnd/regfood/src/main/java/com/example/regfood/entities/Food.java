package com.example.regfood.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "food")
public class Food {
	@Id
	@Column(name = "food_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "food_name")
	private String foodName;
	
	@Column(name = "price")
	private double price;
	
	@Column(name = "description")
	private String description;
	
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	
	@OneToMany(mappedBy = "food")
	private Set<RatingFood> listRatingFoods;
	
	@OneToMany(mappedBy = "food")
	private Set<BillDetail> listBillDetails;
	
	@OneToMany(mappedBy = "food")
	private Set<FoodImage> listFoodImages;
	
	public Set<FoodImage> getListFoodImages() {
		return listFoodImages;
	}

	public void setListFoodImages(Set<FoodImage> listFoodImages) {
		this.listFoodImages = listFoodImages;
	}

	public Set<BillDetail> getListBillDetails() {
		return listBillDetails;
	}

	public void setListBillDetails(Set<BillDetail> listBillDetails) {
		this.listBillDetails = listBillDetails;
	}

	public Set<RatingFood> getListRatingFoods() {
		return listRatingFoods;
	}
	
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setListRatingFoods(Set<RatingFood> listRatingFoods) {
		this.listRatingFoods = listRatingFoods;
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

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	
	
}
