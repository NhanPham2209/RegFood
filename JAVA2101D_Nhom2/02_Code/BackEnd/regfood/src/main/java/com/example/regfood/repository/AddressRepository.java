package com.example.regfood.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.Address;

import jakarta.transaction.Transactional;
@Repository
public interface AddressRepository extends JpaRepository<Address, Integer>{
	@Query(value = "SELECT * FROM address WHERE user_id = ?1",nativeQuery = true)
	List<Address> findAddressByUserId( int userId);
	
	@Query(value = "SELECT * FROM address WHERE address_id = ?1",nativeQuery = true)
	Address findAddressById(int id);
	
	@Modifying
	@Transactional
	@Query(value = "DELETE FROM Address WHERE address_id = ?1",nativeQuery = true)
	void deleteAddress(Integer id);
}
