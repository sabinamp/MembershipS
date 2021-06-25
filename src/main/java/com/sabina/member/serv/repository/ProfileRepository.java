package com.sabina.member.serv.repository;


import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.sabina.member.serv.model.Profile;

public interface ProfileRepository<Profile,Long> extends CrudRepository<Profile,Long> {

	List<Profile> getApprovedUsers();

	List<Profile> getDisapprovedUsers();

	void addProfile(Profile profile);

	String getUserCount();

	List<Profile> getSignedUpUser(String username);

	void deleteSignup(String username);

	List<Profile> getUsers();

}
