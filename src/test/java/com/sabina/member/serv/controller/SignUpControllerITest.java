package com.sabina.member.serv.controller;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.net.URI;
import java.util.List;
import javax.json.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import com.sabina.member.serv.MembershipSApplication;
import com.sabina.member.serv.beans.MembershipDataConfig;
import com.sabina.member.serv.model.Profile;

@SpringBootTest(classes = { MembershipSApplication.class, MembershipDataConfig.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SignUpControllerITest {

	private static final String LOCAL_HOST = "http://localhost:";
	
	 @LocalServerPort
	 private int randomServerPort;

	 private TestRestTemplate template = new TestRestTemplate();
	 
	 @DisplayName("Count of users")
	 @Test
	 public void count() throws Exception {
	        final String baseUrl = LOCAL_HOST + randomServerPort + "/memberservice/signup/users/count";
	        URI uri = new URI(baseUrl);
	        String response = template.getForObject(uri, String.class);
	        assertNotNull(response);
	       //JSONAssert.assertEquals(expected, response.g, false);
	        
	    }
	 
}
