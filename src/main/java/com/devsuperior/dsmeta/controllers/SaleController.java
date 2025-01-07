package com.devsuperior.dsmeta.controllers;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.services.SaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

@RestController
@RequestMapping(value = "/sales")
public class SaleController {

	@Autowired
	private SaleService service;
	
	@GetMapping(value = "/{id}")
	public ResponseEntity<SaleMinDTO> findById(@PathVariable Long id) {
		SaleMinDTO dto = service.findById(id);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/report")
	public ResponseEntity<Page<SaleMinDTO>> getReport(@RequestParam(name = "minDate", required = false) String startDate,
													   @RequestParam(name = "maxDate", required = false) String finishDate,
													  @RequestParam(name = "name", defaultValue = "") String name,
													   Pageable pageable) {
		LocalDate today = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());

		LocalDate minDate = (startDate == null || startDate.isEmpty()) ? today.minusYears(1L) : LocalDate.parse(startDate);
		LocalDate maxDate = (finishDate == null || finishDate.isEmpty()) ? today : LocalDate.parse(finishDate);

		Page<SaleMinDTO> dto = service.searchSalesReport(minDate, maxDate, name, pageable);
		return ResponseEntity.ok(dto);
	}

	@GetMapping(value = "/summary")
	public ResponseEntity<?> getSummary() {
		// TODO
		return null;
	}
}
