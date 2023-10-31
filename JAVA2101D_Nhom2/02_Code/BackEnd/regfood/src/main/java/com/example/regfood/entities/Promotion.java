package com.example.regfood.entities;

import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity(name = "promotion")
public class Promotion {
	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "percent")
	private int percent;
	@Column(name = "status")
	private boolean status;
	
	@OneToMany(mappedBy = "promotion")
	private Set<Bills> listBills;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPercent() {
		return percent;
	}

	public void setPercent(int percent) {
		this.percent = percent;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Set<Bills> getListBills() {
		return listBills;
	}

	public void setListBills(Set<Bills> listBills) {
		this.listBills = listBills;
	}
	
	
}
