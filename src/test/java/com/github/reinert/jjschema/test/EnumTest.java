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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.reinert.jjschema.test;

import java.util.List;

import junit.framework.TestCase;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.Nullable;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.github.reinert.jjschema.SchemaProperty;

/**
 *
 * @author reinert
 */
public class EnumTest extends TestCase {

	static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().setAutoPutSchemaVersion(false).build();
	
    public EnumTest(String testName) {
        super(testName);
    }

    /**
     * Test if @Nullable works at Collection Types
     * @throws JsonProcessingException 
     */
    public void testGenerateSchema() {
        
    	JsonNode schema = v4generator.generateSchema(Hyperthing.class);
    	System.out.println(schema);
    	
    	JsonNode expected = MAPPER.createArrayNode().add("GET").add("POST").add("PUT").add("DELETE");
    	
    	assertEquals(expected, schema.get("properties").get("method").get("enum"));
    	
    }
    
    static class Hyperthing {

    	@SchemaProperty(enums={"GET", "POST", "PUT", "DELETE"})
    	private String method;

		public String getMethod() {
			return method;
		}

		public void setMethod(String method) {
			this.method = method;
		}
    	
    }
}
