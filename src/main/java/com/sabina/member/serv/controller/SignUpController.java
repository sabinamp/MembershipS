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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.log4j.Log4j2;

@Api(value = "Signup Controller" )
@RestController
@RequestMapping("/signup")
@Log4j2
public class SignUpController {
	
	@Autowired
	private SignUpService userService;
	
	@ApiOperation(value = "Returns all approved users only.", response = List.class)
	//@Operation(summary = "Returns all approved users only")
	@GetMapping( value = "/users/approved", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getApprovedUsers() {
		
		return userService.getApprovedUsers();
		
	}
	
	@ApiOperation(value = "Returns only disapproved users.", response = List.class)
	//@Operation(summary = "Returns all disapproved users only")
	@GetMapping( value = "/users/disapproved", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getDisapprovedUsers() {
		
		return userService.getDisApprovedUsers();
	}
	
	@ApiOperation(value = "Returns all users.", response = List.class)
	//@Operation(summary = "Returns all users. ")
	@GetMapping( value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getSignedupUsers() {
		return userService.getSignedupUsers();
	}


	@ApiOperation(value = "Returns total number of users.", response = JsonObject.class)
	//@Operation(summary = "Returns total number of users. ")
	@GetMapping( value = "/users/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public JsonObject getTotalUsers() {
		
		return userService.getTotalUsers();
	}
	
	
	@ApiOperation(value = "Returns user with matched username.", response = List.class)
	//@Operation(summary = "Returns user with matched username. ")
	@GetMapping( value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Profile> getSignedupUser(@ApiParam(value = "username") @PathVariable String username) throws SignUpException {
		
		return userService.getSignedupUser(username);
	}

	@ApiOperation(value = "Add new user.", response = String.class, consumes = "Profile data")
	//@Operation(summary = "Add new user. Consumes Profile data. ")
	@PostMapping( value = "/user/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNewSignup(@Valid @RequestBody Profile profile) {
		
		userService.addNewSignup(profile);
		return ResponseEntity.ok("added profile");
	}

	@ApiOperation(value = "Returns all user login info.", response = JsonArray.class)
	//@Operation(summary = "Returns all user login info. ")
	@GetMapping( value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public JsonArray getLoginInfo() {		
		return userService.getLoginInfo();
	}
	
	@ApiOperation(value = "Add new user.", response = String.class)
	//@Operation(summary = "Add new user.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful data entry!" ,response = String.class),
			@ApiResponse(code = 500, message = "Form data invalid!"),
			@ApiResponse(code = 404, message = "URL invalid!"),
			@ApiResponse(code = 403, message = "Request handler problem!")}
			)
	@PostMapping( value = "/user/add/form", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> addNewFormSignup(@RequestParam Map<String, String> reqParams) {
		
		log.info(reqParams);
		
		userService.addNewFormSignup(reqParams);
		return ResponseEntity.ok("added profile");
	}


	@ApiOperation(value = "Update a user account.", response = String.class)
	//@Operation(summary = "Update a user account.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated profile!" ,response = String.class),
			@ApiResponse(code = 500, message = "Update invalid!"),
			@ApiResponse(code = 404, message = "URL invalid!"),
			@ApiResponse(code = 403, message = "Request handler problem!")}
			)
	@PutMapping( value = "/user/update/full/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateSignup(@ApiParam(value = "PUT profile") @RequestBody Profile profile,@ApiParam(value = "username") @PathVariable String username) {

		userService.updateSignup(profile, username);
		return ResponseEntity.ok("updated profile");
	}
	
	@ApiOperation(value = "Update some profile detail.", response = String.class)
	//@Operation(summary = "Update some profile detail.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successful partial profile update!",response = String.class),
			@ApiResponse(code = 500, message = "Partial update invalid!"),
			@ApiResponse(code = 404, message = "URL invalid!"),
			@ApiResponse(code = 403, message = "Request handler problem!")}
			)
	@PatchMapping( value = "/user/update/partial/{username}", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> partialupdateSignup(@ApiParam(value = "Map<String,Object> patch updates") @RequestBody Map<String, Object> updates,@ApiParam(value = "username") @PathVariable String username) {
			
		userService.partialupdateSignup(updates, username);
		return ResponseEntity.ok("updated profile");
	}
	
	
	@ApiOperation(value = "Delete user account.", response = String.class)
	//@Operation(summary = "Delete user account.")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully deleted profile!" ,response = String.class),
			@ApiResponse(code = 500, message = "Delete transaction invalid!"),
			@ApiResponse(code = 404, message = "URL invalid!"),
			@ApiResponse(code = 403, message = "Request handler problem!")}
			)
	@DeleteMapping( value = "/user/{username}")
	public ResponseEntity<String> deleteSignup(@ApiParam(value = "username") @PathVariable String username) {
		boolean isDeleted = userService.deleteSignup(username);
		if(isDeleted) {
			return new ResponseEntity<>("deleted profile "+username, HttpStatus.OK);
		}
		return new ResponseEntity<>(username +" - not existing", HttpStatus.NOT_FOUND);
	}
	
	//@Operation(summary = "Returns users async.")
	@ApiOperation(value = "Returns users asynchronously.", response = CompletionStage.class)
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
