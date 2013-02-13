package com.github.reinert.jjschema;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.reinert.jjschema.model.User;
import com.github.reinert.jjschema.resource.UserResource;

import junit.framework.TestCase;

public class UserResourceTest extends TestCase {
	
	ObjectWriter om = new ObjectMapper().writerWithDefaultPrettyPrinter();
	
	public void testHyperSchema() throws JsonProcessingException {
		JsonNode userSchema = SchemaFactory.v4HyperSchemaFrom(User.class);
		System.out.println(om.writeValueAsString(userSchema));
		
		System.out.println();
		
		JsonNode userHyperSchema = SchemaFactory.v4HyperSchemaFrom(UserResource.class);
		System.out.println(om.writeValueAsString(userHyperSchema));
	}

}
