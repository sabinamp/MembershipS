package com.sabina.member.serv.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sabina.member.serv.exception.SignUpException;
import com.sabina.member.serv.model.Profile;
import com.sabina.member.serv.service.SignUpService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/signup")
@Log4j2
public class SignUpController {
	
	@Autowired
	private SignUpService userService;
	
	@GetMapping( value = "/users/approved", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getApprovedUsers() {
		
		return userService.getApprovedUsers();
		
	}
	
	
	@GetMapping( value = "/users/disapproved", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getDisapprovedUsers() {
		
		return userService.getDisApprovedUsers();
	}
	
	@GetMapping( value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getSignedupUsers() {
		return userService.getSignedupUsers();
	}

	
	@GetMapping( value = "/users/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public JsonObject getTotalUsers() {
		
		return userService.getTotalUsers();
	}
	
	@GetMapping( value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getSignedupUser(@PathVariable String username) throws SignUpException {
		
		return userService.getSignedupUser(username);
	}

	@PostMapping( value = "/user/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNewSignup(@RequestBody @Valid Profile profile) {
		//users.add(profile);
		userService.addNewSignup(profile);
		return ResponseEntity.ok("added profile");
	}

	
	@GetMapping( value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public JsonArray getLoginInfo() {
		
		return userService.getLoginInfo();
	}
	
	@PostMapping( value = "/user/add/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> addNewFormSignup(@RequestParam Map<String, String> reqParams) {
		
		log.info(reqParams);
		
		userService.addNewFormSignup(reqParams);
		return ResponseEntity.ok("added profile");
	}

	@PutMapping( value = "/user/update/full/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSignup(@RequestBody Profile profile, @PathVariable String username) {

		
		userService.updateSignup(profile, username);
		return ResponseEntity.ok("updated profile");
	}

	@PatchMapping( value = "/user/update/partial/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> partialupdateSignup(@RequestBody Map<String, Object> updates, @PathVariable String username) {
	
		
		userService.partialupdateSignup(updates, username);
		return ResponseEntity.ok("updated profile");
	}
	

	@DeleteMapping( value = "/user/delete/{username}")
	public ResponseEntity<?> deleteSignup(@PathVariable String username) {
		
		userService.deleteSignup(username);
		return ResponseEntity.ok("updated profile");
	}
	
	@GetMapping( value = "/users/async", produces = MediaType.APPLICATION_JSON_VALUE)
	public CompletionStage<List<Profile>> getAsyncListProd(){
		 CompletionStage<List<Profile>> prodCS = CompletableFuture.supplyAsync( () -> {
						
			 			try {
			 				Thread.sleep(1000);
			 			} catch (InterruptedException ex) {   }
				 
			 			return userService.getSignedupUsers();
						});
		 return prodCS;
	}
	

}
