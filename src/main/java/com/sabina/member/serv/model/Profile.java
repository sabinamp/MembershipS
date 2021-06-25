package com.sabina.member.serv.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name = "profile")
@ApiModel(description = "Carries User Data", value = "Profile Information")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Profile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@Column(name="name")
	@ApiModelProperty(notes = "Complete name", dataType = "varchar",  position = 0) 
	@JsonProperty("name")
	@NotNull(message = "validation.name.NotNull")
	@NotBlank(message = "validation.name.NotBlank")
	private String name;
	
	@Column(name="mobile")
	@ApiModelProperty(notes = "Mobile Number", dataType = "varchar", position = 1) 
	@JsonProperty("mobile")
	@NotNull(message = "validation.mobile.NotNull")
	@NotBlank(message = "{validation.mobile.NotBlank}")
	private String mobile;
	
	@Column(name="address")
	@ApiModelProperty(notes = "User Address", dataType = "varchar", position = 2) 
	@JsonProperty("address")
	@NotNull(message = "validation.address.NotNull")
	@NotBlank(message = "validation.name.NotBlank")
	private String address;
	
	@Column(name="email")
	@ApiModelProperty(notes = "Current email address", dataType = "varchar", position = 3) 
	@JsonProperty("email")
	@NotNull(message = "validation.email.NotNull")	
	@Pattern(regexp = "[A-Za-z0-9]+@yahoo\\.com")
	private String email;
	
	@Column(name="approved")
	@ApiModelProperty(notes = "Admin's Approval", dataType = "true/false", position = 4)
	@JsonProperty("approved")
	private boolean approved;
	
	@Column(name="birthday", columnDefinition = "DATE")
	@ApiModelProperty(notes = "Birthday", dataType = "date", position = 5) 
	@JsonProperty("bday")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy", locale = "de")
	private LocalDate bday;
	
	@OneToOne(fetch = FetchType.EAGER, cascade =  CascadeType.ALL, mappedBy = "profile")
	private Credentials login;
}
