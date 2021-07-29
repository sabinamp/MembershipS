package com.sabina.member.serv.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sabina.member.serv.model.Location;

public interface LocationRepository extends CrudRepository<Location, Long> {
	Optional<Location> findByOwner(String owner);

}
