package com.sabina.member.serv.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ShoppingListItem {

	private Integer id;
	private String name;
	private Integer qty;
	private Float price;
	private String location;
	private String description;
	private String status;
	private LocalDateTime deliveryDate;
	private String type;
	private String owner;
}
