package com.sabina.member.serv.client;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.json.bind.JsonbBuilder;
import com.sabina.member.serv.model.Profile;

@Service
public class SignUpServiceClient {

	private RestTemplate restTemplate;
	
	public SignUpServiceClient(RestTemplateBuilder restTemplateBuilder) {
		restTemplate = restTemplateBuilder.rootUri("http://localhost:8082/memberservice").build();
	}
	
	public String doGetUserProfile(String username) {
		List<Profile> result = new ArrayList<>();
		Profile profile = new Profile();
		
		try {
			result = restTemplate.getForObject("/signup/users/"+username, List.class);			
		}catch(Exception e) {
			return JsonbBuilder.create().toJson(profile);
			
		}
		return JsonbBuilder.create().toJson(result.get(0));
		
	}
}
