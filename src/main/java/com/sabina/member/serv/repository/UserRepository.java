package com.sabina.member.serv.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.sabina.member.serv.model.Profile;

@Component
public class UserRepository {

		public List<Profile> getUsers(){
			List<Profile> userList = new ArrayList<>();
			Profile p1 = new Profile();
			p1.setName("Anna Chira");
			p1.setMobile("0033109758351");
			p1.setAddress("France");
			p1.setEmail("anna_c@yahoo.com");
			p1.setApproved(true);
			p1.setUsername("Anna");
			p1.setPassword("chira@2");
			p1.setBday(  LocalDateTime.of(2021, 02, 04, 4, 0));
			
			Profile p2 = new Profile();
			p2.setName("Julia Robby");
			p2.setMobile("0041784222111");
			p2.setAddress("Switzerland");
			p2.setEmail("julia_r@yahoo.com");
			p2.setApproved(true);
			p2.setUsername("jrobby");
			p2.setPassword("jrobby@8");
			p2.setBday( LocalDateTime.of(2021, 02, 05, 5, 5));
			
			Profile p3 = new Profile();
			p3.setName("Kyra J");
			p3.setMobile("0041784112111");
			p3.setAddress("Switzerland");
			p3.setEmail("kyra_j@yahoo.com");
			p3.setApproved(false);
			p3.setUsername("kyra");
			p3.setPassword("kyraj@8");
			p3.setBday(LocalDateTime.of(2021, 02, 05, 05, 9));
			
			userList.add(p1);
			userList.add(p2);
			userList.add(p3);
			
			return userList;
		}
	

	public long getUserCount() {
		return getUsers().stream().count();
	}
	
	
	public void deleteSignup(String username) {
		getUsers().removeIf( p -> p.getUsername().equals(username));
	}
	
	public void addProfile(Profile profile) {
		getUsers().add(profile);
	}


	public List<Profile> getApprovedUsers() {
		
		return getUsers().stream().filter(u-> u.isApproved() == true).collect(Collectors.toList());
	}


	public List<Profile> getDisapprovedUsers() {
	
		return getUsers().stream().filter(u-> u.isApproved() == false).collect(Collectors.toList());
	}
}

	