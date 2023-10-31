package com.example.regfood.service.imp;

import java.util.List;

import com.example.regfood.dto.PromotionDTO;
import com.example.regfood.entities.Promotion;

public interface PromotionServiceImp {
	List<PromotionDTO> getAllPromotion();
	boolean addPromoiton(int percent,boolean status);
	boolean editPromotion(int id ,int percent,boolean status);
	PromotionDTO getPromotion(int id);
}
