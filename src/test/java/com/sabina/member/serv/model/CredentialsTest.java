package com.sabina.member.serv.model;

import org.junit.jupiter.api.Test;

public class CredentialsTest {
	 @Test
	  public void testAllArgsConstructor() {
		 Credentials c = new Credentials("Anna Chira", "Anna", "chira@2");
		 assertEquals("Anna Chira", c.getName());
		 
	 }

}
