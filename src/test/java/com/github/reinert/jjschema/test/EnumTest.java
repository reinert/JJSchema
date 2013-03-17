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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import junit.framework.TestCase;

import java.io.IOException;

/**
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
     *
     * @throws JsonProcessingException
     */
    public void testGenerateSchema() throws IOException {

        JsonNode schema = v4generator.generateSchema(Hyperthing.class);
        System.out.println(schema);

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

        expected = MAPPER.createArrayNode().add("NOT_FOUND").add("UNAUTHORIZED");
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

        @Override
        public String toString() {
            return String.valueOf(numVal);
        }
    }

    public enum SimpleEnum {
        NOT_FOUND, UNAUTHORIZED
    }

    static class Hyperthing {

        @Attributes(enums = {"GET", "POST", "PUT", "DELETE"})
        private String method;
        private IntegerEnum resultCode;
        private FloatingEnum floatingResultCode;
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
