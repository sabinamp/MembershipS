package com.sabina.member.serv.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sabina.member.serv.model.Credentials;

public interface CredentialsRepository extends CrudRepository<Credentials, Long> {
	Optional<Credentials> findByUsername(String username);
	Optional<Credentials> findById(Long id) ;

}
