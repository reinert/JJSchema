/*
 * Copyright (c) 2013, Danilo Reinert <daniloreinert@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.reinert.jjschema;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.fge.jsonschema.util.JsonLoader;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.github.reinert.jjschema.exception.UnavailableVersion;

import junit.framework.TestCase;

public class ProductTest extends TestCase {

	ObjectWriter om = new ObjectMapper().writerWithDefaultPrettyPrinter();
	JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema()
			.build();

	/**
	 * Test the scheme generate following a scheme source, avaliable at
	 * http://json-schema.org/example1.html the output should match the example.
	 * 
	 * @throws IOException
	 */
	public void testProductSchema() throws UnavailableVersion, IOException {
		JsonNode productSchema = v4generator.generateSchema(Product.class);
		// System.out.println(om.writeValueAsString(productSchema));
		JsonNode productSchemaRes = JsonLoader
				.fromResource("/product_schema.json");
		assertEquals(productSchemaRes, productSchema);

		// NOTE that my implementation of ProductSet uses the ComplexProduct
		// class that inherits from Product class. That's an example of
		// inheritance support of JJSchema.
		JsonNode productSetSchema = v4generator
				.generateSchema(ProductSet.class);
		// System.out.println(om.writeValueAsString(productSetSchema));
		JsonNode productSetSchemaRes = JsonLoader
				.fromResource("/products_set_schema.json");
		assertEquals(productSetSchemaRes, productSetSchema);
	}

	@Attributes(title = "Product", description = "A product from Acme's catalog")
	static class Product {

		@Attributes(required = true, description = "The unique identifier for a product")
		private long id;
		@Attributes(required = true, description = "Name of the product")
		private String name;
		@Attributes(required = true, minimum = 0, exclusiveMinimum = true)
		private BigDecimal price;
		@Attributes(minItems = 1, uniqueItems = true)
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
		@Attributes(description = "Coordinates of the warehouse with the product")
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

		@Attributes(required = true)
		private double length;
		@Attributes(required = true)
		private double width;
		@Attributes(required = true)
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

	@Attributes($ref = "http://json-schema.org/geo", description = "A geographical coordinate")
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

	@Attributes(title = "Product set")
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
