package com.github.reinert.jjschema;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.reinert.jjschema.JsonSchema;
import com.github.reinert.jjschema.SchemaFactory;
import com.github.reinert.jjschema.SchemaProperty;
import com.github.reinert.jjschema.SchemaRef;
import com.github.reinert.jjschema.exception.UnavailableVersion;

import junit.framework.TestCase;

public class ProductTest extends TestCase {

	ObjectWriter om = new ObjectMapper().writerWithDefaultPrettyPrinter();
	static final ObjectMapper MAPPER = new ObjectMapper();
	
	/**
     * Test the scheme generate following a scheme source, avaliable at
     * http://json-schema.org/example1.html the output should match the example.
	 * @throws IOException 
     */
	public void testProductSchema() throws UnavailableVersion, IOException {
		JsonSchema productSchema = SchemaFactory.v4PojoSchemaFrom(Product.class);
//		System.out.println(om.writeValueAsString(productSchema));
		
		// Test new implementation using ObjectNode representation
		JsonNode prodNodeSchema = SchemaFactory.v4SchemaFrom(Product.class);
//		System.out.println(om.writeValueAsString(prodNodeSchema));
		
		JsonNode productPojoSchema = MAPPER.readTree(om.writeValueAsString(productSchema));
		assertTrue(prodNodeSchema.equals(productPojoSchema));
		
		
		

		JsonSchema complexProductSchema = SchemaFactory.v4PojoSchemaFrom(ComplexProduct.class);
//		System.out.println(om.writeValueAsString(complexProductSchema));
		
		// Test new implementation using ObjectNode representation
		JsonNode complexProdNodeSchema = SchemaFactory.v4SchemaFrom(ComplexProduct.class);
//		System.out.println(om.writeValueAsString(complexProdNodeSchema));
		
		JsonNode complexProductPojoSchema = MAPPER.readTree(om.writeValueAsString(complexProductSchema));
		assertTrue(complexProdNodeSchema.equals(complexProductPojoSchema));
		
		
		
		
		JsonSchema productSetSchema = SchemaFactory.v4PojoSchemaFrom(ProductSet.class);
//		System.out.println(om.writeValueAsString(productSetSchema));
		
		// Test new implementation using ObjectNode representation
		JsonNode prodSetNodeSchema = SchemaFactory.v4SchemaFrom(ProductSet.class);
		System.out.println(om.writeValueAsString(prodSetNodeSchema));
		
		JsonNode productSetPojoSchema = MAPPER.readTree(om.writeValueAsString(productSetSchema));
		assertTrue(prodSetNodeSchema.equals(productSetPojoSchema));
		
		
	}
	
	
	@SchemaProperty($schema=SchemaRef.V4, title="Product", description="A product from Acme's catalog")
	static class Product {

		@SchemaProperty(required=true, description="The unique identifier for a product")
		private long id;
		@SchemaProperty(required=true, description="Name of the product")
		private String name;
		@SchemaProperty(required=true, minimum=0, exclusiveMinimum=true)
		private BigDecimal price;
		@SchemaProperty(minItems=1,uniqueItems=true)
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
		@SchemaProperty(description="Coordinates of the warehouse with the product")
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
		
		@SchemaProperty(required=true)
		private double length;
		@SchemaProperty(required=true)
		private double width;
		@SchemaProperty(required=true)
		private double heigth;
		
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
		public double getHeigth() {
			return heigth;
		}
		public void setHeigth(double heigth) {
			this.heigth = heigth;
		}
		
	}
	
	@SchemaProperty($ref="http://json-schema.org/geo", description="A geographical coordinate")
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
	
	static class ProductSet implements Iterable<ComplexProduct> {
		
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
