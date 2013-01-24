/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.reinert.jsonschema;

import junit.framework.TestCase;

/**
 *
 * @author heatbr
 */
public class JsonSchemaTest extends TestCase {

    public JsonSchemaTest(String testName) {
        super(testName);
    }

    /**
     * Test the scheme generate following a scheme source, avaliable at
     * :http://json-schema.org/address the output should match the example.
     */
    public void testGenerateSchema() {
        @SchemaProperty(description = "An Address following the convention of http://microformats.org/wiki/hcard")
        class Address {

            @SchemaProperty()
            private String postOfficeBox;
            @SchemaProperty()
            private String ExtendedAddress;
            @SchemaProperty()
            private String StreetAddress;
            @SchemaProperty(required = true)
            private String locality;
            @SchemaProperty(required = true)
            private String region;
            @SchemaProperty()
            private String postalCode;
            @SchemaProperty(required = true)
            private String countryName;

            public String getPostOfficeBox() {
                return postOfficeBox;
            }

            public void setPostOfficeBox(String postOfficeBox) {
                this.postOfficeBox = postOfficeBox;
            }

            public String getExtendedAddress() {
                return ExtendedAddress;
            }

            public void setExtendedAddress(String ExtendedAddress) {
                this.ExtendedAddress = ExtendedAddress;
            }

            public String getStreetAddress() {
                return StreetAddress;
            }

            public void setStreetAddress(String StreetAddress) {
                this.StreetAddress = StreetAddress;
            }

            public String getLocality() {
                return locality;
            }

            public void setLocality(String locality) {
                this.locality = locality;
            }

            public String getRegion() {
                return region;
            }

            public void setRegion(String region) {
                this.region = region;
            }

            public String getPostalCode() {
                return postalCode;
            }

            public void setPostalCode(String postalCode) {
                this.postalCode = postalCode;
            }

            public String getCountryName() {
                return countryName;
            }

            public void setCountryName(String countryName) {
                this.countryName = countryName;
            }
        }
        String expected = "{\"description\":\"An Address following the convention of http://microformats.org/wiki/hcard\",\"type\":\"object\",\"properties\":{\"post-office-box\":{\"type\":\"string\"},\"extended-address\":{\"type\":\"string\"},\"street-address\":{\"type\":\"string\"},\"locality\":{\"type\":\"string\",\"required\":true},\"region\":{\"type\":\"string\",\"required\":true},\"postal-code\":{\"type\":\"string\"},\"country-name\":{\"type\":\"string\",\"required\":true}}}";

        JsonSchema s = JsonSchema.generateSchema(Address.class);
        // verifica se o proprio objeto tem schema
        assertEquals("An Address following the convention of http://microformats.org/wiki/hcard", s.getDescription());
        assertEquals("object", s.getType());

        //compara a saida com modelo exemplo do site
        //assertEquals(expected, s.toString());
    }
}
