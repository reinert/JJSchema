/*
 * Copyright (c) 2013, Danilo Reinert <daniloreinert@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.reinert.jjschema.test;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.github.reinert.jjschema.test.model.User;
import com.github.reinert.jjschema.test.rest.UserResource;

import junit.framework.TestCase;

public class UserResourceTest extends TestCase {
	
	ObjectWriter om = new ObjectMapper().writerWithDefaultPrettyPrinter();
    JsonSchemaGenerator v4hyperGenerator = SchemaGeneratorBuilder.draftV4HyperSchema().setAutoPutSchemaVersion(false).build();
	
	public void testHyperSchema() throws JsonProcessingException {
		JsonNode userSchema = v4hyperGenerator.generateSchema(User.class);
		System.out.println(om.writeValueAsString(userSchema));
		
		System.out.println();
		
		JsonNode userHyperSchema = v4hyperGenerator.generateSchema(UserResource.class);
		System.out.println(om.writeValueAsString(userHyperSchema));
	}

}
