package com.github.reinert.jjschema.xproperties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;

import junit.framework.TestCase;

/**
 * Right Property Test
 */
public class ValidPropertyTest extends TestCase {

    private static ObjectMapper MAPPER = new ObjectMapper();
    private JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public ValidPropertyTest(String testName) {
        super(testName);
    }

    public void testMultilineValue() throws Throwable {
        JsonNode fromResource = MAPPER.readTree(
                "{\"type\":\"object\",\"properties\":{\"wrongProperty\":{\"type\":\"string\",\"k\":{\"VALID\":\"VALID\"}}}}");
        JsonNode fromJavaType = schemaFactory.createSchema(MultilineValueExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));

        assertEquals(fromResource, fromJavaType);
    }

    // -----------------------------------------------------------------------

    public static class MultilineValueExample {
        @XProperties({ "k={\\\n\"VALID\":\\\n\"VALID\"\\\n}" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }
}
