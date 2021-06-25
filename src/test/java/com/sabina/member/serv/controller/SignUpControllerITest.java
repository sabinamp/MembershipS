package com.sabina.member.serv.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.io.StringReader;
import java.net.URI;
import java.time.LocalDate;
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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
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

     private static RestTemplate template = new RestTemplate();
	 
	 private static MockRestServiceServer mockServer;
	 
	 @BeforeAll
	 public static void init() {
	        mockServer = MockRestServiceServer.createServer(template);
	 }
	 
	 @Autowired
	 private MockMvc mockMvc;
	 
	 @Autowired
	 private ObjectMapper objectMapper;
	 
	 @DisplayName("Integration test - Get users")
	 @Test
	 public void getUsersTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users";
	        URI uri = new URI(baseUrl);
	    	
			  mockServer.expect(ExpectedCount.once(), requestTo(uri))
			  .andExpect(method(HttpMethod.GET)) 
			  .andRespond(withStatus(HttpStatus.OK)
			  .contentType(MediaType.APPLICATION_JSON) );
			  
			  List<Profile> response = template.getForObject(uri, List.class);
			  mockServer.verify();
	       
	 }
	 
	 @DisplayName("Integration test-Get user count")
	 @Test
	 public void getUserCountTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/count";
	        URI uri = new URI(baseUrl);
	        mockServer.expect(ExpectedCount.once(), requestTo(uri))
			  .andExpect(method(HttpMethod.GET)) .andRespond(withStatus(HttpStatus.OK)
			  .contentType(MediaType.APPLICATION_JSON).body("{\"count\": \"3\"}") );
			  
	        ResponseEntity<String> response = template.getForEntity(uri, String.class);
	        assertNotNull(response);	       
	        assertTrue(response.getBody().contains("count"));
	        assertTrue(response.getBody().contains("3")); 
			mockServer.verify();		 
			           
	              
	}
	 
	 @DisplayName("Integration Test-Post User Profile- Invalid Email")
	 @Test
	 public void postNewProfileWithWrongEmail() throws Exception{
		 final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/add";
	     URI uri = new URI(baseUrl);
	        			
			  Profile prof = new Profile(); prof.setName("Ueli Fehlmann");
			  prof.setMobile("0041781122111"); prof.setAddress("Switzerland");
			  prof.setEmail("ueli_f@gmail.com"); prof.setApproved(false);
			 // prof.setUsername("ueli"); prof.setPassword("ueli@5"); 
			  prof.setLogin(new Credentials("ueli","ueli@5"));
			  prof.setBday(LocalDate.of(2021, 03, 05)); 
			  String profile1 = objectMapper.valueToTree(prof).toPrettyString();
			 
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")
				 .content(profile1).contentType(MediaType.APPLICATION_JSON)
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
			//  prof3.setPassword("");   prof3.setUsername(""); 
			prof3.setLogin(new Credentials("",""));
		 
	     String profile3 =  objectMapper.writeValueAsString(prof3);
	   mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")	
	       .content(profile3).contentType(MediaType.APPLICATION_JSON)
	  		 .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest())
	       .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("must match \"[A-Za-z0-9]+@yahoo\\.com\"")))
	       .andExpect(MockMvcResultMatchers.jsonPath("$.login.username", Is.is("validation.username.NotBlank")))
	       .andExpect(MockMvcResultMatchers.jsonPath("$.login.password", Is.is("validation.password.NotBlank")))
	       .andReturn();
	 }
	 
	 @DisplayName("Integration Test: PostUserProfile - null email mandatory field")
	 @Test
	 void postNewProfile_whenEmailNullValue_thenReturns400() throws Exception {
			
			  Profile prof4 = new Profile(); prof4.setName("Sabina Peter");
			  prof4.setMobile("0041780133111"); prof4.setAddress("CH");
			  prof4.setEmail(null); 
			 // prof4.setPassword("sabip@3");  prof4.setUsername("sabip");
			 prof4.setLogin(new Credentials("sabip","sabip@3"));
		
		
	     String profile4 =  objectMapper.writeValueAsString(prof4);
	     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")	
	       .content(profile4).contentType(MediaType.APPLICATION_JSON)
	  		.accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest())
	       .andExpect(MockMvcResultMatchers.jsonPath("$.email", Is.is("validation.email.NotNull")))	      
	       .andReturn();
	   
	 
	   
	 }
	 
	 @DisplayName("Integration Test: PostUserProfile - null password mandatory field")
	 @Test
	 void postNewProfile_whenPasswordNullValue_thenReturns400() throws Exception {
		
			/*
			 * JsonObject prof5Json = Json.createObjectBuilder() .add("name","Sabina Peter")
			 * .add("mobile", "0041780133111") .add("address", "CH") .add("email",
			 * "sabi@gmail.com") .add("password", "") .add("username", "sabipeter")
			 * .build();
			 */
		 Profile prof5= new Profile();
		 prof5.setAddress("CH");
		 prof5.setApproved(false);
		 prof5.setName("Sabina Peter");
		 prof5.setMobile("0041780133111");
		 prof5.setEmail("sabi@gmail.com");
		 //prof5.setPassword(null); prof5.setUsername("sabipeter");
		 prof5.setLogin(new Credentials(null, "sabipeter"));
	     String prof5Json =  objectMapper.writeValueAsString(prof5);
	     MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")	
	       .content(prof5Json).contentType(MediaType.APPLICATION_JSON)
	  	   .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest())     
	       .andExpect(MockMvcResultMatchers.jsonPath("$.password", Is.is("validation.password.NotNull")))
	       .andReturn();	  
	 
	 }
	 
	 @DisplayName("Integration Test: PostUserProfile - null username mandatory field")
	 @Test
	 void postNewProfile_whenUsernameNullValue_thenReturns400() throws Exception {
		 Profile prof6 = new Profile();
		 prof6.setName("X YY");
		 prof6.setMobile("0041780133111");
		 prof6.setEmail("sabi@gmail.com");
		 //prof6.setPassword("sabi@7");
		 // prof6.setUsername(null);
		 Credentials c= new Credentials(null, "sabi@7");
		prof6.setLogin(c);
		 
		
	     String prof6Json =  objectMapper.writeValueAsString(prof6);
			
	    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")	
	       .content(prof6Json).contentType(MediaType.APPLICATION_JSON)
	  	   .accept(MediaType.APPLICATION_JSON))
	       .andExpect(status().isBadRequest())     
	       .andExpect(MockMvcResultMatchers.jsonPath("$.username", Is.is("validation.username.NotBlank")))	       
	       .andReturn();	   
	 
	   
	 }
	 
	 @DisplayName("Integration test: GetLoginInfo")
	 @Test
	 public void getLoginInfoTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/login";
	        URI uri = new URI(baseUrl);
	       
			ResponseEntity<List> response = template.getForEntity(uri, List.class);
	        assertNotNull(response);
	        
	        //[{"name":"Anna Chira","username":"Anna","password":"chira@2"},{"name":"Julia Robby","username":"jrobby","password":"jrobby@8"},{"name":"Kyra J","username":"kyra","password":"kyraj@8"}]
		    Credentials user1 = new Credentials("Anna","chira@2");
		    Credentials user2 = new Credentials("jrobby","jrobby@8");
		    Credentials user3 = new Credentials("kyra","kyraj@8");
		  
		   String user1Json = objectMapper.valueToTree(user1).toPrettyString();
		   String user2Json = objectMapper.valueToTree(user2).toPrettyString();
		   String user3Json = objectMapper.valueToTree(user3).toPrettyString();
		   
		
	}
	 
	 
	 @DisplayName("Integration test: getApprovedUsersTest")
	 @Test
	 public void getApprovedUsersTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/approved";
	        URI uri = new URI(baseUrl);
	       
			ResponseEntity<List> response = template.getForEntity(uri, List.class);
	        assertNotNull(response);       
	        assertEquals(2, response.getBody().size());
	       
	}
	 
	 
	 @DisplayName("Integration test: getDisapprovedUsersTest")
	 @Test
	 public void getDisapprovedUsersTest() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/disapproved";
	        URI uri = new URI(baseUrl);
	       
			ResponseEntity<List> response = template.getForEntity(uri, List.class);
	        assertNotNull(response);      
	       
	        assertEquals(1,response.getBody().size());
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
