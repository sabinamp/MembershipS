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
import com.sabina.member.serv.model.Credentials;
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
	@GetMapping( value = "/users/approved", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Profile>> getApprovedUsers() {
		List<Profile> usersapproved=userService.getApprovedUsers();
		if( usersapproved != null && !usersapproved.isEmpty()) {
			return new ResponseEntity<>(usersapproved, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@ApiOperation(value = "Returns only disapproved users.", response = List.class)
	//@Operation(summary = "Returns disapproved users only")
	@GetMapping( value = "/users/disapproved", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Profile>> getDisapprovedUsers() {
		
		List<Profile> usersdisapproved=userService.getDisApprovedUsers();
		if( usersdisapproved != null && !usersdisapproved.isEmpty()) {
			return new ResponseEntity<>(usersdisapproved, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@ApiOperation(value = "Returns all users.", response = List.class)	
	@GetMapping( value = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Profile>> getSignedupUsers() {
		List<Profile> users=userService.getSignedupUsers();
		if( users != null && !users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}


	@ApiOperation(value = "Returns total number of users.", response = String.class)
	//@Operation(summary = "Returns total number of users. ")
	@GetMapping( value = "/users/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> getTotalUsers() {
		String countString= userService.getTotalUsers();
		if( countString != null && !countString.isEmpty()) {
			return new ResponseEntity<>(countString, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
	}
	
	
	@ApiOperation(value = "Returns user with matched username.", response = List.class)
	@GetMapping( value = "/users/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Profile>> getSignedupUser(@ApiParam(value = "username") @PathVariable String username) throws SignUpException {
		List<Profile> user=userService.getSignedupUser(username);
		if(user!=null && !user.isEmpty()) {
			return new ResponseEntity<>(user, HttpStatus.OK);
		}else {
			throw new SignUpException();
			//return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}

	@ApiOperation(value = "Add new user.", response = String.class, consumes = "Profile data")
	@PostMapping( value = "/user/add", consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNewSignup(@Valid @RequestBody Profile profile) {
		
		boolean userAdded=userService.addNewSignup(profile);
		if(userAdded) {
			return new ResponseEntity<>("added profile "+profile.getName(), HttpStatus.OK);
		}
		return new ResponseEntity<>(profile.getName() +" - cannot add profile", HttpStatus.BAD_REQUEST);
		
	}

	@ApiOperation(value = "Returns all user login info.", response = List.class)
	@GetMapping( value = "/users/login", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Credentials>> getLoginInfo() {		
		List<Credentials> logindata =  userService.getLoginInfo();
		if(logindata!= null && !logindata.isEmpty()) {
			return new ResponseEntity<>(logindata , HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@ApiOperation(value = "Add new user.", response = String.class)
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
