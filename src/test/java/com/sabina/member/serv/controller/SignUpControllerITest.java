package com.sabina.member.serv.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.json.bind.*;

import java.io.StringReader;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.sabina.member.serv.model.Credentials;
import com.sabina.member.serv.model.Profile;

@SpringBootTest(classes = {
		MembershipSApplication.class, MembershipDataConfig.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
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
	        assertEquals(response.get("count").toString(), "3");
	              
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
	 
	 @DisplayName("Integration Test: PostUserProfile - null email mandatory field")
	 @Test
	 void postNewProfile_whenEmailNullValue_thenReturns400() throws Exception {
		 Profile prof4 = new Profile();
		 prof4.setName("Sabina Peter");
		 prof4.setMobile("0041780133111");
		 prof4.setAddress("CH");
		 prof4.setEmail(null);
		 prof4.setPassword("sabip@3");
		 prof4.setUsername("sabip");
		 //Jsonb jsonb = JsonbBuilder.create();
		 //String profileJson= jsonb.toJson(prof4);
		 Map<String, String> prof4Map = new HashMap<>();
		 prof4Map.put("name", "Sabina Peter");
		 prof4Map.put("mobile", "0041780133111");
		 prof4Map.put("address", "CH");
		 prof4Map.put("email", null);
		 prof4Map.put("password", "sabip@3");
		 prof4Map.put("username", "sabip");
		 String profileJson= new JSONObject(prof4Map).toString();
		 
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")	
	       .content(profileJson).contentType(MediaType.APPLICATION_JSON)
	  		.accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest())
	       .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("validation.email.NotNull")))	      
	       .andReturn();
	   
	  // String actualResponseBody = mvcResult.getResponse().getContentAsString();
	   
	 }
	 
	 @DisplayName("Integration Test: PostUserProfile - null password mandatory field")
	 @Test
	 void postNewProfile_whenPasswordNullValue_thenReturns400() throws Exception {
		 Profile prof5 = new Profile();
		 prof5.setName("Sabina Peter");
		 prof5.setMobile("0041780133111");
		 prof5.setAddress("CH");
		 prof5.setEmail("sabi@gmail.com");
		 prof5.setPassword(null);
		 prof5.setUsername("sabipeter");
		// Jsonb jsonb = JsonbBuilder.create();
		 //String profileJson= jsonb.toJson(prof5);
		 
		 Map<String, String> prof5Map = new HashMap<>();
		 prof5Map.put("name", "Sabina Peter");
		 prof5Map.put("mobile", "0041780133111");
		 prof5Map.put("address", "CH");
		 prof5Map.put("email", "sabi@gmail.com");
		 prof5Map.put("password", null);
		 prof5Map.put("username", "sabipeter");
		 String profileJson= new JSONObject(prof5Map).toString();
		
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")	
	       .content(profileJson).contentType(MediaType.APPLICATION_JSON)
	  		.accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest())     
	       .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is("validation.password.NotNull")))
	       .andReturn();	   
	 
	   
	 }
	 @DisplayName("Integration Test: PostUserProfile - null username mandatory field")
	 @Test
	 void postNewProfile_whenUsernameNullValue_thenReturns400() throws Exception {
		 Profile prof6 = new Profile();
		 prof6.setName("Sabina Peter");
		 prof6.setMobile("0041780133111");
		 prof6.setEmail("sabi@gmail.com");
		 prof6.setPassword("sabi@7");
		 prof6.setUsername(null);
		 Jsonb jsonb = JsonbBuilder.create();
		 String profileJson= jsonb.toJson(prof6);
			
	   MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")	
	       .content(profileJson).contentType(MediaType.APPLICATION_JSON)
	  		.accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest())     
	       .andExpect(MockMvcResultMatchers.jsonPath("$.username", Is.is("validation.username.NotNull")))	       
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
		    Credentials user1 = new Credentials("Anna Chira","Anna", "chira@2");
		    Credentials user2 = new Credentials("Julia Robby","jrobby", "jrobby@8");
		    Credentials user3 = new Credentials("Kyra J","kyra", "kyraj@8");
		    Jsonb jsonb = JsonbBuilder.create();
		    String user1Json= jsonb.toJson(user1);
		    String user2Json= jsonb.toJson(user2);
		    String user3Json= jsonb.toJson(user3);
		       
		   JSONAssert.assertEquals(user1Json, ResponseEntityUtils.getObj(0, response), false);
		   JSONAssert.assertEquals("{\"name\":\"Julia Robby\",\"username\":\"jrobby\",\"password\":\"jrobby@8\"}",
				   ResponseEntityUtils.getObj(1, response), false);
		   JSONAssert.assertEquals("{\"name\":\"Kyra J\",\"username\":\"kyra\",\"password\":\"kyraj@8\"}",
		    		   ResponseEntityUtils.getObj(2, response), false);
	}
	 
	 
	 @DisplayName("Integration test: getApprovedUsersTest")
	 @Test
	 public void getApprovedUsersTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/approved";
	        URI uri = new URI(baseUrl);
	       
			ResponseEntity<JsonArray> response = template.getForEntity(uri, JsonArray.class);
	        assertNotNull(response);       
	        assertEquals(2,ResponseEntityUtils.getObjCount(response));
	       
	}
	 
	 
	 @DisplayName("Integration test: getDisapprovedUsersTest")
	 @Test
	 public void getDisapprovedUsersTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/disapproved";
	        URI uri = new URI(baseUrl);
	       
			ResponseEntity<JsonArray> response = template.getForEntity(uri, JsonArray.class);
	        assertNotNull(response);      
	       
	        assertEquals(1,ResponseEntityUtils.getObjCount(response));
	}
	 
	 @DisplayName("Integration test: DeleteProfileTest")
	 @Test
	 public void deleteSignupTest() throws Exception {
		    String username="jrobby";
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/user/jrobby";
	        URI uri = new URI(baseUrl);
	       
			ResponseEntity<String> response = template.getForEntity(uri, String.class);
	        assertNotNull(response); 
	       
	        assertEquals(HttpStatus.OK,response.getStatusCode());
	        assertTrue(response.getBody().contains("deleted profile"));
	}
}
