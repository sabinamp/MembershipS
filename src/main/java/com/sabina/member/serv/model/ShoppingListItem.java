package com.sabina.member.serv.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ShoppingListItem {
	@ApiModelProperty(notes = "Shopping List Item ID", dataType = "varchar",  position = 0) 
	@JsonProperty("id")
	private String id;
	
	@ApiModelProperty(notes = "Complete name", dataType = "varchar",  position = 1) 
	@JsonProperty("name")
	private String name;
	
	@ApiModelProperty(notes = "Number of Items", dataType = "integer",  position = 2) 
	@JsonProperty("quantity")
	private Integer qty;
	
	@ApiModelProperty(notes = "item Price", dataType = "decimal",  position = 3) 
	@JsonProperty("price")
	private Float price;
	
	
	
	@ApiModelProperty(notes = "Description", dataType = "varchar",  position = 5) 
	@JsonProperty("description")
	private String description;
	
	@ApiModelProperty(notes = "Status", dataType = "varchar",  position = 6)
	@JsonProperty("status")
	private String status;
	
	@ApiModelProperty(notes = "Delivery Date", dataType = "date", position = 7) 
	@JsonProperty("bday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm", locale = "de")
	private LocalDateTime deliveryDate;
	
	@ApiModelProperty(notes = "Type", dataType = "varchar", position = 8) 
	@JsonProperty("type")
	private String type;
	
	@ApiModelProperty(notes = "Owner", dataType = "Location", position = 9) 
	@JsonProperty("location")
	private Location location;
}
