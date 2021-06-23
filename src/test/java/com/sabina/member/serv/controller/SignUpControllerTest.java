package com.sabina.member.serv.controller;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.json.JSONObject;
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
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.sabina.member.serv.beans.MembershipDataConfig;
import com.sabina.member.serv.model.Login;
import com.sabina.member.serv.model.Profile;
import com.sabina.member.serv.service.SignUpService;

@ContextConfiguration(classes= {MembershipDataConfig.class, SignUpController.class})
@WebMvcTest(SignUpController.class)
public class SignUpControllerTest {
	
	 @Autowired
	 private MockMvc mockMvc;
	 
	 @MockBean
	 private SignUpService userService;
	 
	 
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
			p1.setBday( LocalDate.of(2021, 02, 04));
			
			Profile p2 = new Profile();
			p2.setName("Julia Robby");
			p2.setMobile("0041784222111");
			p2.setAddress("Switzerland");
			p2.setEmail("julia_r@yahoo.com");
			p2.setApproved(false);
			p2.setUsername("jrobby");
			p2.setPassword("jrobby@8");
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
                 //.andExpect(content().string(containsString("Anna Chira")))
                 .andReturn();
		 assertNotNull(result.getResponse().getContentAsString());
		 //assertTrue(result.getResponse().getContentAsString().contains("Julia Robby"));
		 
	 }
	
	 
	 @DisplayName("Testing Controller-Get Signed Up User Count")
	 @Test
	 public void testGetUserCount() throws Exception{
		JsonObject countString= Json.createObjectBuilder().add("count", 3).build();
		// {"count":3}
		 when(userService.getTotalUsers()).thenReturn(countString);
		 
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/signup/users/count")
                 .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())           
                 .andReturn();
		 assertNotNull(result.getResponse().getContentAsString());
		JSONAssert.assertEquals("{\"count\":3}", result.getResponse().getContentAsString(), false);
		 
	 }
	 
	 @DisplayName("Testing Login Users")
	 @Test
	 public void testLoginUsers() throws Exception{
		 String expected = "["
				 +"{\"name\":\"Anna Chira\",\"username\":\"Anna\",\"password\":\"chira@2\"},"
				 +"{\"name\":\"Julia Robby\",\"username\":\"jrobby\",\"password\":\"jrobby@8\"},"
				 +"{\"name\":\"Kyra J\",\"username\":\"kyra\",\"password\":\"kyraj@8\"}"
				 +"]";
		 JsonArrayBuilder mockList = Json.createArrayBuilder();
		 mockList.add( "{\"name\":\"Anna Chira\",\"username\":\"Anna\",\"password\":\"chira@2\"}");
		 mockList.add( "{\"name\":\"Julia Robby\",\"username\":\"jrobby\",\"password\":\"jrobby@8\"}");
		 mockList.add("{\"name\":\"Kyra J\",\"username\":\"kyra\",\"password\":\"kyraj@8\"}");
		 when(userService.getLoginInfo()).thenReturn(mockList.build());
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/signup/users/login")
                 .accept(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk()).andExpect(content().string(containsString("Anna Chira")))
                 .andReturn();
		 assertNotNull(result.getResponse().getContentAsString());
		 
		 assertTrue(result.getResponse().getContentAsString().contains("Julia Robby"));
		 
	 }
	 
	 @DisplayName("Testing Get User by username")
	 @Test
	 public void getUserWithPathVarUsername() throws Exception{
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/signup/users/{username}","nonexisting")
				  		 .accept(MediaType.APPLICATION_JSON))
                 		 .andExpect(status().isOk()).andExpect(content().string(containsString("[]")))
                 		 .andReturn();
	 }
	 
	 @DisplayName("Testing Post User Profile")
	 @Test
	 public void postNewProfile() throws Exception{
		 Profile prof = new Profile();
		 	prof.setName("John Smith");
			prof.setMobile("0041783322111");
			prof.setAddress("Switzerland");
			prof.setEmail("john_s@yahoo.com");
			prof.setApproved(true);
			prof.setUsername("john");
			prof.setPassword("john@5");
			prof.setBday( LocalDate.of(2021, 06, 23));
		
			Jsonb jsonb = JsonbBuilder.create();
			String profileJson= jsonb.toJson(prof);			
			
		 MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/signup/user/add")
				 .content(profileJson)
				 .contentType(MediaType.APPLICATION_JSON)
				  		 .accept(MediaType.APPLICATION_JSON))
                 		 .andExpect(status().isOk()).andExpect(content().string("added profile"))                		 
                 		 .andReturn();
		 
		 assertNotNull(result.getResponse());
		 assertEquals("added profile", result.getResponse().getContentAsString());
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
