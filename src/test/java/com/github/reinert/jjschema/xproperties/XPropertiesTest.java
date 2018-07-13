package com.github.reinert.jjschema.xproperties;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.exception.UnavailableVersion;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;

import junit.framework.TestCase;

/**
 * @author reinert
 */
public class XPropertiesTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public XPropertiesTest(String testName) {
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

        final InputStream in = XPropertiesTest.class.getResourceAsStream("/xproperties_example.json");
        if (in == null)
            throw new IOException("resource not found");
        JsonNode fromResource = MAPPER.readTree(in);
        JsonNode fromJavaType = schemaFactory.createSchema(XPropertiesExample.class);
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(fromJavaType));

        assertEquals(fromResource, fromJavaType);
    }

    public static class DoubleFactory implements XPropertyOperation {
        @Override
        public Object applyXProperty(JsonNode schema, String value) {
            return Double.valueOf(value);
        }
    }

    @Attributes(title = "Example Schema")
    @XProperties({
            "fieldsets.0.fields.0 = s:firstName",
            "fieldsets.0.fields.1 = s:lastName",
            "fieldsets.1.fields.0 = s:age"
    })
    static class XPropertiesExample {

        @Attributes(title = "First Name", required = true)
        @XProperties({
                "widget.id = s:string",
                "widget.aBooleanProp = true"
        })
        private String firstName;

        @Attributes(title = "Last Name", required = true)
        @XProperties({
                "widget.id = s:string",
                "widget.anIntegerProp = 42",
        })
        private String lastName;

        @Attributes(title = "Age in years", minimum = 0)
        @XProperties({
                "widget.id = s:number",
                "widget.aDoubleProp = java.lang.Double:3.141",
                "widget.anotherDoubleProp = com.github.reinert.jjschema.xproperties.XPropertiesTest$DoubleFactory:3.141"
        })
        private int age;

        @JsonProperty(value = "another_name", required = true, defaultValue = "John Doe")
        @Attributes(title = "Example")
        @XProperties({
                "widget.id = s:TO_BE_REMOVED",
                "widget.id = null"
        })
        private String example;

        @Attributes(title = "Enum String")
        @XProperties(files = {
                "/xproperties_example.properties"
        })
        private String enumString;

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

        public void setExample(String example) {
            this.example = example;
        }

        public String getExample() {
            return this.example;
        }

        public String getEnumString() {
            return this.enumString;
        }

        public void setEnumString(String enumString) {
            this.enumString = enumString;
        }
    }
}
