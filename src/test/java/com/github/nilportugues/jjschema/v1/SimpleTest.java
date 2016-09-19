/*
 * Copyright (c) 2014, Danilo Reinert (daniloreinert@growbit.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.nilportugues.jjschema.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nilportugues.jjschema.annotation.JsonSchema;
import com.github.nilportugues.jjschema.exception.UnavailableVersion;
import junit.framework.TestCase;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author reinert
 */
public class SimpleTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public SimpleTest(String testName) {
        super(testName);
    }

    /**
     * Test the scheme generate following a scheme source, avaliable at
     * http://json-schema.org/examples.html the output should match the example.
     *
     * @throws java.io.IOException
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     *
     */
    public void testGenerateSchema() throws UnavailableVersion, JsonProcessingException, IOException {

        final InputStream in = SimpleTest.class.getResourceAsStream("/simple_example.json");
        if (in == null)
            throw new IOException("resource not found");
        JsonNode fromResource = MAPPER.readTree(in);
        JsonNode fromJavaType = schemaFactory.createSchema(SimpleExample.class);
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(fromJavaType));

        assertEquals(fromResource, fromJavaType);
    }

    @JsonSchema(title = "Example Schema")
    static class SimpleExample {
        @JsonSchema(required = true)
        private String firstName;
        @JsonSchema(required = true)
        private String lastName;
        @JsonSchema(description = "Age in years", minimum = 0)
        private int age;

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }
}
