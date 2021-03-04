package com.sabina.member.serv.controller;

import java.util.List;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sabina.member.serv.model.Profile;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/signup")
@Log4j2
public class SignUpController {
	@Autowired
	private List<Profile> users;
	
	@GetMapping( value = "/users/approved", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getApprovedUsers() {
		return users.stream().filter(u-> u.isApproved() == true).collect(Collectors.toList());
	}
	
	@GetMapping( value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getSignedupUsers() {
		return users;
	}

	
	@GetMapping( value = "/users/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public JsonObject getTotalUsers() {
		JsonObject data = Json.createObjectBuilder()
				.add("count", users.stream().count())
				.build();
		return data;
	}
}
