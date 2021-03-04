package com.sabina.member.serv.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Data;
@Data
public class Profile {
	@NotNull
	@NotBlank
	private String name;
	
	@NotNull
	@NotBlank
	private String mobile;
	
	@NotNull
	@NotBlank
	private String address;
	
	@NotNull
	@NotBlank
	@Pattern(regexp = "[A-Za-z0-9]+@yahoo\\.com")
	private String email;
	
	@NotNull
	@NotBlank
	private String username;
	
	@NotNull
	@NotBlank
	private String password;
	
	private boolean approved;
	private LocalDateTime bday;
}
