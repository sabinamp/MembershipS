package com.sabina.member.serv.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Data
@AllArgsConstructor
public class Login {
	
	
	@NonNull
	private String username;
	@NonNull
	private String password;
	
	private String passphrase;
	
	private Profile profile;
}
