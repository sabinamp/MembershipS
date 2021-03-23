package com.sabina.member.serv.beans;

import java.lang.reflect.Type;

import javax.json.Json;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.json.bind.JsonbConfig;
import javax.json.bind.serializer.DeserializationContext;
import javax.json.bind.serializer.JsonbDeserializer;
import javax.json.bind.serializer.JsonbSerializer;
import javax.json.bind.serializer.SerializationContext;
import javax.json.stream.JsonGenerator;
import javax.json.stream.JsonParser;

import org.apache.johnzon.core.JohnzonJsonParser;
import org.apache.johnzon.jsonb.spi.JohnzonAdapterFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.JsonbHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabina.member.serv.model.Profile;


  
  @Configuration public class JsonbConfiguration<T> {
		/*
		 * 
		 * @Autowired private ObjectMapper serializingObjectMapper;
		 * 
		 * 
		 * 
		 * 
		 * 
		 * 
		 * @Bean protected HttpMessageConverter jacksonHttpMessageConverters() {
		 * MappingJackson2HttpMessageConverter messageConverter = new
		 * MappingJackson2HttpMessageConverter();
		 * messageConverter.setObjectMapper(serializingObjectMapper); return
		 * messageConverter;
		 * 
		 * }
		 */
  
  //https://developer.ibm.com/languages/java/articles/j-javaee8-json-binding-3/
  
  @Bean protected HttpMessageConverter johnzonHttpMessageConverter() {
	  JsonbConfig config = new JsonbConfig();  
  		config.withNullValues(true); 
  		config.withStrictIJSON(true);
	
	  config.withDeserializers(new JsonbDeserializer<Profile>() {
	  
	  @Override public Profile deserialize(JsonParser parser, DeserializationContext ctx,
	  Type rtType) { 
		  while (parser.hasNext()) {
	          JsonParser.Event event = parser.next();
	          if (event == JsonParser.Event.KEY_NAME) {
	              String keyName = parser.getString();
	              if (keyName.equals("id")) {
	                  return ctx.deserialize(Profile.class, parser);
	              }
	          }
	          parser.next();
	      }
	      return null;
	  
	  } }); 
	  
	  config.withSerializers(new JsonbSerializer<Profile>() {
	  
	  @Override public void serialize(Profile obj, JsonGenerator generator, SerializationContext ctx) {
		  String keyname= "name";
		  generator.write(keyname, JsonbBuilder.create().toJson(obj.getName()));
		  ctx.serialize(Profile.class, generator); }
	  
	  });
	 
	  
	  final Jsonb jsonb = JsonbBuilder.create(config);
	  
	  JsonbHttpMessageConverter messageConverter = new JsonbHttpMessageConverter();
	  messageConverter.setJsonb(jsonb); return messageConverter;
	  
	  }
  
  
  
  
  
  }
 