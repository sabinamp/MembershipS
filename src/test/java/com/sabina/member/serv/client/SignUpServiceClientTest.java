package com.sabina.member.serv.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.client.MockRestServiceServer;

import javax.json.JsonObjectBuilder;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;

import com.sabina.member.serv.MembershipSApplication;
import com.sabina.member.serv.model.Profile;

@RestClientTest(SignUpServiceClient.class)
@ContextConfiguration(classes = {MembershipSApplication.class})
public class SignUpServiceClientTest {
	
	@Autowired
	private SignUpServiceClient signUpServiceClient;
	@Autowired
	private MockRestServiceServer mockRestServerService;
	private String userJsonData;
	
	 @BeforeEach
	 void initData() {		
		 List<Profile> userList=setUpTestData();
	     Jsonb jsonb = JsonbBuilder.create();
	     userJsonData = jsonb.toJson(userList);
	       
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
			userList.add(p2);
			userList.add(p1);
			
			return userList;
	 }
	
	   
    @Test
    public void testClientGetUserProfile() {
    	
    	this.mockRestServerService
        .expect(requestTo("/signup/users/jrobby"))
       // .andRespond(withSuccess(userJsonData, MediaType.APPLICATION_JSON));
        .andExpect(method(HttpMethod.GET))
        .andRespond(withStatus(HttpStatus.OK)
        .contentType(MediaType.APPLICATION_JSON)
        .body(userJsonData));
     
      
        String testResult = signUpServiceClient.doGetUserProfile("jrobby");
        assertNotNull(testResult);
    	assertTrue(testResult.contains("Julia Robby"));
    }
}
