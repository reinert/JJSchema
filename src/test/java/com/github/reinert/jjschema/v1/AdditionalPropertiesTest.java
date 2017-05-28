/*
 * Copyright (c) 2014, Danilo Reinert (daniloreinert@growbit.com)
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
import com.github.reinert.jjschema.Attributes;
import junit.framework.TestCase;

import java.util.List;

/**
 * @author manukura
 */
public class AdditionalPropertiesTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory v4generator = new JsonSchemaV4Factory();

    public AdditionalPropertiesTest(String testName) {
        super(testName);
    }

    public void testGenerateSaleItemSchema() throws JsonProcessingException {
        JsonNode schema = v4generator.createSchema(SaleItem.class);
        // System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
        JsonNode properties = schema.get("properties");
        assertEquals(2, properties.size());
        assertFalse(schema.get("additionalProperties").asBoolean());
    }

    public void testGenerateSaleSchema() throws JsonProcessingException {
        JsonNode schema = v4generator.createSchema(Sale.class);
        // System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
        assertFalse(schema.get("additionalProperties").asBoolean());
        JsonNode properties = schema.get("properties");
        assertEquals(2, properties.size());

        // ensure additional properties is in the nested schema
        assertFalse(properties.findValue("additionalProperties").asBoolean());
    }

    @Attributes(title = "Sale Parent Schema", additionalProperties = false)
    static class Sale {
        int id;

        List<SaleItem> saleItems;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<SaleItem> getSaleItems() {
            return saleItems;
        }

        public void setSaleItems(List<SaleItem> saleItems) {
            this.saleItems = saleItems;
        }
    }

    @Attributes(title = "Sale Item Child Schema", additionalProperties = false)
    static class SaleItem {
        int idSale;
        String name;

        public int getIdSale() {
            return idSale;
        }

        public void setIdSale(int idSale) {
            this.idSale = idSale;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
