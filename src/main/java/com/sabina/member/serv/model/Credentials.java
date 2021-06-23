package com.sabina.member.serv.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Credentials {
	
	
	@JsonProperty("username")
	@NonNull
	private String username;
	
	@JsonProperty("password")
	@NonNull
	private String password;
	
	@JsonProperty("passphrase")
	@NonNull
	private String passphrase;
	
}
