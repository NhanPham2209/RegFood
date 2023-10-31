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

@Entity(name = "users")
public class Users {
	@Id
	@Column(name = "user_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="fullname")
	private String fullName;
	
	@Column(name = "avatar")
	private String avatar;
	
	@Column(name = "numberphone")
	private String numberPhone;
	
	@Column(name = "password")
	private String passWord ;
	
	@Column(name = "email")
	private String email;
	
	

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Roles roles;
	
	@OneToMany(mappedBy = "users")
    private Set<RatingFood> listRatingFood;
	
	@OneToMany(mappedBy = "users")
	private Set<RatingRestaurant> listRatingRestaurants;
	
	@OneToMany(mappedBy = "users")
	private Set<Address> listAddresses;
	
	@OneToMany(mappedBy = "users")
	private Set<Bills> listBills;
	
	@OneToMany(mappedBy = "users")
	private Set<Posts> listPosts;
	
	
	public Set<Posts> getListPosts() {
		return listPosts;
	}

	public void setListPosts(Set<Posts> listPosts) {
		this.listPosts = listPosts;
	}

	public Set<Bills> getListBills() {
		return listBills;
	}

	public void setListBills(Set<Bills> listBills) {
		this.listBills = listBills;
	}

	public Set<Address> getListAddresses() {
		return listAddresses;
	}

	public void setListAddresses(Set<Address> listAddresses) {
		this.listAddresses = listAddresses;
	}

	public Set<RatingRestaurant> getListRatingRestaurants() {
		return listRatingRestaurants;
	}

	public void setListRatingRestaurants(Set<RatingRestaurant> listRatingRestaurants) {
		this.listRatingRestaurants = listRatingRestaurants;
	}

	public Set<RatingFood> getListRatingFood() {
		return listRatingFood;
	}

	public void setListRatingFood(Set<RatingFood> listRatingFood) {
		this.listRatingFood = listRatingFood;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getNumberPhone() {
		return numberPhone;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Roles getRoles() {
		return roles;
	}

	public void setRoles(Roles roles) {
		this.roles = roles;
	}
	
	
}
