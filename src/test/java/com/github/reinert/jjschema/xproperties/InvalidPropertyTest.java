package com.github.reinert.jjschema.xproperties;

import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;
import com.github.reinert.jjschema.xproperties.annotations.XProperties;

import junit.framework.TestCase;

/**
 * Wrong Property Test
 */
public class InvalidPropertyTest extends TestCase {

    private JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public InvalidPropertyTest(String testName) {
        super(testName);
    }

    public void testEmptyProperty() throws Throwable {
        try {
            schemaFactory.createSchema(EmptyPropertyExample.class);
            fail("Empty property should throw an exception");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals(EmptyPropertyExample.class.getName() + ".wrongProperty")) {
                e.printStackTrace();
                fail("Exception #1 should notify class name and field name");
            }
            if (!e.getCause().getMessage().equals("xProperties[0]")) {
                e.getCause().printStackTrace();
                fail("Exception #2 should notify property index");
            }
            if (!e.getCause().getCause().getMessage().equals("Exactly one property must be defined")) {
                e.getCause().getCause().printStackTrace();
                fail("Exception #3 exception should notify 'Exactly one property must be defined'");
            }
        }
    }

    public void testEmptyPropertyPath() throws Throwable {
        try {
            schemaFactory.createSchema(EmptyPropertyPathExample.class);
            fail("Empty property path should throw an exception");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals(EmptyPropertyPathExample.class.getName() + ".wrongProperty")) {
                e.printStackTrace();
                fail("Exception #1 should notify class name and field name");
            }
            if (!e.getCause().getMessage().equals("xProperties[0]")) {
                e.getCause().printStackTrace();
                fail("Exception #2 should notify property index");
            }
            if (!e.getCause().getCause().getMessage().equals("First key of property path is empty")) {
                e.getCause().getCause().printStackTrace();
                fail("Exception #3 exception should notify ' First key of property path is empty'");
            }
        }
    }

    public void testMultipleProperties() throws Throwable {
        try {
            schemaFactory.createSchema(MultiplePropertiesExample.class);
            fail("Multiple properties should throw an exception");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals(MultiplePropertiesExample.class.getName() + ".wrongProperty")) {
                e.printStackTrace();
                fail("Exception #1 should notify class name and field name");
            }
            if (!e.getCause().getMessage().equals("xProperties[0]")) {
                e.getCause().printStackTrace();
                fail("Exception #2 should notify property index");
            }
            if (!e.getCause().getCause().getMessage().equals("Exactly one property must be defined")) {
                e.getCause().getCause().printStackTrace();
                fail("Exception #3 exception should notify 'Exactly one property must be defined'");
            }
        }
    }

    public void testNumericFirstKey() throws Throwable {
        try {
            schemaFactory.createSchema(NumericFirstKeyExample.class);
            fail("Numeric first property path key should throw an exception");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals(NumericFirstKeyExample.class.getName() + ".wrongProperty")) {
                e.printStackTrace();
                fail("Exception #1 should notify class name and field name");
            }
            if (!e.getCause().getMessage().equals("xProperties[0]")) {
                e.getCause().printStackTrace();
                fail("Exception #2 should notify property index");
            }
            if (!e.getCause().getCause().getMessage().equals("First key of the property path is not a string")) {
                e.getCause().getCause().printStackTrace();
                fail("Exception #3 exception should notify 'First key of the property path is not a string'");
            }
        }
    }

    public void testEmptyValue() throws Throwable {
        try {
            schemaFactory.createSchema(EmptyValueExample.class);
            fail("Empty property value should throw an exception");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals(EmptyValueExample.class.getName() + ".wrongProperty")) {
                e.printStackTrace();
                fail("Exception #1 should notify class name and field name");
            }
            if (!e.getCause().getMessage().equals("xProperties[0]")) {
                e.getCause().printStackTrace();
                fail("Exception #2 should notify property index");
            }
            if (e.getCause().getCause().getMessage().indexOf("No content to map due to end-of-input") < 0) {
                e.getCause().getCause().printStackTrace();
                fail("Exception #3 exception should notify 'No content to map due to end-of-input'");
            }
        }
    }

    public void testIllegalJson() throws Throwable {
        try {
            schemaFactory.createSchema(IllegalJsonExample.class);
            fail("Empty property value should throw an exception");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals(IllegalJsonExample.class.getName() + ".wrongProperty")) {
                e.printStackTrace();
                fail("Exception #1 should notify class name and field name");
            }
            if (!e.getCause().getMessage().equals("xProperties[0]")) {
                e.getCause().printStackTrace();
                fail("Exception #2 should notify property index");
            }
            if (e.getCause().getCause().getMessage()
                    .indexOf("Unrecognized token 'XXX': was expecting ('true', 'false' or 'null')") < 0) {
                e.getCause().getCause().printStackTrace();
                fail("Exception #3 exception should notify 'Unrecognized token 'XXX': was expecting ('true', 'false' or 'null')'");
            }
        }
    }

    public void testIllegalFile() throws Throwable {
        try {
            schemaFactory.createSchema(IllegalFileExample.class);
            fail("Empty property value should throw an exception");
        } catch (IllegalArgumentException e) {
            if (!e.getMessage().equals(IllegalFileExample.class.getName() + ".wrongProperty")) {
                e.printStackTrace();
                fail("Exception #1 should notify class name and field name");
            }
            if (e.getCause().getMessage()
                    .indexOf("Could not find resource: XXX_FAIL_FAIL_FAIL_XXX") < 0) {
                e.getCause().printStackTrace();
                fail("Exception #3 exception should notify 'Could not find resource: XXX_FAIL_FAIL_FAIL_XXX'");
            }
        }
    }

    // -----------------------------------------------------------------------

    public static class EmptyPropertyExample {
        @XProperties({ "" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }

    public static class EmptyPropertyPathExample {
        @XProperties({ "=true" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }

    public static class MultiplePropertiesExample {
        @XProperties({ "foo = 0\nbar = 1" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }

    public static class NumericFirstKeyExample {
        @XProperties({ "0 = true" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }

    public static class EmptyValueExample {
        @XProperties({ "k=" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }

    public static class IllegalJsonExample {
        @XProperties({ "k=XXX" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }

    public static class IllegalFileExample {
        @XProperties(files = { "XXX_FAIL_FAIL_FAIL_XXX" })
        private String wrongProperty;

        public String getWrongProperty() {
            return wrongProperty;
        }

        public void setWrongProperty(String wrongProperty) {
            this.wrongProperty = wrongProperty;
        }
    }
}
