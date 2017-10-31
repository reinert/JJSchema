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

package com.github.nilportugues.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.fge.jsonschema.util.JsonLoader;
import com.github.nilportugues.jjschema.annotation.JsonSchema;
import com.github.nilportugues.jjschema.exception.UnavailableVersion;
import junit.framework.TestCase;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ProductTest extends TestCase {

    ObjectWriter om = new ObjectMapper().writerWithDefaultPrettyPrinter();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    {
        schemaFactory.setAutoPutDollarSchema(true);
    }

    /**
     * Test the scheme generate following a scheme source, avaliable at
     * http://json-schema.org/example1.html the output should match the example.
     *
     * @throws java.io.IOException
     */
    public void testProductSchema() throws UnavailableVersion, IOException {
        JsonNode productSchema = schemaFactory.createSchema(Product.class);
        //Assert.assertNotNull(om.writeValueAsString(productSchema));
        JsonNode productSchemaRes = JsonLoader
            .fromResource("/product_schema.json");
        assertEquals(productSchemaRes, productSchema);

        //TODO: Add support to custom Iterable classes?
        // NOTE that my implementation of ProductSet uses the ComplexProduct
        // class that inherits from Product class. That's an example of
        // inheritance support of JJSchema.
        /*
        JsonNode productSetSchema = SchemaWrapperFactory.createArrayWrapper(ProductSet.class).putDollarSchema().asJson();
        Assert.assertNotNull(om.writeValueAsString(productSetSchema));
        JsonNode productSetSchemaRes = JsonLoader
                .fromResource("/products_set_schema.json");
        assertEquals(productSetSchemaRes, productSetSchema);
        */
    }

    @JsonSchema(title = "Product", description = "A product from Acme's catalog")
    static class Product {

        @JsonSchema(required = true, description = "The unique identifier for a product")
        private long id;
        @JsonSchema(required = true, description = "Name of the product")
        private String name;
        @JsonSchema(required = true, minimum = 0, exclusiveMinimum = true)
        private BigDecimal price;
        @JsonSchema(minItems = 1, uniqueItems = true)
        private List<String> tags;

        public long getId() {
            return id;
        }

        public void setId(long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public BigDecimal getPrice() {
            return price;
        }

        public void setPrice(BigDecimal price) {
            this.price = price;
        }

        public List<String> getTags() {
            return tags;
        }

        public void setTags(List<String> tags) {
            this.tags = tags;
        }

    }

    static class ComplexProduct extends Product {

        private Dimension dimensions;
        @JsonSchema(description = "Coordinates of the warehouse with the product")
        private Geo warehouseLocation;

        public Dimension getDimensions() {
            return dimensions;
        }

        public void setDimensions(Dimension dimensions) {
            this.dimensions = dimensions;
        }

        public Geo getWarehouseLocation() {
            return warehouseLocation;
        }

        public void setWarehouseLocation(Geo warehouseLocation) {
            this.warehouseLocation = warehouseLocation;
        }

    }

    static class Dimension {

        @JsonSchema(required = true)
        private double length;
        @JsonSchema(required = true)
        private double width;
        @JsonSchema(required = true)
        private double height;

        public double getLength() {
            return length;
        }

        public void setLength(double length) {
            this.length = length;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }

    }

    @JsonSchema($ref = "http://json-schema.org/geo", description = "A geographical coordinate")
    static class Geo {

        private BigDecimal latitude;
        private BigDecimal longitude;

        public BigDecimal getLatitude() {
            return latitude;
        }

        public void setLatitude(BigDecimal latitude) {
            this.latitude = latitude;
        }

        public BigDecimal getLongitude() {
            return longitude;
        }

        public void setLongitude(BigDecimal longitude) {
            this.longitude = longitude;
        }
    }

    @JsonSchema(title = "Product set")
    static class ProductSet implements Iterable<ComplexProduct> {

        // NOTE: all custom collection types must declare the wrapped collection
        // as the first field.
        private Set<ComplexProduct> products;

        public ProductSet(Set<ComplexProduct> products) {
            this.products = products;
        }

        @Override
        public Iterator<ComplexProduct> iterator() {
            return products.iterator();
        }

    }

}
