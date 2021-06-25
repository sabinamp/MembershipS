package com.sabina.member.serv.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

@Entity
@Table(name="login")
@ApiModel(description = "Carries User Credentials", value = "Profile Credentials")
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class Credentials {
	public Credentials(String string, String string2) {
		this.username=string;
		this.password=string2;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	
	@Column(name="username")
	@ApiModelProperty(notes = "Username", dataType = "varchar", position = 2) 
	@JsonProperty("username")
	@NotNull(message = "validation.username.NotNull")
	@NotBlank(message = "validation.username.NotBlank")
	private String username;

	@Column(name="password")
	@ApiModelProperty(notes = "Password", dataType = "varchar", position = 3) 
	@NotNull(message = "validation.password.NotNull")
	@NotBlank(message = "validation.password.NotBlank")
	@JsonProperty("password")
	private String password;
	
	@Column(name="passphrase")
	@ApiModelProperty(notes = "passphrase", dataType = "varchar", position = 4) 
	@JsonProperty("passphrase")
	@NonNull
	private String passphrase;
	
	@OneToOne(fetch = FetchType.EAGER, optional = false)
	@JoinColumn(name = "user_id", nullable = false)
	private Profile profile;
}
