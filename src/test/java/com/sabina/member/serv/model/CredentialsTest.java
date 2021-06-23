package com.sabina.member.serv.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
public class CredentialsTest {
	 @Test
	  public void testAllArgsConstructor() {
		 Credentials c = new Credentials( "Anna", "chira@2", "passphrase");
		 assertTrue("Anna".equals(c.getUsername()) );		 
	 }

	 @Test
	  public void testUsernameSetter() {
		 Credentials c = new Credentials("Anna", "chira@2","passphrase");
		 c.setUsername("Anne");
		 assertTrue("Anne".equals(c.getUsername()) );		 
	 }
}
