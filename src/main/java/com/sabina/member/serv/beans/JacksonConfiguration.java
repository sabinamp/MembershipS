package com.sabina.member.serv.beans;

import java.lang.reflect.Type;

import javax.json.Json;

import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.sabina.member.serv.model.Profile;


  
  @Configuration public class JacksonConfiguration<T> {
  
  
  @Autowired 
  private ObjectMapper objectMapper;
  
  @Bean
  @Primary
  public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
      ObjectMapper objectMapper = builder.createXmlMapper(false).build();
      objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
     // objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
      return objectMapper;
  }
  
  @Bean protected HttpMessageConverter jacksonHttpMessageConverters() {
  MappingJackson2HttpMessageConverter messageConverter = new
  MappingJackson2HttpMessageConverter();
  messageConverter.setObjectMapper(objectMapper); return
  messageConverter;
  
  }
  
  
  
  
  }
 