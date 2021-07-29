package com.sabina.member.serv.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name = "location")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long location_id;
	
	@Column(name = "name")
	@ApiModelProperty(notes = "Location", dataType = "varchar",  position = 4) 
	@JsonProperty("locationName")
	private String locationName;
	
	@Column(name = "delivery_date")
	private LocalDate deliveryDate;
	
	@Column(name = "owner")
	@ApiModelProperty(notes = "Owner", dataType = "varchar", position = 4) 
	@JsonProperty("owner")
	private String owner;
	
	@JsonProperty("item")
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "item_id", nullable = false)
	private ShoppingListItem item;
}
