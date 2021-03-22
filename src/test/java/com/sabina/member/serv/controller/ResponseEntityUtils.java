package com.sabina.member.serv.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.ResultMatcher;

public class ResponseEntityUtils {
	 
	/*
	 * private static ResponseBodyUtils instance;
	 * 
	 * private ResponseBodyUtils() {
	 * 
	 * 
	 * }
	 * 
	 * public static ResponseBodyUtils instance() { if(instance == null) { instance
	 * = new ResponseBodyUtils(); } return instance; }
	 */
	 
	 static String getProperty(int indexInArray, String property, ResponseEntity<JsonArray> response) {
		    if (response.getStatusCode().equals(HttpStatus.OK)) {
		     
		      JsonArray jsonArray = response.getBody().asJsonArray();
		      assertNotNull(jsonArray);
		      if (!jsonArray.isEmpty()) {
		        return jsonArray.getJsonObject(indexInArray).getString(property);
		      }
		    }

		    return null;
	 }
	 
	 static String getObj(int indexInArray, ResponseEntity<JsonArray> response) {
		    if (response.getStatusCode().equals(HttpStatus.OK)) {
		     
		      JsonArray jsonArray = response.getBody().asJsonArray();
		      assertNotNull(jsonArray);
		      if (!jsonArray.isEmpty()) {
		        return jsonArray.getJsonObject(indexInArray).toString();
		      }
		    }

		    return null;
	 }
	 
	 static int getObjCount(ResponseEntity<JsonArray> response) {
		    if (response.getStatusCode().equals(HttpStatus.OK)) {
		     
		      JsonArray jsonArray = response.getBody().asJsonArray();
		      assertNotNull(jsonArray);
		      if (!jsonArray.isEmpty()) {
		        return jsonArray.size();
		      }
		    }

		    return 0;
	 }
	 
	 static String getJsonObjectProperty( String property, ResponseEntity<JsonObject> response) {
		    if (response.getStatusCode().equals(HttpStatus.OK)) {
		     
		      JsonObject json = response.getBody().asJsonObject();

		      if (!json.isEmpty()) {
		        return json.getString(property);
		      }
		    }

		    return null;
	 }
		
}
