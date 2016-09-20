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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.nilportugues.jjschema.annotation.JsonSchema;
import com.github.nilportugues.jjschema.annotation.Nullable;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author reinert
 */
public class EnumTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public EnumTest(String testName) {
        super(testName);
    }

    /**
     * Test if @Nullable works at Collection Types
     *
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     *
     */
    public void testGenerateSchema() throws IOException {

        JsonNode schema = schemaFactory.createSchema(Hyperthing.class);
        Assert.assertNotNull(schema);

        JsonNode expected = MAPPER.createArrayNode().add("GET").add("POST").add("PUT").add("DELETE");
        assertEquals(expected, schema.get("properties").get("method").get("enum"));

        expected = MAPPER.createArrayNode().add(404).add(401);
        // This is a workaround because of Jackson's matching process.
        // While for JSON schema there is difference only between integer numbers and floating numbers
        // Jackson considers all Java Types as different (e.g. Long != Integer and Float != Double != BigDecimal)
        // So, for correct testing, transform the generated schema to string resource than ask to
        // Jackson's Processor to generate a JsonNode from this resource
        JsonNode generated = MAPPER.readTree(schema.get("properties").get("resultCode").get("enum").toString());
        assertEquals(expected, generated);

        expected = MAPPER.createArrayNode().add(4.04).add(4.01);
        // Same workaround as explained above
        generated = MAPPER.readTree(schema.get("properties").get("floatingResultCode").get("enum").toString());
        assertEquals(expected, generated);

        expected = MAPPER.createArrayNode().add("NOT_FOUND").add("UNAUTHORIZED").add("null");
        assertEquals(expected, schema.get("properties").get("result").get("enum"));
    }

    public enum IntegerEnum {
        NOT_FOUND(404), UNAUTHORIZED(401);
        private int numVal;

        IntegerEnum(int numVal) {
            this.numVal = numVal;
        }

        public int getNumVal() {
            return numVal;
        }

        // JJSchema uses the toString method of enum to parse the accepted values
        // If the returned value is a numeric string, then it correctly parses as a number
        @Override
        public String toString() {
            return String.valueOf(numVal);
        }
    }

    public enum FloatingEnum {
        NOT_FOUND(4.04), UNAUTHORIZED(4.01);
        private double numVal;

        FloatingEnum(double numVal) {
            this.numVal = numVal;
        }

        public double getNumVal() {
            return numVal;
        }

        // JJSchema uses the toString method of enum to parse the accepted values
        // If the returned value is a numeric string, then it correctly parses as a number
        @Override
        public String toString() {
            return String.valueOf(numVal);
        }
    }

    public enum SimpleEnum {
        // For string values, there's no need to override toString
        NOT_FOUND, UNAUTHORIZED
    }

    static class Hyperthing {

        @JsonSchema(enums = {"GET", "POST", "PUT", "DELETE"})
        private String method;
        private IntegerEnum resultCode;
        private FloatingEnum floatingResultCode;
        // Notice that JJSchema correctly adds "null" as an acceptable value in this case
        @Nullable
        private SimpleEnum result;

        public String getMethod() {
            return method;
        }

        public void setMethod(String method) {
            this.method = method;
        }

        public IntegerEnum getResultCode() {
            return resultCode;
        }

        public void setResultCode(IntegerEnum resultCode) {
            this.resultCode = resultCode;
        }

        public FloatingEnum getFloatingResultCode() {
            return floatingResultCode;
        }

        public void setFloatingResultCode(FloatingEnum floatingResultCode) {
            this.floatingResultCode = floatingResultCode;
        }

        public SimpleEnum getResult() {
            return result;
        }

        public void setResult(SimpleEnum result) {
            this.result = result;
        }
    }
}
