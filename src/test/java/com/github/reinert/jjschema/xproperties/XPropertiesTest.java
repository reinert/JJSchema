package com.github.reinert.jjschema.xproperties;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.exception.UnavailableVersion;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;
import com.github.reinert.jjschema.xproperties.annotations.XProperties;

import junit.framework.TestCase;

/**
 * X Properties Test
 */
public class XPropertiesTest extends TestCase {

    private static ObjectMapper MAPPER = new ObjectMapper();
    private JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public XPropertiesTest(String testName) {
        super(testName);
    }

    @Test()
    public void testGenerateSchema() throws UnavailableVersion, JsonProcessingException, IOException {

        final InputStream in = XPropertiesTest.class.getResourceAsStream("/xproperties_example.json");
        if (in == null)
            throw new IOException("resource not found");
        final JsonNode fromResource = MAPPER.readTree(in);
        final JsonNode fromJavaType = schemaFactory.createSchema(XPropertiesExample.class);
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    // -----------------------------------------------------------------------

    @Attributes(title = "X Properties Example Schema")
    @XProperties({
            "fieldsets.0.title = \"One\"",
            "fieldsets.0.fields.0 = \"firstName\"",
            "fieldsets.0.fields.1 = \"LastName\"",
            "fieldsets.1.title = \"Two\"",
            "fieldsets.1.fields.0 =  \"Age\"",
            "fieldsets.1.fields.1 =  \"enumString\""
    })
    static class XPropertiesExample {
        @JsonProperty(required = true, defaultValue = "John")
        @Attributes(title = "First Name")
        @XProperties({ "widget.id = \"string\"" })
        private String firstName;

        @JsonProperty(value = "LastName", required = true, defaultValue = "Doe")
        @Attributes(title = "Last Name")
        @XProperties({ "widget.id = \"string\"" })
        private String lastName;

        @JsonProperty(value = "Age", required = true, defaultValue = "1")
        @Attributes(title = "Age in years", required = true, minimum = 0)
        @XProperties({ "widget.id = \"number\"", "maximum = 100" })
        private int age;

        @JsonProperty(required = true, defaultValue = "foo")
        @Attributes(title = "Enum String", required = true)
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
