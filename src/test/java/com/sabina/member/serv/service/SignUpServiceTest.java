package com.sabina.member.serv.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.sabina.member.serv.model.Profile;
import com.sabina.member.serv.repository.UserRepository;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {
	
	 @Mock
	 private UserRepository userRepository;
	 
	 @InjectMocks
	 private SignUpServiceImpl userService;
	 
	private List<Profile> setUpTestData(){
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
			p2.setApproved(false);
			p2.setUsername("jrobby");
			p2.setPassword("jrobby@8");
			p2.setBday( LocalDateTime.of(2021, 02, 05, 5, 5));
			
			return userList;
	 }
	
	 @DisplayName("Testing SignUpService - Get Signed Up Users")
	 @Test
	 public void testGetSignedUpUsers() throws Exception{
		 List<Profile> userList=setUpTestData();
		 when(userRepository.getUsers()).thenReturn(setUpTestData());
		 //BDDMockito.given(userRepository.getUsers()).willReturn(userList);
		 
		 List<Profile> testResult= userService.getSignedupUsers();
		 //BDDMockito.then(userRepository).should(times(1)).getUsers();
		 Mockito.verify(userRepository, times(1)).getUsers();
		 assertEquals(userList.size(), testResult.size());
	 }
}
