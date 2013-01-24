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
        
        JsonSchema s = JsonSchema.generateSchema(PojoNumber.class);
        System.out.println(s.toString());
        assertTrue(
                true);
    }
}
