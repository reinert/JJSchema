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

    public void testGenerateSchema() {
        class PojoNumber {

            @SchemaProperty(title = "number")
            public int number;

            public PojoNumber(int n) {
                this.number = n;
            }
        }
        
        /*
         {
    "description": "An Address following the convention of http://microformats.org/wiki/hcard",
    "type": "object",
    "properties": {
        "post-office-box": { "type": "string" },
        "extended-address": { "type": "string" },
        "street-address": { "type": "string" },
        "locality":{ "type": "string", "required": true },
        "region": { "type": "string", "required": true },
        "postal-code": { "type": "string" },
        "country-name": { "type": "string", "required": true}
    },
    "dependencies": {
        "post-office-box": "street-address",
        "extended-address": "street-address"
    }
}
         */
        @SchemaProperty(description="teste")
        class User {

            @SchemaProperty(required = true, title = "ID", minimum = 100000, maximun = 999999)
            private short id;
            @SchemaProperty(required = true, description = "User's name")
            private String name;
            @SchemaProperty(description = "User's sex", enums = {"M", "F"})
            private char sex;
            @SchemaProperty(description = "User's personal photo")
            @Media(type = "image/jpg", binaryEncoding = "base64")
            private Byte[] photo;

            public short getId() {
                return id;
            }

            public void setId(short id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public char getSex() {
                return sex;
            }

            public void setSex(char sex) {
                this.sex = sex;
            }

            public Byte[] getPhoto() {
                return photo;
            }

            public void setPhoto(Byte[] photo) {
                this.photo = photo;
            }
        }
        JsonSchema s = JsonSchema.generateSchema(User.class);
        
        assertTrue(
                s.getDescription().equals("teste"));
        System.out.println(s.toString());
        assertTrue(
                true);
    }
}
