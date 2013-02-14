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
import com.github.reinert.jjschema.exception.UnavailableVersion;

import junit.framework.TestCase;

public class ProductTest extends TestCase {

	ObjectWriter om = new ObjectMapper().writerWithDefaultPrettyPrinter();
	
	/**
     * Test the scheme generate following a scheme source, avaliable at
     * http://json-schema.org/example1.html the output should match the example.
	 * @throws IOException 
     */
	public void testProductSchema() throws UnavailableVersion, IOException {
		// Test new implementation using ObjectNode representation
		JsonNode productSchema = SchemaFactory.v4SchemaFrom(Product.class);
		//System.out.println(om.writeValueAsString(productSchema));
		JsonNode productSchemaRes = JsonLoader.fromResource("/res/product_schema.json");
		
		assertEquals(productSchemaRes.get("properties"), productSchema.get("properties"));
		assertEquals(productSchemaRes.get("$schema"), productSchema.get("$schema"));
		assertEquals(productSchemaRes.get("title"), productSchema.get("title"));
		assertEquals(productSchemaRes.get("description"), productSchema.get("description"));
		assertEquals(productSchemaRes.get("type"), productSchema.get("type"));
		// An ArrayNode must be in the same order to match equals
		assertEquals(productSchemaRes.get("required"), productSchema.get("required"));
		assertEquals(productSchemaRes, productSchema);
		
		

//		JsonSchema complexProductSchema = SchemaFactory.v4PojoSchemaFrom(ComplexProduct.class);
////		System.out.println(om.writeValueAsString(complexProductSchema));
//		
//		// Test new implementation using ObjectNode representation
//		JsonNode complexProdNodeSchema = SchemaFactory.v4SchemaFrom(ComplexProduct.class);
////		System.out.println(om.writeValueAsString(complexProdNodeSchema));
//		
//		JsonNode complexProductPojoSchema = MAPPER.readTree(om.writeValueAsString(complexProductSchema));
//		assertTrue(complexProdNodeSchema.equals(complexProductPojoSchema));
//		
//		
//		
//		
//		JsonSchema productSetSchema = SchemaFactory.v4PojoSchemaFrom(ProductSet.class);
////		System.out.println(om.writeValueAsString(productSetSchema));
//		
//		// Test new implementation using ObjectNode representation
//		JsonNode prodSetNodeSchema = SchemaFactory.v4SchemaFrom(ProductSet.class);
////		System.out.println(om.writeValueAsString(prodSetNodeSchema));
//		
//		JsonNode productSetPojoSchema = MAPPER.readTree(om.writeValueAsString(productSetSchema));
//		assertTrue(prodSetNodeSchema.equals(productSetPojoSchema));
//		
		JsonNode productSetSchema = SchemaFactory.v4SchemaFrom(ProductSet.class);
		//System.out.println(om.writeValueAsString(productSetSchema));
		JsonNode productSetSchemaRes = JsonLoader.fromResource("/res/products_set_schema.json");
		
		//assertEquals(productSetSchemaRes.get("properties"), productSetSchema.get("properties"));
		assertEquals(productSetSchemaRes.get("$schema"), productSetSchema.get("$schema"));
		assertEquals(productSetSchemaRes.get("title"), productSetSchema.get("title"));
		//assertEquals(productSetSchemaRes.get("description"), productSetSchema.get("description"));
		assertEquals(productSetSchemaRes.get("type"), productSetSchema.get("type"));
		// An ArrayNode must be in the same order to match equals
		JsonNode pSetResItems = productSetSchemaRes.get("items");
		JsonNode pSetItems = productSetSchema.get("items");
		
		assertEquals(pSetResItems.get("title"), pSetItems.get("title"));
		assertEquals(pSetResItems.get("type"), pSetItems.get("type"));
		assertEquals(pSetResItems.get("required"), pSetItems.get("required"));
		
		JsonNode pSetResItemsProps = pSetResItems.get("properties");
		JsonNode pSetItemsProps = pSetItems.get("properties");
		assertEquals(pSetResItemsProps.get("dimensions"), pSetItemsProps.get("dimensions"));
		assertEquals(pSetResItemsProps.get("warehouseLocation"), pSetItemsProps.get("warehouseLocation"));
		assertEquals(pSetResItemsProps.get("id"), pSetItemsProps.get("id"));
		assertEquals(pSetResItemsProps.get("price"), pSetItemsProps.get("price"));
		assertEquals(pSetResItemsProps.get("name"), pSetItemsProps.get("name"));
		assertEquals(pSetResItemsProps.get("tags"), pSetItemsProps.get("tags"));
		assertEquals(pSetResItemsProps, pSetItemsProps);
		
		assertEquals(pSetResItems, pSetItems);
		
		assertEquals(productSetSchemaRes, productSetSchema);
		
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
	
	@SchemaProperty($schema=SchemaRef.V4, title="Product set")
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
