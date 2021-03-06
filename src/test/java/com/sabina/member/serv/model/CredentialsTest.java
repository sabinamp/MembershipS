package com.sabina.member.serv.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class CredentialsTest {
	 @Test
	  public void testAllArgsConstructor() {
		 Credentials c = new Credentials("Anna Chira", "Anna", "chira@2");
		 assertTrue("Anna Chira".equals(c.getName()) );
		 
	 }

}
