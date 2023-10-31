package com.example.regfood.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.regfood.payload.ResponseData;
import com.example.regfood.payload.request.OrderRequest;
import com.example.regfood.service.imp.BillsServiceImp;
@CrossOrigin("*")
@RestController
@RequestMapping("/order")
public class OrderController {
	@Autowired 
	BillsServiceImp billsServiceImp;
	
	@PostMapping("")
	public ResponseEntity<?> order(@RequestBody OrderRequest orderRequest){
		ResponseData responseData = new ResponseData();
		responseData.setData(billsServiceImp.insertOrder(orderRequest));
		return new ResponseEntity<>(responseData,HttpStatus.OK) ;
		
	}
	
	 @GetMapping("/order-detail")
	 public ResponseEntity<?> findBillById(@RequestParam int id){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(billsServiceImp.getBillDetailById(id));
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
	 @GetMapping("/get-all")
	 public ResponseEntity<?> getAllOrder(){
		 ResponseData responseData  = new ResponseData();
		 responseData.setData(billsServiceImp.getAllBillDTOs());
		 
		  return new ResponseEntity<>(responseData,HttpStatus.OK);
		 
	 }
		@PostMapping("/edit-status")
		public ResponseEntity<?> editStatusBill(@RequestParam int billId, @RequestParam int status){
			ResponseData responseData = new ResponseData();
			responseData.setData(billsServiceImp.editStatusBill(billId, status));
			return new ResponseEntity<>(responseData,HttpStatus.OK) ;
			
		}
}
