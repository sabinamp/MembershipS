package com.sabina.member.serv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Credentials {
	/*
	 * public Credentials(String string1, String string2, String string3) {
	 * this.name = string1; this.username = string2; this.password = string3; }
	 */
	@JsonProperty("name")
	@NonNull
	private String name;
	
	@JsonProperty("username")
	@NonNull
	private String username;
	
	@JsonProperty("password")
	@NonNull
	private String password;
	
}
