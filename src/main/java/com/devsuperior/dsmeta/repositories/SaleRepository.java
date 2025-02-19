package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.projections.SaleProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {
    @Query(value="SELECT obj FROM Sale obj " +
            "JOIN FETCH obj.seller " +
            "WHERE (obj.date BETWEEN :minDate AND :maxDate) " +
            "AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%', :name, '%'))",
            countQuery = "SELECT COUNT(obj) FROM Sale obj JOIN obj.seller")
    Page<Sale> searchSalesReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable);

    @Query("SELECT obj.seller.name AS sellerName, SUM(obj.amount) as totalAmount " +
            "FROM Sale obj " +
            "JOIN obj.seller " +
            "WHERE obj.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY obj.seller.name")
    Page<SaleProjection> searchSalesSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable);
}
