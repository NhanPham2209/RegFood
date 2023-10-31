package com.example.regfood.repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.regfood.entities.Bills;
@Repository
public interface BillsRepository extends JpaRepository<Bills, Integer>{
	@Query(value = "SELECT * FROM bills WHERE bill_id = ?1",nativeQuery = true)
	Bills findBillById(int id);
	
	List<Bills> findByCreatedDateBetweenAndStatus(Date start, Date end,int status);
	
//	@Query(value = "SELECT YEAR(b.created_date) as year, MONTH(b.created_date) as month, SUM(b.total_amount) as revenue " +
//		       "FROM bills b " +
//		       "WHERE b.created_date >= DATE_SUB(CURDATE(), INTERVAL 6 MONTH) " +
//		       "AND b.status = 1 " +
//		       "GROUP BY YEAR(b.created_date), MONTH(b.created_date) " +
//		       "ORDER BY year DESC, month DESC",nativeQuery = true )
//	    List<Object[]> getRevenueLastSixMonthsWithStatus2();
	@Query(value = "SELECT SUM(b.total_amount) FROM bills b " +
	           "WHERE b.created_date BETWEEN :fromDate AND :toDate " +
	           "AND b.status = :status", nativeQuery = true)
	    Object[] getTotalAmountAndDateRangeAndStatus(@Param("fromDate") Date fromDate, @Param("toDate") Date toDate, @Param("status") int status);
}
