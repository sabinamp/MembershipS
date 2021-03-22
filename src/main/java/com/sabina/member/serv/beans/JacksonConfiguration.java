package com.sabina.member.serv.beans;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;


import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class JacksonConfiguration {
	@Autowired
	private ObjectMapper serializingObjectMapper;

	
	 @Bean
	 protected HttpMessageConverter jacksonHttpMessageConverters() { 
		  MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
		  messageConverter.setObjectMapper(serializingObjectMapper);
		  return messageConverter;
	  
	 }
}
