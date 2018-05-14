package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.fasterxml.jackson.databind.node.ObjectNode;
import junit.framework.TestCase;

public class BadPropertyTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public void testGenerateSchema() {
        ObjectNode expected = MAPPER.createObjectNode();
        expected.putObject("get").put("type", "number");

        JsonNode schema = schemaFactory.createSchema(BadClass.class);

        assertEquals(expected, schema.get("properties"));
    }

    static class BadClass {
        public double get() {
            return 9d;
        }
    }
}