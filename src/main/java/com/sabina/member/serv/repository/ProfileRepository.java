package com.sabina.member.serv.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.sabina.member.serv.model.Profile;

public interface ProfileRepository<Profile,Long> extends JpaRepository<Profile,Long> {
	@Query("select * from profile where approved=true")
	List<Profile> getApprovedUsers();
	
	@Query("select * from profile where approved=false")
	List<Profile> getDisapprovedUsers();

	@Query("select user from profile where user.username=:username")
	List<Profile> getSignedUpUser(@Param("username") String username);

	@Query("delete user from profile where user.username=:username")
	void deleteSignup(String username);
	

}
