package com.sabina.member.serv.repository;


import java.util.List;
import java.util.Optional;
import com.sabina.member.serv.model.Profile;

public class ProfileRepositoryImpl implements ProfileRepository<Profile,Long> {

	@Override
	public <S extends Profile> S save(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Profile> Iterable<S> saveAll(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<Profile> findById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean existsById(Long id) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterable<Profile> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Profile> findAllById(Iterable<Long> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void deleteById(Long id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Profile entity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAllById(Iterable<? extends Long> ids) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll(Iterable<? extends Profile> entities) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Profile> getApprovedUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Profile> getDisapprovedUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addProfile(Profile profile) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getUserCount() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Profile> getSignedUpUser(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteSignup(String username) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Profile> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	

}
