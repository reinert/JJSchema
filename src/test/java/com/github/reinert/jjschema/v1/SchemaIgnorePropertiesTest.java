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

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.SchemaIgnoreProperties;

import junit.framework.TestCase;

/**
 * @author reinert
 */
public class SchemaIgnorePropertiesTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory v4generator = new JsonSchemaV4Factory();

    public SchemaIgnorePropertiesTest(String testName) {
        super(testName);
    }

    /**
     * Test if @SchemaIgnore works correctly
     */
    public void testGenerateSchema() throws JsonProcessingException {

        JsonNode schema = v4generator.createSchema(ItemWrapper.class);
//        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
        JsonNode item = schema.get("properties").get("item");
        assertEquals("\"object\"", item.get("type").toString());
        assertFalse(item.has("properties"));
    }

    static class ItemWrapper {
        int id;
        @SchemaIgnoreProperties
        Item item;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public Item getItem() {
            return item;
        }

        public void setItem(Item item) {
            this.item = item;
        }
    }

    static class Item {
        int itemId;
        String itemName;


        public int getItemId() {
            return itemId;
        }

        public void setItemId(int itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }
    }
}
