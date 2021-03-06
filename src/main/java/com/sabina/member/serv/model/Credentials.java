package com.sabina.member.serv.model;

import lombok.Data;
import lombok.NonNull;

@Data
public class Credentials {
	public Credentials(String string1, String string2, String string3) {
		this.name = string1;
		this.username = string2;
		this.password = string3;
	}
	@NonNull
	private String name;
	@NonNull
	private String username;
	@NonNull
	private String password;
	
}
