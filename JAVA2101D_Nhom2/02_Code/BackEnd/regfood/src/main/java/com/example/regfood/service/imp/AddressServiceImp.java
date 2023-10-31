package com.example.regfood.service.imp;

import java.util.List;

import com.example.regfood.dto.AddressDTO;
import com.example.regfood.payload.request.AddressRequest;

public interface AddressServiceImp {
	List<AddressDTO> getAllAddressByUserId(int userId);
	boolean editAdress(AddressDTO addressDTO, int id);
	boolean deleteAddress(int id);
	boolean addAdress(AddressRequest addressRequest);
	
}
