package com.sabina.member.serv.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;

import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.assertj.core.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabina.member.serv.beans.MembershipDataConfig;
import com.sabina.member.serv.model.Credentials;
import com.sabina.member.serv.model.Profile;
import com.sabina.member.serv.service.SignUpService;

@ContextConfiguration(classes= {MembershipDataConfig.class, SignUpController.class})
@WebMvcTest(SignUpController.class)
public class SignUpControllerTest {
	
	 @Autowired
	 private MockMvc mockMvc;
	 
	 @MockBean
	 private SignUpService userService;
	 
	 @Autowired
	 ObjectMapper objectMapper;
	 
	 private List<Profile> setUpTestData(){
		 List<Profile> userList = new ArrayList<>();
			Profile p1 = new Profile();
			p1.setName("Anna Chira");
			p1.setMobile("0033109758351");
			p1.setAddress("France");
			p1.setEmail("anna_c@yahoo.com");
			p1.setApproved(true);
			// p1.setUsername("Anna"); 	p1.setPassword("chira@2");
			p1.setLogin(new Credentials("Anna", "chira@2"));
			p1.setBday(LocalDate.of(2021, 02, 04));
			
			Profile p2 = new Profile();
			p2.setName("Julia Robby");
			p2.setMobile("0041784222111");
			p2.setAddress("Switzerland");
			p2.setEmail("julia_r@yahoo.com");
			p2.setApproved(false);
			// p2.setUsername("jrobby"); p2.setPassword("jrobby@8");
			p2.setLogin(new Credentials("jrobby", "jrobby@8"));
			p2.setBday(LocalDate.of(2021, 02, 05));
			return userList;			
	 }
	 
	 @DisplayName("Testing Controller Get Signed Up Users")
	 @Test
	 public void testGetSignedUpUsers() throws Exception{
		 List<Profile> mockList = setUpTestData();
		 when(userService.getSignedupUsers()).thenReturn(mockList);
		 
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/signup/users")
                 .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andReturn();
		
		 assertNotNull(result.getResponse());
		
	 }
	
	 
	 @DisplayName("Testing Controller-Get Signed Up User Count")
	 @Test
	 public void testGetUserCount() throws Exception{
		String countString= Json.createObjectBuilder().add("count", 3).build().toString();
		 
		 when(userService.getTotalUsers()).thenReturn(countString);
		 
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/signup/users/count")
                 .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())           
                 .andReturn();
		 assertNotNull(result.getResponse().getContentAsString());
		 assertTrue( result.getResponse().getContentAsString().contains("count"));
		 
	 }
	 
	 @DisplayName("Testing Login Users")
	 @Test
	 public void testLoginUsers() throws Exception{
		 String expected = "["
				 +"{\"name\":\"Anna Chira\",\"username\":\"Anna\",\"password\":\"chira@2\"},"
				 +"{\"name\":\"Julia Robby\",\"username\":\"jrobby\",\"password\":\"jrobby@8\"},"
				 +"{\"name\":\"Kyra J\",\"username\":\"kyra\",\"password\":\"kyraj@8\"}"
				 +"]";
		
		 List<Credentials> mockList = new ArrayList<>();
		 mockList.add(new Credentials( "Anna", "chira@2"));
		 mockList.add(new Credentials("jrobby", "jrobby@8"));
		 mockList.add(new Credentials("kyra", "kyraj@8"));
		 when(userService.getLoginInfo()).thenReturn(mockList);
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/signup/users/login")
                 .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())
                 .andExpect(MockMvcResultMatchers.jsonPath("$[*].username").isNotEmpty())
                 .andReturn();
		 assertNotNull(result.getResponse().getContentAsString());		 
			 
	 }
	 
	 @DisplayName("Testing Get User by username")
	 @Test
	 public void getUserWithPathVarUsername() throws Exception{
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/signup/users/{username}","nonexisting")
				  		 .accept(MediaType.APPLICATION_JSON))
                 		 .andExpect(status().is4xxClientError())                 		 
                 		 .andReturn();
	 }
	 
	 @DisplayName("Testing Post User Profile")
	 @Test
	 public void postNewProfile() throws Exception{
			
		 Profile profile = new Profile();
		 profile.setName("John Smith");
		 profile.setMobile("0041783322111");
		 profile.setAddress("Switzerland");
		 profile.setEmail("john_s@yahoo.com");
		 profile.setApproved(false);
		// profile.setUsername("john");  profile.setPassword("john@5");
		 profile.setLogin(new Credentials("john","john@5"));
		 profile.setBday( LocalDate.of(2021, 03, 05)); 
		 SimpleDateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm");
		 objectMapper.setDateFormat(df);
		 String jsonString = objectMapper.valueToTree(profile).toPrettyString();
		 
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")
				 		 .content(jsonString)
				 		 .contentType(MediaType.APPLICATION_JSON)
				  		 .accept(MediaType.APPLICATION_JSON))
                 		 .andExpect(status().isOk())
                 		 .andReturn();
		 
		 assertNotNull(result.getResponse());
	 }
	 	 
	 
	 @DisplayName("Testing Post AddNewFormSignUp")
	 @Test
	 public void postNewFormSignUp() throws Exception{
		
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add/form")
				 	.param("name", "Andreas Pereto")
				 	.param("email", "an_s@yahoo.com")
				 	.param("address", "Switzerland")
				 	.param("mobile", "0041785522111")
				 	.param("username", "andreas")
					.param("password", "andreas@5")
					.param("approved", "true")
				    .contentType(MediaType.APPLICATION_FORM_URLENCODED)
				  	.accept(MediaType.APPLICATION_JSON))
                 	.andExpect(status().isOk())
                 	.andReturn();
		 
		 assertNotNull(result.getResponse());
	 }
	 
}
