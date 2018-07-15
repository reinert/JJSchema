package com.github.reinert.jjschema.xproperties;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;
import com.github.reinert.jjschema.xproperties.annotations.XProperties;

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

    public void testNullValueOnClass() throws Throwable {
        JsonNode fromResource = MAPPER.readTree("{}");
        JsonNode fromJavaType = schemaFactory.createSchema(NullValueOnClassExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testNullValueOnField() throws Throwable {
        JsonNode fromResource = MAPPER.readTree(
                "{\"type\":\"object\",\"properties\":{\"wrongProperty\":{\"type\": null}}}");
        JsonNode fromJavaType = schemaFactory.createSchema(NullValueOnFieldExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testMultilineValue() throws Throwable {
        JsonNode fromResource = MAPPER.readTree(
                "{\"type\":\"object\",\"properties\":{\"wrongProperty\":{\"type\":\"string\",\"k\":{\"VALID\":\"VALID\"}}}}");
        JsonNode fromJavaType = schemaFactory.createSchema(MultilineValueExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testJsonPrimitives() throws Throwable {
        JsonNode fromResource = MAPPER.readTree(
                "{\"b\":true,\"d\":3.141,\"f\":-0.1,\"i\":-1,\"l\":42}");
        JsonNode fromJavaType = schemaFactory.createSchema(PrimitiveExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testArrayInPath() throws Throwable {
        JsonNode fromResource = MAPPER.readTree("{\"a\":[null,11,22,33]}");
        JsonNode fromJavaType = schemaFactory.createSchema(ArrayInPathExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testArrayInValue() throws Throwable {
        JsonNode fromResource = MAPPER.readTree("{\"a\":[null,11,22,33]}");
        JsonNode fromJavaType = schemaFactory.createSchema(ArrayInValueExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testObjectInPath() throws Throwable {
        JsonNode fromResource = MAPPER.readTree("{\"o\":{\"foo\":11,\"bar\":22}}");
        JsonNode fromJavaType = schemaFactory.createSchema(ObjectInPathExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testObjectInValue() throws Throwable {
        JsonNode fromResource = MAPPER.readTree("{\"o\":{\"foo\":11,\"bar\":22}}");
        JsonNode fromJavaType = schemaFactory.createSchema(ObjectInValueExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testMethod() throws Throwable {
        JsonNode fromResource = MAPPER.readTree(
                "{\"type\":\"object\",\"properties\":{\"text\":{\"type\":\"string\",\"widget\":\"string\"}}}");
        JsonNode fromJavaType = schemaFactory.createSchema(MethodExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testClassOverridesField() throws Throwable {
        JsonNode fromResource = MAPPER.readTree("{}");
        JsonNode fromJavaType = schemaFactory.createSchema(ClassOverridesFieldExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    public void testArrayOfArrays() throws Throwable {
        JsonNode fromResource = MAPPER.readTree("{\"a\":[[11,12],[21,22]]}");
        JsonNode fromJavaType = schemaFactory.createSchema(ArrayOfArraysExample.class);
        System.out.println(MAPPER.writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    // -----------------------------------------------------------------------

    @XProperties({ "type = null" })
    public static class NullValueOnClassExample {
    }

    public static class NullValueOnFieldExample {
        @XProperties({ "type = null" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }

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

    @XProperties({
            "type = null",
            "b = true",
            "d = 3.141",
            "f = -0.1",
            "i = -1",
            "l = 42"
    })
    public static class PrimitiveExample {
    }

    @XProperties({
            "type = null",
            "a.3 = 33",
            "a.2 = 22",
            "a.1 = 11"
    })
    public static class ArrayInPathExample {
    }

    @XProperties({
            "type = null",
            "a = [null, 11, 22, 33]"
    })
    public static class ArrayInValueExample {
    }

    @XProperties({
            "type = null",
            "o.foo = 11",
            "o.bar = 22"
    })
    public static class ObjectInPathExample {
    }

    @XProperties({
            "type = null",
            "o = { \"foo\": 11, \"bar\": 22 }"
    })
    public static class ObjectInValueExample {
    }

    public static interface MethodExample {
        @XProperties({ "widget = \"string\"" })
        public String getText();
    }

    @XProperties({
            "type = null",
            "properties = null"
    })
    public static class ClassOverridesFieldExample {
        @XProperties({ "k=true" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }

    @XProperties({
            "type = null",
            "a.0.0 = 11",
            "a.0.1 = 12",
            "a.1.0 = 21",
            "a.1.1 = 22"
    })
    public static class ArrayOfArraysExample {
    }
}
