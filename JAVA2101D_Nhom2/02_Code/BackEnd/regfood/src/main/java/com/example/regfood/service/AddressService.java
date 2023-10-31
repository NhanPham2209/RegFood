package com.example.regfood.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.regfood.dto.AddressDTO;
import com.example.regfood.entities.Address;
import com.example.regfood.entities.Users;
import com.example.regfood.payload.request.AddressRequest;
import com.example.regfood.repository.AddressRepository;
import com.example.regfood.service.imp.AddressServiceImp;
@Service
public class AddressService implements AddressServiceImp {
	@Autowired 
	AddressRepository addressRepository;
	@Override
	public List<AddressDTO> getAllAddressByUserId(int userId) {
		List<AddressDTO> listAddressDTOs = new ArrayList<>();
		List<Address> listAddresses = addressRepository.findAddressByUserId(userId);
		for(Address address : listAddresses) {
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setId(address.getId());
			addressDTO.setTitle(address.getTitle());
			addressDTO.setAddressDetail(address.getAddressDetail());
			listAddressDTOs.add(addressDTO);
		}
		return listAddressDTOs;
	}

	@Override
	public boolean editAdress(AddressDTO addressDTO, int id) {
		Address address = addressRepository.findAddressById(id);
		address.setTitle(addressDTO.getTitle());
		address.setAddressDetail(addressDTO.getAddressDetail());
		try {
			addressRepository.save(address);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	@Override
	public boolean deleteAddress(int id) {
		try {
			addressRepository.deleteAddress(id);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public boolean addAdress(AddressRequest addressRequest) {
		Address address = new Address();
		Users user = new Users();
		user.setId(addressRequest.getUserId());
		address.setTitle(addressRequest.getTitle());
		address.setAddressDetail(addressRequest.getAddressDetail());
		address.setUsers(user);
		
		try {
			addressRepository.save(address);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
