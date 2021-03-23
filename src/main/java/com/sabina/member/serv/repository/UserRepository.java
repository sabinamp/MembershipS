package com.sabina.member.serv.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sabina.member.serv.model.Profile;

@Component
public class UserRepository {
		@Autowired
		private List<Profile> users;
		
		
	public List<Profile> getUsers(){		
			return users;
	}
	

	public long getUserCount() {
		return getUsers().stream().count();
	}
	
	
	public boolean deleteSignup(String username) {
		return users.removeIf( p -> p.getUsername().equals(username));
	}
	
	public void addProfile(Profile profile) {
		users.add(profile);
	}


	public List<Profile> getApprovedUsers() {
		
		return getUsers().stream().filter(u-> u.isApproved() == true).collect(Collectors.toList());
	}


	public List<Profile> getDisapprovedUsers() {
	
		return getUsers().stream().filter(u-> u.isApproved() == false).collect(Collectors.toList());
	}


	public List<Profile> getSignedUpUser(String username) {
		
		return getUsers().stream().filter(u -> u.getUsername().contains(username)).collect(Collectors.toList());
	}
}

	