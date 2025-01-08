package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.entities.Sale;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class SaleMinDTO {

	private final Long id;
	private final Double amount;
	private final LocalDate date;
	private final String name;

	public SaleMinDTO(Builder builder) {
		this.id = builder.id;
		this.amount = builder.amount;
		this.date = builder.date;
		this.name = builder.name;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static class Builder {
		private Long id;
		private Double amount;
		private LocalDate date;
		private String name;

		public Builder id(Long id) {
			this.id = id;
			return this;
		}

		public Builder amount(Double amount) {
			this.amount = amount;
			return this;
		}

		public Builder date(LocalDate date) {
			this.date = date;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public SaleMinDTO build() {
			return new SaleMinDTO(this);
		}
	}

	public Long getId() {
		return id;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

    public String getName() {
        return name;
    }
}
