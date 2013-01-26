package org.reinert.jsonschema;

import org.reinert.jsonschema.model.User;
import org.reinert.jsonschema.resource.UserResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import junit.framework.TestCase;

public class UserResourceTest extends TestCase {
	
	ObjectMapper om = new ObjectMapper();
	
	public void testHyperSchema() throws JsonProcessingException {
		HyperSchemaV4 userSchema = HyperSchemaV4.generateHyperSchema(User.class);
		System.out.println(om.writeValueAsString(userSchema));
		
		HyperSchemaV4 userHyperSchema = HyperSchemaV4.generateHyperSchema(UserResource.class);
		System.out.println(om.writeValueAsString(userHyperSchema));
	}

}
