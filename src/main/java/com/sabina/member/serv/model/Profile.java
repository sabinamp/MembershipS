package com.sabina.member.serv.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;

@Data
public class Profile {
	@NotNull(message = "validation.name.NotNull")
	@NotBlank(message = "validation.name.NotBlank")
	private String name;
	
	@NotNull(message = "validation.mobile.NotNull")
	@NotBlank(message = "{validation.mobile.NotBlank}")
	private String mobile;
	
	@NotNull(message = "validation.address.NotNull")
	@NotBlank(message = "validation.name.NotBlank")
	private String address;
	
	@NotNull(message = "validation.email.NotNull")
	@NotBlank(message = "validation.email.NotBlank")
	@Pattern(regexp = "[A-Za-z0-9]+@yahoo\\.com")
	private String email;
	
	@NotNull(message = "validation.username.NotNull")
	@NotBlank(message = "validation.username.NotBlank")
	private String username;
	
	@NotNull(message = "validation.password.NotNull")
	@NotBlank(message = "validation.password.NotBlank")
	private String password;
	
	private boolean approved;		
	private LocalDateTime bday;
}
