package com.example.regfood.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.regfood.dto.AddressDTO;
import com.example.regfood.dto.BillDTO;
import com.example.regfood.dto.BillDetailDTO;
import com.example.regfood.dto.FoodDTO;
import com.example.regfood.dto.PromotionDTO;
import com.example.regfood.dto.RatingFoodDTO;
import com.example.regfood.dto.UserDTO;
import com.example.regfood.entities.Address;
import com.example.regfood.entities.BillDetail;
import com.example.regfood.entities.Bills;
import com.example.regfood.entities.Food;
import com.example.regfood.entities.Promotion;
import com.example.regfood.entities.RatingFood;
import com.example.regfood.entities.Users;
import com.example.regfood.payload.request.ChangePassWordRequest;
import com.example.regfood.repository.UserRepository;
import com.example.regfood.service.imp.FileServieImp.FileServiceImp;
import com.example.regfood.service.imp.UserServiceImp;
@Service
public class UserService implements UserServiceImp{
	@Autowired
	UserRepository userRepository;
	@Autowired
	FileServiceImp fileServiceImp;
	@Autowired
	PasswordEncoder passwordEncoder;
	@Override
	public UserDTO getDetailUserDTO(String email) {
		Users user = userRepository.findByEmail(email);
		UserDTO userDTO = new UserDTO();
		userDTO.setAvatar(user.getAvatar());
		userDTO.setUserName(user.getFullName());
		userDTO.setEmail(user.getEmail());
			List<AddressDTO> listAddressDTOs = new ArrayList<>();
			for(Address address : user.getListAddresses()) {
				AddressDTO addressDTO = new AddressDTO();
				addressDTO.setId(address.getId());
				addressDTO.setTitle(address.getTitle());
				addressDTO.setAddressDetail(address.getAddressDetail());
				listAddressDTOs.add(addressDTO);
			}
		userDTO.setAddressDTOs(listAddressDTOs);
		userDTO.setNumberPhone(user.getNumberPhone());
		
			List<BillDTO> listBillDTOs = new ArrayList<>();
			
			for(Bills bills : user.getListBills()) {
				
				List<BillDetailDTO> listBillDetailDTOs = new ArrayList<>();
				
				BillDTO billDTO = new BillDTO();
				billDTO.setId(bills.getId());
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
		userDTO.setBillDTOs(listBillDTOs);
			List<RatingFoodDTO> listRatingFoodDTOs = new ArrayList<>();
			for(RatingFood ratingFood : user.getListRatingFood()) {
				RatingFoodDTO ratingFoodDTO = new RatingFoodDTO();
				ratingFoodDTO.setId(ratingFood.getId());
				ratingFoodDTO.setRatingPoint(ratingFood.getRatingPoint());
				ratingFoodDTO.setContent(ratingFood.getContent());
				// Định dạng ngày theo yêu cầu
		        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd-MM-yyyy");
		        String formattedDate = sdf.format(ratingFood.getCreatedDate());
				ratingFoodDTO.setCreatedDate(formattedDate);
				listRatingFoodDTOs.add(ratingFoodDTO);
			}
		userDTO.setRatingFoodDTOs(listRatingFoodDTOs);	
		
		return userDTO;
	}
	@Override
	public boolean changeAvatar(String email, MultipartFile file) {
		Users user = userRepository.findByEmail(email);
		if(file != null) {
			boolean isSaveFileSuccess = fileServiceImp.saveFile(file);
			if(isSaveFileSuccess) {
				user.setAvatar(file.getOriginalFilename());
				
			}
		}
		try {
			userRepository.save(user);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	@Override
	public boolean changePassWord(ChangePassWordRequest changePassWordRequest) {
		Users user = userRepository.findByEmail(changePassWordRequest.getEmail());
		boolean check =  passwordEncoder.matches(changePassWordRequest.getOldPass(), user.getPassWord());
		if(check) {
			// mã hóa mật khẩu người dùng nhập thành bcrypt rồi lưu vào database
			String encodedPassword = passwordEncoder.encode(changePassWordRequest.getNewPass()); 
			user.setPassWord(encodedPassword);
			userRepository.save(user);
			return true;
		}else {
			return false;
		}
		
	}
	@Override
	public List<UserDTO> getAllCustommer() {
		List<Users> listUsers = userRepository.getAllCustommer();
		List<UserDTO> listUserDTOs = new ArrayList<>();
		for(Users user : listUsers) {
			UserDTO userDTO = new UserDTO();
			userDTO.setId(user.getId());
			userDTO.setUserName(user.getFullName());
			userDTO.setAvatar(user.getAvatar());
			userDTO.setEmail(user.getEmail());
			userDTO.setNumberPhone(user.getNumberPhone());
			listUserDTOs.add(userDTO);
		}
		return listUserDTOs;
	}

	

}
