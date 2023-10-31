package com.example.regfood.service.imp;

import java.util.List;
import java.util.Map;

import com.example.regfood.entities.Bills;

public interface SalesServiceImp {
	double getRevenueLastMonth();
	double getRevenueToday();
	List<Object[]> getRevenueLastSixMonths();
}
