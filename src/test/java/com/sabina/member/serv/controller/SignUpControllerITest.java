package com.sabina.member.serv.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.json.bind.*;

import java.io.StringReader;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonString;

import org.hamcrest.core.Is;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sabina.member.serv.MembershipSApplication;
import com.sabina.member.serv.beans.MembershipDataConfig;
import com.sabina.member.serv.model.Profile;

@SpringBootTest(classes = { MembershipSApplication.class, MembershipDataConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
@AutoConfigureMockMvc
public class SignUpControllerITest {

	private static final String LOCAL_HOST = "http://localhost:";
	
	 @LocalServerPort
	 private int randomServerPort;

	 @Autowired
	 private TestRestTemplate template /*= new TestRestTemplate();*/;
	 
	 @Autowired
	 private MockMvc mockMvc;
	 
	 @DisplayName("Integration test - Get users")
	 @Test
	 public void getUsersTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users";
	        URI uri = new URI(baseUrl);
	        @SuppressWarnings("unchecked")
			List<Profile> response = template.getForObject(uri, List.class);
	        assertNotNull(response);
	       
	 }
	 
	 @DisplayName("Integration test-Get user count")
	 @Test
	 public void getUserCountTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/count";
	        URI uri = new URI(baseUrl);
	       
			JsonObject response = template.getForObject(uri, JsonObject.class);
	        assertNotNull(response);
	        assertTrue(response.containsKey("count"));
	              
	}
	 
	 @DisplayName("Integration Test-Post User Profile- Invalid Email")
	 @Test
	 public void postNewProfileWithWrongEmail() throws Exception{
		 final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/add";
	     URI uri = new URI(baseUrl);
	        
		 Profile prof = new Profile();
		 	prof.setName("Ueli Fehlmann");
			prof.setMobile("0041781122111");
			prof.setAddress("Switzerland");
			prof.setEmail("ueli_f@gmail.com");		
			prof.setApproved(false);
			prof.setUsername("ueli");
			prof.setPassword("ueli@5");
			prof.setBday( LocalDateTime.of(2021, 03, 05, 3, 5));
			Jsonb jsonb = JsonbBuilder.create();
			String profileJson= jsonb.toJson(prof);

		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")
				 .content(profileJson).contentType(MediaType.APPLICATION_JSON)
				  		 .accept(MediaType.APPLICATION_JSON))
                 		 .andExpect(status().isBadRequest())   
                 		 .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("must match \"[A-Za-z0-9]+@yahoo\\.com\"")))
                 		 .andReturn();		
		
		
	 }
	 
	 @DisplayName("Integration Test-PostUserProfile - empty profile mandatory fields")
	 @Test
	 void postNewProfile_whenBlankValue_thenReturns400() throws Exception {
		 Profile prof3 = new Profile();
		 prof3.setName("Corina Fehlmann");
		 prof3.setMobile("00417801122111");
		 prof3.setAddress("Switzerland");
		 prof3.setEmail("");
		 prof3.setPassword("");
		 prof3.setUsername("");
		 Jsonb jsonb = JsonbBuilder.create();
		 String profileJson= jsonb.toJson(prof3);
			
	   mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")	
	       .content(profileJson).contentType(MediaType.APPLICATION_JSON)
	  		 .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest())
	       .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("must match \"[A-Za-z0-9]+@yahoo\\.com\"")))
	       .andExpect(MockMvcResultMatchers.jsonPath("$.username", Is.is("validation.username.NotBlank")))
	       .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is("validation.password.NotBlank")))
	       .andReturn();
	 }
	 
	 @DisplayName("Integration Test: PostUserProfile - null profile mandatory fields")
	 @Test
	 void postNewProfile_whenNullValue_thenReturns400() throws Exception {
		 Profile prof2 = new Profile();
		 prof2.setName("Sabina Peter");
		 prof2.setMobile("0041780133111");
		 prof2.setAddress("CH");
		 prof2.setEmail(null);
		 prof2.setPassword(null);
		 prof2.setUsername(null);
		 Jsonb jsonb = JsonbBuilder.create();
		 String profileJson= jsonb.toJson(prof2);
			
	   mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")	
	       .content(profileJson).contentType(MediaType.APPLICATION_JSON)
	  		 .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest())
	       .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("validation.email.NotNull")))
	       .andExpect(MockMvcResultMatchers.jsonPath("$.username", Is.is("validation.username.NotNull")))
	       .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is("validation.password.NotNull")))
	       .andReturn();
	 }
	 
	 @DisplayName("Integration test: GetLoginInfo")
	 @Test
	 public void getLoginInfoTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/login";
	        URI uri = new URI(baseUrl);
	       
			ResponseEntity<JsonArray> response = template.getForEntity(uri, JsonArray.class);
	        assertNotNull(response);
	        
	        //[{"name":"Anna Chira","username":"Anna","password":"chira@2"},{"name":"Julia Robby","username":"jrobby","password":"jrobby@8"},{"name":"Kyra J","username":"kyra","password":"kyraj@8"}]
	      
	       JSONAssert.assertEquals("{\"name\":\"Anna Chira\",\"username\":\"Anna\",\"password\":\"chira@2\"}",
	    		   response.getBody().asJsonArray().get(0).toString(), false);
	       JSONAssert.assertEquals("{\"name\":\"Julia Robby\",\"username\":\"jrobby\",\"password\":\"jrobby@8\"}",
	    		   response.getBody().asJsonArray().get(1).toString(), false);
	       JSONAssert.assertEquals("{\"name\":\"Kyra J\",\"username\":\"kyra\",\"password\":\"kyraj@8\"}",
	    		   response.getBody().asJsonArray().get(2).toString(), false);
	}
	 
	 @DisplayName("Integration test: getApprovedUsersTest")
	 @Test
	 public void getApprovedUsersTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/approved";
	        URI uri = new URI(baseUrl);
	       
			ResponseEntity<JsonArray> response = template.getForEntity(uri, JsonArray.class);
	        assertNotNull(response);       
	       
	      
	}
	 
	 @DisplayName("Integration test: getDisapprovedUsersTest")
	 @Test
	 public void getDisapprovedUsersTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/disapproved";
	        URI uri = new URI(baseUrl);
	       
			ResponseEntity<JsonArray> response = template.getForEntity(uri, JsonArray.class);
	        assertNotNull(response);      
	       
	      
	}
}
