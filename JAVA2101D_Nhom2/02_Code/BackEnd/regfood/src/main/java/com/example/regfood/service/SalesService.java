package com.example.regfood.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.regfood.entities.Bills;
import com.example.regfood.repository.BillsRepository;
import com.example.regfood.service.imp.SalesServiceImp;

@Service
public class SalesService implements SalesServiceImp{
	@Autowired
	BillsRepository billsRepository;

	@Override
	public double getRevenueLastMonth() {
		 Date now = new Date();
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(now);
	        calendar.add(Calendar.MONTH, -1);
	        Date oneMonthAgo = calendar.getTime();
        List<Bills> billsLastMonth = billsRepository.findByCreatedDateBetweenAndStatus(oneMonthAgo, now,2);
        double revenue = billsLastMonth.stream()
                .mapToDouble(Bills::getTotalAmount)
                .sum();
		return revenue;
	}

	@Override
	public double getRevenueToday() {
		 Date now = new Date();
	        Date startOfDay = truncateTime(now); // Hàm này sẽ cắt giờ, phút, giây, và mili giây về 00:00:00
	        
	        List<Bills> billsToday = billsRepository.findByCreatedDateBetweenAndStatus(startOfDay, now,2);
	        
	        double revenue = billsToday.stream()
	                .mapToDouble(Bills::getTotalAmount)
	                .sum();
		return revenue;
	}
	private Date truncateTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

	

	@Override
	public List<Object[]> getRevenueLastSixMonths() {
		List<Object[]> monthlyRevenue = new ArrayList<>();
		for (int i = 0; i < 7; i++) {
	        Calendar calendar = Calendar.getInstance();
	        calendar.add(Calendar.MONTH, -i);
	        Date fromDate = calendar.getTime();
	        calendar.add(Calendar.MONTH, 1);
	        Date toDate = calendar.getTime();

	        Object[] data = billsRepository.getTotalAmountAndDateRangeAndStatus(fromDate, toDate, 2);
	        monthlyRevenue.add(data);
	    }

		return monthlyRevenue;
	}
	
}
