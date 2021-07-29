package com.sabina.member.serv.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.sabina.member.serv.model.Basket;

public interface BasketRepository<Basket, Long> extends CrudRepository<Basket, Long> {
	Optional<Basket> findByPurchasedBy(String user);
}
