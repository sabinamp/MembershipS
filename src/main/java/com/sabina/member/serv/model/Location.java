package com.sabina.member.serv.model;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Location {
	private Long location_id;
	
	@ApiModelProperty(notes = "Location", dataType = "varchar",  position = 4) 
	@JsonProperty("locationName")
	private String locationName;
	
	private LocalDate deliveryDate;
	
	@ApiModelProperty(notes = "Owner", dataType = "varchar", position = 4) 
	@JsonProperty("owner")
	private String owner;
	
	private ShoppingListItem item;
}
