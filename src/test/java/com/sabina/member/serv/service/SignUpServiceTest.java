package com.sabina.member.serv.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.sabina.member.serv.exception.SignUpException;
import com.sabina.member.serv.model.Profile;
import com.sabina.member.serv.repository.UserRepository;


@ExtendWith(MockitoExtension.class)
public class SignUpServiceTest {
	
	 @Mock
	 private UserRepository userRepository;
	 
	 @InjectMocks
	 private SignUpServiceImpl userService;
	 
	 private List<Profile> userList= null;
	 
	 @BeforeEach
	  void initUseCase() {		
	     userList=setUpTestData();
	  }
	 
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
			p1.setBday(LocalDate.of(2021, 02, 04));
			
			Profile p2 = new Profile();
			p2.setName("Julia Robby");
			p2.setMobile("0041784222111");
			p2.setAddress("Switzerland");
			p2.setEmail("julia_r@yahoo.com");
			p2.setApproved(false);
			p2.setUsername("jrobby");
			p2.setPassword("jrobby@8");
			p2.setBday(LocalDate.of(2021, 02, 05));
			userList.add(p2);
			userList.add(p1);
			
			return userList;
	 }
	
	
	 @DisplayName("Testing SignUpService - Get Signed Up Users")
	 @Test
	 public void testGetSignedUpUsers() throws Exception{
		
		 when(userRepository.getUsers()).thenReturn(userList);
		 //BDDMockito.given(userRepository.getUsers()).willReturn(userList);
		 
		 List<Profile> testResult= userService.getSignedupUsers();
		 //BDDMockito.then(userRepository).should(times(1)).getUsers();
		 Mockito.verify(userRepository, times(1)).getUsers();
		 assertEquals(userList.size(), testResult.size());
	 }
	 
	 @DisplayName("Testing SignUpService - GetApprovedUsers")
	 @Test
	 public void testGetApprovedUsers() throws Exception{
		List<Profile> approvedUsers=userList.stream().filter(u->u.isApproved()==true).collect(Collectors.toList());
		 when(userRepository.getApprovedUsers()).thenReturn(approvedUsers);		
		 
		 List<Profile> testResult= userService.getApprovedUsers();		
		 Mockito.verify(userRepository, times(1)).getApprovedUsers();
		 assertEquals(approvedUsers.size(), testResult.size());
	 }
	 
	 @DisplayName("Testing SignUpService - GetApprovedUsers")
	 @Test
	 public void testGetDisapprovedUsers() throws Exception{
		List<Profile> disapprovedUsers=userList.stream().filter(u->u.isApproved()==false).collect(Collectors.toList());
		 when(userRepository.getDisapprovedUsers()).thenReturn(disapprovedUsers);		
		 
		 List<Profile> testResult= userService.getDisApprovedUsers();		
		 Mockito.verify(userRepository, times(1)).getDisapprovedUsers();
		 assertEquals(disapprovedUsers.size(), testResult.size());
	 }
	 
	 @DisplayName("Testing SignUpService - GetSignedUpUser-existing user")
	 @Test
	 public void testGetSignedUpUser() throws Exception{
		List<Profile> userJRobby= userList.stream().filter(u->u.getUsername().contains("jrobby")).collect(Collectors.toList());
		 
		when(userRepository.getSignedUpUser(anyString())).thenReturn(userJRobby);		
		 
		 List<Profile> testResult= userService.getSignedupUser("jrobby");	
		 
		 Mockito.verify(userRepository, times(1)).getSignedUpUser(anyString());
		 assertEquals(1, testResult.size());
		 assertThat(testResult).isNotEmpty();
		 assertEquals(userJRobby.get(0).getName(), testResult.get(0).getName());
	 }
	 
	 @DisplayName("Testing SignUpService - GetSignedUpUser-missing profile")
	 @Test
	 public void testGetSignedUpUserNotExisting() throws Exception{
		 List<Profile> user= userList.stream().filter(u->u.getUsername().contains("sabi")).collect(Collectors.toList());
		 
		 when(userRepository.getSignedUpUser(anyString())).thenReturn(user);
		 
		 Exception exception= assertThrows(SignUpException.class,()->{
			 List<Profile> testResult= userService.getSignedupUser("sabi");	
			  }); 
		 String expectedMessage = "missing resource";
		 String actualMessage = exception.getMessage();
		 Mockito.verify(userRepository, times(1)).getSignedUpUser(anyString());	  
	     assertTrue(actualMessage.contains(expectedMessage));
		 
		 
	 }
}
