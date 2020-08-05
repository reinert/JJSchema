/*
 * Copyright (c) 2017, Danilo Reinert (daniloreinert@growbit.com)
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

package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.reinert.jjschema.SchemaIgnoredFields;
import java.util.List;
import junit.framework.TestCase;

/**
 * @author lcastro
 */
public class SchemaIgnoredFieldsTest extends TestCase {

    JsonSchemaFactory v4generator = new JsonSchemaV4Factory();

    public SchemaIgnoredFieldsTest(String testName) {
        super(testName);
    }


    /**
     * Test that it does not work without @SchemaIgnoredFields
     */
    public void testStackOverflowWhenFieldIsNotIgnored() {
        boolean errorThrown = false;
        try {
            v4generator.createSchema(SchemaIgnoredFieldsTest.Parent.class);
        } catch (StackOverflowError e) {
            errorThrown = true;
        }
        assertTrue("Expected to thrown StackOverflowError circular dependency is not ignored", errorThrown);
    }


    /**
     * Test if @SchemaIgnoredFields works correctly
     */
    public void testGenerateSchema() {

        JsonNode schema = v4generator.createSchema(SchemaIgnoredFieldsTest.Sale.class);
        JsonNode properties = schema.get("properties");
        assertEquals(1, properties.size());

        schema = v4generator.createSchema(SchemaIgnoredFieldsTest.SaleItem.class);
        properties = schema.get("properties");
        assertEquals(2, properties.size());
    }

    @SchemaIgnoredFields(names = "saleItems")
    static class Sale {
        int id;
        List<SchemaIgnoredFieldsTest.SaleItem> saleItems;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<SchemaIgnoredFieldsTest.SaleItem> getSaleItems() {
            return saleItems;
        }

        public void setSaleItems(List<SchemaIgnoredFieldsTest.SaleItem> saleItems) {
            this.saleItems = saleItems;
        }
    }

    @SchemaIgnoredFields(names = "parent")
    static class SaleItem {
        int idSale;
        int seqNumber;

        SchemaIgnoredFieldsTest.Sale parent;

        public int getIdSale() {
            return idSale;
        }

        public void setIdSale(int idSale) {
            this.idSale = idSale;
        }

        public int getSeqNumber() {
            return seqNumber;
        }

        public void setSeqNumber(int seqNumber) {
            this.seqNumber = seqNumber;
        }

        public SchemaIgnoredFieldsTest.Sale getParent() {
            return parent;
        }

        public void setParent(SchemaIgnoredFieldsTest.Sale parent) {
            this.parent = parent;
        }
    }

    static class Parent {
        Child child;

        public Child getChild() {
            return child;
        }

        public void setChild(Child child) {
            this.child = child;
        }
    }

    static class Child {
        Parent parent;

        public Parent getParent() {
            return parent;
        }

        public void setParent(Parent parent) {
            this.parent = parent;
        }
    }

}
