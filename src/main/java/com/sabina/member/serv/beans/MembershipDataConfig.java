package com.sabina.member.serv.beans;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.sabina.member.serv.model.*;

@Configuration
public class MembershipDataConfig {
	@Bean
	public List<Profile> users(){
		List<Profile> userList = new ArrayList<>();
		Profile p1 = new Profile();
		p1.setName("Anna Chira");
		p1.setMobile("0033109758351");
		p1.setAddress("France");
		p1.setEmail("anna_c@yahoo.com");
		p1.setApproved(false);
		p1.setUsername("Anna");
		p1.setPassword("chira@2");
		p1.setBday(LocalDateTime.now());
		
		Profile p2 = new Profile();
		p2.setName("Julia Robby");
		p2.setMobile("0041784222111");
		p2.setAddress("Switzerland");
		p2.setEmail("julia_r@yahoo.com");
		p2.setApproved(true);
		p2.setUsername("jrobby");
		p2.setPassword("jrobby@8");
		p2.setBday(LocalDateTime.now());
		
		userList.add(p1);
		userList.add(p2);
		
		return userList;
	}
}
