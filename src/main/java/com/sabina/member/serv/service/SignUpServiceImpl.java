package com.sabina.member.serv.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sabina.member.serv.exception.SignUpException;
import com.sabina.member.serv.model.Profile;
import com.sabina.member.serv.repository.UserRepository;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

@Service
public class SignUpServiceImpl implements SignUpService {
	
	
	private final UserRepository userRepository;
	
	
	public SignUpServiceImpl( UserRepository userRepository) {
		this.userRepository = userRepository;
		
	}
	
	@Override
	public List<Profile> getApprovedUsers() {		
		return userRepository.getApprovedUsers();
	}

	@Override
	public List<Profile> getDisApprovedUsers() {
		return userRepository.getDisapprovedUsers();
	}

	@Override
	public List<Profile> getSignedupUsers() {		
		return userRepository.getUsers();
	}

	@Override
	public List<Profile> getSignedupUser(String username) throws SignUpException {
		List<Profile> filteredUsers = userRepository.getSignedUpUser(username);
		if(filteredUsers.size() == 0 || filteredUsers == null) {
			throw new SignUpException("missing resource");
		}
		return filteredUsers;
	}

	@Override
	public void addNewSignup(Profile profile) {
		userRepository.addProfile(profile);		
	}

	@Override
	public JsonObject getTotalUsers() {
		JsonObject data = Json.createObjectBuilder()
				.add("count", userRepository.getUserCount())
				.build();
		return data;
	}

	@Override
	public void updateSignup(Profile profile, String username) {

		Optional.of(profile).ifPresent(prf -> { 
			userRepository.deleteSignup(username);
			userRepository.addProfile(profile);
		});
	}

	@Override
	public void partialupdateSignup(Map<String, Object> updates, String username) {
		Profile profile = userRepository.getUsers().stream().filter(u -> u.getUsername().equals(username)).findAny().orElse(null);
		Optional.ofNullable(updates.get("username")).ifPresent( u -> {
			String user = (String) u;
			profile.setUsername(user);
		});
		
		Optional.ofNullable(updates.get("password")).ifPresent( p -> {
			String pass = (String) p;
			profile.setPassword(pass);
		});
		Optional.ofNullable(updates.get("name")).ifPresent( n -> {
			String name = (String) n;
			profile.setName(name);
		});
		Optional.ofNullable(updates.get("mobile")).ifPresent( m -> {
			String mobile = (String) m;
			profile.setMobile(mobile);
		});
		Optional.ofNullable(updates.get("email")).ifPresent( e -> {
			String email = (String) e;
			profile.setEmail(email);
		});
		Optional.ofNullable(updates.get("address")).ifPresent( a -> {
			String address = (String) a;
			profile.setAddress(address);
		});
		Optional.ofNullable(updates.get("approved")).ifPresent( a -> {
			boolean approved = (Boolean) a;
			profile.setApproved(approved);
		});
	}

	@Override
	public void deleteSignup(String username) {
		userRepository.deleteSignup(username);
	}

	
	@Override
	public void addNewFormSignup(Map<String, String> reqParams) {
		Profile profile = new Profile();		
		profile.setName(reqParams.getOrDefault("name", ""));
		profile.setEmail(reqParams.getOrDefault("email", ""));
		profile.setAddress(reqParams.getOrDefault("address", ""));
		profile.setMobile(reqParams.getOrDefault("mobile", ""));
		profile.setUsername(reqParams.getOrDefault("username", ""));
		profile.setPassword(reqParams.getOrDefault("password", ""));
		profile.setApproved(Boolean.parseBoolean(reqParams.getOrDefault("approved", "false")));
		userRepository.addProfile(profile);
	}


	@Override
	public JsonArray getLoginInfo() {
		JsonArray loginData = Json.createArrayBuilder().build(); 
		JsonArrayBuilder jsonDataBuilder = Json.createArrayBuilder(loginData);
		for(Profile rec : userRepository.getUsers()) {
			JsonObject jsonRow = Json.createObjectBuilder()
					.add("name", rec.getName())
					.add("username", rec.getUsername())
					.add("password", rec.getPassword())
					.build();
			jsonDataBuilder.add(jsonRow);
		}
		loginData = jsonDataBuilder.build();
		return loginData;
	}

}
