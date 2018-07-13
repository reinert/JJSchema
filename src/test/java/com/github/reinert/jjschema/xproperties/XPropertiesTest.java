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
 * X Properties Test
 * 
 * @author reinert
 * @author WhileTrueEndWhile
 */
public class XPropertiesTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public XPropertiesTest(String testName) {
        super(testName);
    }

    /**
     * Test the scheme generate following a scheme source, available at
     * http://json-schema.org/examples.html the output should match the example.
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

    @Attributes(title = "X Properties Example Schema")
    @XProperties({
            "fieldsets.0.title = \"One\"",
            "fieldsets.0.fields.0 = \"FirstName\"",
            "fieldsets.0.fields.1 = \"LastName\"",
            "fieldsets.1.title = \"Two\"",
            "fieldsets.1.fields.0 =  \"Age\"",
            "fieldsets.1.fields.1 =  \"EnumString\""
    })
    static class XPropertiesExample {
        @JsonProperty(value = "FirstName", required = true, defaultValue = "John")
        @Attributes(title = "First Name")
        @XProperties({ "widget.id = \"string\"" })
        private String firstName;

        @JsonProperty(value = "LastName", required = true, defaultValue = "Doe")
        @Attributes(title = "Last Name")
        @XProperties({ "widget.id = \"string\"" })
        private String lastName;

        @JsonProperty(value = "Age", required = true, defaultValue = "1")
        @Attributes(title = "Age in years", minimum = 0)
        @XProperties({ "widget.id = \"number\"", "maximum = 100" })
        private int age;

        @JsonProperty(value = "EnumString", required = true, defaultValue = "foo")
        @Attributes(title = "Enum String")
        @XProperties(files = { "/xproperties_example.properties" })
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

        public String getEnumString() {
            return this.enumString;
        }

        public void setEnumString(String enumString) {
            this.enumString = enumString;
        }
    }
}
