package org.reinert.jsonschema.tests.model;

import java.math.BigDecimal;
import java.util.List;

import org.reinert.jsonschema.SchemaProperty;
import org.reinert.jsonschema.SchemaRef;

@SchemaProperty($schema=SchemaRef.V4, title="Product", description="A product from Acme's catalog")
public class Product {

	@SchemaProperty(description="The unique identifier for a product")
	private long id;
	@SchemaProperty(description="Name of the product")
	private String name;
	@SchemaProperty(minimum=0, exclusiveMinimum=true)
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
