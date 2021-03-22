package com.sabina.member.serv.model;

import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "Carries User Data", value = "Profile Information")
@Data
public class Profile {
	@ApiModelProperty(notes = "Complete name", dataType = "varchar",  position = 0) 
	@NotNull(message = "validation.name.NotNull")
	@NotBlank(message = "validation.name.NotBlank")
	private String name;
	
	@ApiModelProperty(notes = "Mobile Number", dataType = "varchar", position = 1) 
	@NotNull(message = "validation.mobile.NotNull")
	@NotBlank(message = "{validation.mobile.NotBlank}")
	private String mobile;
	
	@ApiModelProperty(notes = "User Address", dataType = "varchar", position = 2) 
	@NotNull(message = "validation.address.NotNull")
	@NotBlank(message = "validation.name.NotBlank")
	private String address;
	
	@ApiModelProperty(notes = "Current email address", dataType = "varchar", position = 3) 
	@NotNull(message = "validation.email.NotNull")	
	@Pattern(regexp = "[A-Za-z0-9]+@yahoo\\.com")
	private String email;
	
	@ApiModelProperty(notes = "Username", dataType = "varchar", position = 4) 
	@NotNull(message = "validation.username.NotNull")
	@NotBlank(message = "validation.username.NotBlank")
	private String username;
	
	
	@ApiModelProperty(notes = "Password", dataType = "varchar", position = 5) 
	@NotNull(message = "validation.password.NotNull")
	@NotBlank(message = "validation.password.NotBlank")
	private String password;
	
	@ApiModelProperty(notes = "Admin's Approval", dataType = "true/false", position = 6) 
	private boolean approved;
	
	@ApiModelProperty(notes = "Signup Date", dataType = "date", position = 7) 
	private LocalDateTime bday;
}
