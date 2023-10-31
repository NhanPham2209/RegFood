package com.example.regfood.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.BillDetail;

@Repository
public interface BillDetailRepository extends JpaRepository<BillDetail, Integer>{

}
