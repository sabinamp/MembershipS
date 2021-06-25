package com.sabina.member.serv.model;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShoppingListItem {
	@ApiModelProperty(notes = "Shopping List Item ID", dataType = "long",  position = 0) 
	@JsonProperty("id")
	@Id
	//@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Long id;
	
	@Column(name="name")
	@ApiModelProperty(notes = "Complete name", dataType = "varchar",  position = 1) 
	@JsonProperty("name")
	private String name;
	
	@Column(name="qty")
	@ApiModelProperty(notes = "Number of Items", dataType = "integer",  position = 2) 
	@JsonProperty("quantity")
	private Integer qty;
	
	@Column(name="price")
	@ApiModelProperty(notes = "item Price", dataType = "decimal",  position = 3) 
	@JsonProperty("price")
	private Float price;
	
	@Column(name="description")
	@ApiModelProperty(notes = "Description", dataType = "varchar",  position = 5) 
	@JsonProperty("description")
	private String description;
	
	@Column(name="status")
	@ApiModelProperty(notes = "Status", dataType = "varchar",  position = 6)
	@JsonProperty("status")
	private String status;
	
	@Column(name="approved")
	@ApiModelProperty(notes = "Delivery Date", dataType = "date", position = 7) 
	@JsonProperty("bday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy HH:mm", locale = "de")
	private LocalDateTime deliveryDate;
	
	@Column(name="type")
	@ApiModelProperty(notes = "Type", dataType = "varchar", position = 8) 
	@JsonProperty("type")
	private String type;
	
	@OneToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL, mappedBy = "item")
	@ApiModelProperty(notes = "Owner", dataType = "Location", position = 9) 
	@JsonProperty("location")
	private Location location;
}
