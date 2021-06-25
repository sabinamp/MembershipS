package com.sabina.member.serv.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Basket {
	@JsonProperty("id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@JsonProperty("purchaseDate")
	@Column(name="purchased_date", columnDefinition="DATE")
	private LocalDate purchaseDate;
	
	@JsonProperty("deliveryDate")
	@Column(name="delivery_date", columnDefinition="DATE")
	private LocalDate deliveryDate;
	
	@JsonProperty("purchasedBy")
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name="user_id",nullable = false)
	private Profile purchasedBy;
	
	@JsonProperty("total")
	@Column(name="total")
	private BigDecimal total;
	
}
