package com.example.regfood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.regfood.dto.PromotionDTO;
import com.example.regfood.entities.Promotion;
import com.example.regfood.repository.PromotionRepository;
import com.example.regfood.service.imp.PromotionServiceImp;
@Service
public class PromotionService implements PromotionServiceImp {
	@Autowired
	PromotionRepository promotionRepository;
	@Override
	public List<PromotionDTO> getAllPromotion() {
		List<PromotionDTO> listPromotionDTOs = new ArrayList<>();
		List<Promotion> listPromotions = promotionRepository.findPromotionByStatus();
		for (Promotion promotion : listPromotions) {
			PromotionDTO promotionDTO = new PromotionDTO();
			promotionDTO.setId(promotion.getId());
			promotionDTO.setPercent(promotion.getPercent());
			promotionDTO.setStatus(promotion.isStatus());
			listPromotionDTOs.add(promotionDTO);
		}
		return listPromotionDTOs;
	}
	@Override
	public boolean addPromoiton(int percent, boolean status) {
		Promotion promotion = new Promotion();
		promotion.setPercent(percent);
		promotion.setStatus(status);
		try {
			promotionRepository.save(promotion);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public boolean editPromotion(int id, int percent, boolean status) {
		Promotion promotionSaved = promotionRepository.findPromotionById(id); 
		promotionSaved.setPercent(percent);
		promotionSaved.setStatus(status);
		try {
			promotionRepository.save(promotionSaved);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public PromotionDTO getPromotion(int id) {
		// TODO Auto-generated method stub
		Promotion promotion =  promotionRepository.findPromotionById(id);
		PromotionDTO promotionDTO = new PromotionDTO();
		promotionDTO.setId(promotion.getId());
		promotionDTO.setPercent(promotion.getPercent());
		promotionDTO.setStatus(promotion.isStatus());
		return promotionDTO;
	}

}
