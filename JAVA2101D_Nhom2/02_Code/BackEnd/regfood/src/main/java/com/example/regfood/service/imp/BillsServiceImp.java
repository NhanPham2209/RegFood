package com.example.regfood.service.imp;

import java.util.List;

import com.example.regfood.dto.BillDTO;
import com.example.regfood.payload.request.OrderRequest;

public interface BillsServiceImp {
	boolean insertOrder(OrderRequest orderRequest);
	BillDTO getBillDetailById(int billId);
	List<BillDTO> getAllBillDTOs();
	boolean editStatusBill( int billId,int status);
}
