package com.sabina.member.serv.service;
import java.util.List;
import java.util.Map;

import com.sabina.member.serv.exception.SignUpException;
import  com.sabina.member.serv.model.*;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import org.springframework.http.ResponseEntity;

public interface SignUpService {
	public List<Profile> getApprovedUsers();
	
	
	public List<Profile> getDisApprovedUsers();
	
	
	public List<Profile> getSignedupUsers();
	
	
	public List<Profile> getSignedupUser(String username) throws SignUpException;
	
	
	public JsonObject getTotalUsers();
		
	public void addNewSignup(Profile profile);
	
	public void addNewFormSignup(Map<String, String> reqParams);
	
	public void updateSignup(Profile profile, String username);
	
	public JsonArray getLoginInfo();
	public void partialupdateSignup(Map<String, Object> updates, String username);
	
	
	public boolean deleteSignup( String username);
	
}
