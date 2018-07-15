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
import com.github.reinert.jjschema.xproperties.annotations.OneOf;

import junit.framework.TestCase;

/**
 * X Properties Test
 */
public class OneOfTest extends TestCase {

    private static ObjectMapper MAPPER = new ObjectMapper();
    private JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public OneOfTest(String testName) {
        super(testName);
    }

    @Test()
    public void testGenerateSchema() throws UnavailableVersion, JsonProcessingException, IOException {

        final InputStream in = XPropertiesTest.class.getResourceAsStream("/one_of_example.json");
        if (in == null)
            throw new IOException("resource not found");
        final JsonNode fromResource = MAPPER.readTree(in);
        final JsonNode fromJavaType = schemaFactory.createSchema(OneOfExample.class);
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(fromJavaType));
        assertEquals(fromResource, fromJavaType);
    }

    // -----------------------------------------------------------------------

    static class OneOfExample {
        @JsonProperty("OneOfExample")
        @OneOf()
        private EnumAsAttributes oneOf;

        public EnumAsAttributes getOneOf() {
            return oneOf;
        }

        public void setOneOf(EnumAsAttributes oneOf) {
            this.oneOf = oneOf;
        }
    }

    static class EnumAsAttributes {
        @JsonProperty("IFoo")
        @Attributes(title = "Foo")
        private Foo foo;

        @JsonProperty("IBar")
        @Attributes(title = "Bar")
        private Bar bar;

        public Foo getFoo() {
            return foo;
        }

        public void setFoo(Foo foo) {
            this.bar = null;
            this.foo = foo;
        }

        public Bar getBar() {
            return bar;
        }

        public void setBar(Bar bar) {
            this.foo = null;
            this.bar = bar;
        }
    }

    static class Foo {
        @JsonProperty("FooProperty")
        @Attributes(description = "Foo Property")
        private String fooProperty;

        public String getFooProperty() {
            return fooProperty;
        }

        public void setFooProperty(String fooProperty) {
            this.fooProperty = fooProperty;
        }
    }

    static class Bar {
        @JsonProperty("BarProperty")
        @Attributes(description = "Bar Propertry")
        private int barProperty;

        public int getBarProperty() {
            return barProperty;
        }

        public void setBarProperty(int barProperty) {
            this.barProperty = barProperty;
        }
    }
}
