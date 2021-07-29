package com.sabina.member.serv;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import com.sabina.member.serv.model.Credentials;
import com.sabina.member.serv.model.Profile;
import com.sabina.member.serv.repository.ProfileRepository;

@SpringBootApplication
public class MembershipSApplication {

	public static void main(String[] args) {
		SpringApplication.run(MembershipSApplication.class, args);
	}

	/*
	 * @Bean ApplicationRunner init(ProfileRepository<Profile, Long> repository) {
	 * List<Profile> userList = new ArrayList<>(); Profile p1 = new Profile();
	 * p1.setName("Anna Chira"); p1.setMobile("0033109758351");
	 * p1.setAddress("France"); p1.setEmail("anna_c@yahoo.com"); p1.setLogin(new
	 * Credentials("Anna", "anna@8")); p1.setApproved(true);
	 * p1.setBday(LocalDate.of(2021, 02, 04));
	 * 
	 * Profile p2 = new Profile(); p2.setName("Julia Robby");
	 * p2.setMobile("0041784222111"); p2.setAddress("Switzerland");
	 * p2.setEmail("julia_r@yahoo.com"); p2.setApproved(true); p2.setLogin(new
	 * Credentials("jrobby","jrobby@8")); p2.setBday(LocalDate.of(2021, 02, 05));
	 * 
	 * Profile p3 = new Profile(); p3.setName("Kyra J");
	 * p3.setMobile("0041784112111"); p3.setAddress("Switzerland");
	 * p3.setEmail("kyra_j@yahoo.com"); p3.setApproved(false);
	 * 
	 * p3.setLogin(new Credentials("kyra","kyraj@8")); p3.setBday(LocalDate.of(2021,
	 * 02, 05));
	 * 
	 * userList.add(p1); userList.add(p2); userList.add(p3);
	 * 
	 * 
	 * return args->{ userList.forEach(prof -> { repository.save(prof); });
	 * repository.findAll().forEach(System.out::println); };
	 * 
	 * }
	 */
}
