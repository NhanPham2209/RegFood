package com.example.regfood.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.regfood.dto.AddressDTO;
import com.example.regfood.dto.BillDTO;
import com.example.regfood.dto.BillDetailDTO;
import com.example.regfood.dto.FoodDTO;
import com.example.regfood.dto.PromotionDTO;
import com.example.regfood.dto.UserDTO;
import com.example.regfood.entities.Address;
import com.example.regfood.entities.BillDetail;
import com.example.regfood.entities.Bills;
import com.example.regfood.entities.Food;
import com.example.regfood.entities.Promotion;
import com.example.regfood.entities.Users;
import com.example.regfood.payload.request.ItemFoodRequest;
import com.example.regfood.payload.request.OrderRequest;
import com.example.regfood.repository.BillDetailRepository;
import com.example.regfood.repository.BillsRepository;
import com.example.regfood.service.imp.BillsServiceImp;

import jakarta.transaction.Transactional;
@Service

public class BillService implements BillsServiceImp{
	@Autowired
	BillsRepository billsRepository;
	
	@Autowired
	BillDetailRepository billDetailRepository;
	@Override
	public boolean insertOrder(OrderRequest orderRequest) {
		try {
			Bills bills = new Bills();
			
			Users user = new Users();
			user.setId(orderRequest.getUserId());
			
			Address address = new Address();
			address.setId(orderRequest.getAddressId());
			
			Promotion promotion = new Promotion();
			promotion.setId(orderRequest.getPromotionId());
			
			bills.setUsers(user);
			bills.setAddress(address);
			bills.setTotalAmount(orderRequest.getTotalAmount());
			bills.setPromotion(promotion);
			bills.setQuantity(orderRequest.getQuantity());
			bills.setNote(orderRequest.getNote());
			bills.setStautus(orderRequest.getStatus());
			bills.setPayment(orderRequest.getPayment());
			Date currentDate = new Date();
			Timestamp timestamp = new Timestamp(currentDate.getTime()); 
			bills.setCreatedDate(timestamp);
			billsRepository.save(bills);
			List<BillDetail> listBillDetails = new ArrayList<>();
			for(ItemFoodRequest item : orderRequest.getItemFoodRequests()) {
				Food food = new Food();
				food.setId(item.getFoodId());
				
				BillDetail billDetail = new BillDetail();
				billDetail.setFood(food);
				billDetail.setQuantity(item.getQuantity());
				billDetail.setBills(bills);
				listBillDetails.add(billDetail);
			}
			billDetailRepository.saveAll(listBillDetails);
			return true;
		} catch (Exception e) {
			System.out.println("Error insert bill:" + e.getMessage());
			return false;
		}
		
		
		
		
	}
	@Override
	public BillDTO getBillDetailById(int billId) {
		Bills bills = billsRepository.findBillById(billId);
		List<BillDetailDTO> listBillDetailDTOs = new ArrayList<>();
		
		BillDTO billDTO = new BillDTO();
		billDTO.setId(bills.getId());
			UserDTO userDTO = new UserDTO();
			userDTO.setId(bills.getUsers().getId());
			userDTO.setAvatar(bills.getUsers().getAvatar());
			userDTO.setUserName(bills.getUsers().getFullName());
			userDTO.setEmail(bills.getUsers().getEmail());
			userDTO.setNumberPhone(bills.getUsers().getNumberPhone());
		billDTO.setUserDTO(userDTO);
		billDTO.setQuantity(bills.getQuantity());
		billDTO.setTotalAmount(bills.getTotalAmount());
		billDTO.setNote(bills.getNote());
		billDTO.setStatus(bills.getStautus());
		billDTO.setPayment(bills.getPayment());
		billDTO.setCreatedDate(bills.getCreatedDate());
		
			AddressDTO addressDTO1 = new AddressDTO();
			Address address1 = bills.getAddress();
			addressDTO1.setId(address1.getId());
			addressDTO1.setTitle(address1.getTitle());
			addressDTO1.setAddressDetail(address1.getAddressDetail());
		billDTO.setAddressDTO(addressDTO1);
			
			Promotion promotion = bills.getPromotion();
			PromotionDTO promotionDTO = new PromotionDTO();
			promotionDTO.setId(promotion.getId());
			promotionDTO.setPercent(promotion.getPercent());
		billDTO.setPromotionDTO(promotionDTO);
		
			for(BillDetail billDetail : bills.getListBillDetails()) {
				BillDetailDTO billDetailDTO = new BillDetailDTO();
				billDetailDTO.setId(billDetail.getId());
					Food food = billDetail.getFood();
					FoodDTO foodDTO = new FoodDTO();
					foodDTO.setId(food.getId());
					foodDTO.setFoodName(food.getFoodName());
					foodDTO.setPrice(food.getPrice());
				billDetailDTO.setFoodDTOs(foodDTO);
				billDetailDTO.setQuantity(billDetail.getQuantity());
				listBillDetailDTOs.add(billDetailDTO);
			}
		
		billDTO.setBillDetailDTOs(listBillDetailDTOs);
		return billDTO;
		
	}
	@Override
	public List<BillDTO> getAllBillDTOs() {
		List<Bills> listBills = billsRepository.findAll();
		List<BillDTO> listBillDTOs = new ArrayList<>();
		
		for(Bills bills : listBills) {
			List<BillDetailDTO> listBillDetailDTOs = new ArrayList<>();
			BillDTO billDTO = new BillDTO();
			billDTO.setId(bills.getId());
				UserDTO userDTO = new UserDTO();
				userDTO.setId(bills.getUsers().getId());
				userDTO.setAvatar(bills.getUsers().getAvatar());
				userDTO.setUserName(bills.getUsers().getFullName());
				userDTO.setEmail(bills.getUsers().getEmail());
				userDTO.setNumberPhone(bills.getUsers().getNumberPhone());
			billDTO.setUserDTO(userDTO);
			billDTO.setQuantity(bills.getQuantity());
			billDTO.setTotalAmount(bills.getTotalAmount());
			bills.setNote(bills.getNote());
			billDTO.setStatus(bills.getStautus());
			billDTO.setPayment(bills.getPayment());
			billDTO.setCreatedDate(bills.getCreatedDate());
			
				AddressDTO addressDTO1 = new AddressDTO();
				Address address1 = bills.getAddress();
				addressDTO1.setId(address1.getId());
				addressDTO1.setTitle(address1.getTitle());
				addressDTO1.setAddressDetail(address1.getAddressDetail());
			billDTO.setAddressDTO(addressDTO1);
				
				Promotion promotion = bills.getPromotion();
				PromotionDTO promotionDTO = new PromotionDTO();
				promotionDTO.setId(promotion.getId());
				promotionDTO.setPercent(promotion.getPercent());
			billDTO.setPromotionDTO(promotionDTO);
			
				for(BillDetail billDetail : bills.getListBillDetails()) {
					BillDetailDTO billDetailDTO = new BillDetailDTO();
					billDetailDTO.setId(billDetail.getId());
						Food food = billDetail.getFood();
						FoodDTO foodDTO = new FoodDTO();
						foodDTO.setId(food.getId());
						foodDTO.setFoodName(food.getFoodName());
						foodDTO.setPrice(food.getPrice());
					billDetailDTO.setFoodDTOs(foodDTO);
					billDetailDTO.setQuantity(billDetail.getQuantity());
					listBillDetailDTOs.add(billDetailDTO);
				}
			
			billDTO.setBillDetailDTOs(listBillDetailDTOs);
			listBillDTOs.add(billDTO);
		}
		return listBillDTOs;
	}
	@Override
	public boolean editStatusBill(int billId,int status) {
		Bills bills = billsRepository.findBillById(billId);
		
		try {
			bills.setStautus(status);
			billsRepository.save(bills);
			return true;
		} catch (Exception e) {
		return false;
		}
	}

}
