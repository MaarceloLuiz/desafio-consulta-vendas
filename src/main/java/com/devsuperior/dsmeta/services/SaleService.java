package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.Optional;

import com.devsuperior.dsmeta.projections.SaleProjection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return createSaleMinDTO(entity);
	}

	public Page<SaleMinDTO> searchSalesReport(LocalDate minDate, LocalDate maxDate, String name, Pageable pageable){
		Page<Sale> entity = repository.searchSalesReport(minDate, maxDate, name, pageable);
		return entity.map(this::createSaleMinDTO);
	}

	public Page<SaleMinDTO> searchSalesSummary(LocalDate minDate, LocalDate maxDate, Pageable pageable){
		Page<SaleProjection> entity = repository.searchSalesSummary(minDate, maxDate, pageable);
		return entity.map(e -> SaleMinDTO.builder()
										.name(e.getSellerName())
										.amount(e.getTotalAmount())
										.build());
	}

	private SaleMinDTO createSaleMinDTO(Sale entity){
		return SaleMinDTO.builder()
				.id(entity.getId())
				.amount(entity.getAmount())
				.date(entity.getDate())
				.name(entity.getSeller().getName())
				.build();
	}
}
