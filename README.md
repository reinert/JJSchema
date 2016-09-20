JJSchema
===============

A Java JSON Schema and Hyper-Schema generator. Currently, it is based on v4 draft.

Latest Release
----------------

```xml
<dependency>
  <groupId>com.github.nilportugues</groupId>
  <artifactId>jjschema</artifactId>
  <version>1.1</version>
</dependency>
```

Simple HOW TO
----------------

Suppose the following Class:

```java
@JsonSchema(title="Product", description="A product from Acme's catalog")
static class Product {
    
	@JsonSchema(alias="product-id", required=true, description="The unique identifier for a product")
	private long id;
	
	@JsonSchema(required=true, description="Name of the product")
	private String name;
	
	@JsonSchema(required=true, minimum=0, exclusiveMinimum=true)
	private BigDecimal price;
	
	@JsonSchema(minItems=1,uniqueItems=true)
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
```

Type the following code:

```java
JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().build();
JsonNode productSchema = v4generator.generateSchema(Product.class);
System.out.println(productSchema);
```

The output:

```json
{
  "type" : "object",
  "description" : "A product from Acme's catalog",
  "title" : "Product",
  "properties" : {
    "product-id" : {
      "type" : "integer",
      "description" : "The unique identifier for a product"
    },
    "name" : {
      "type" : "string",
      "description" : "Name of the product"
    },
    "price" : {
      "type" : "number",
      "minimum" : 0,
      "exclusiveMinimum" : true
    },
    "tags" : {
      "type" : "array",
      "items" : {
        "type" : "string"
      },
      "uniqueItems" : true,
      "minItems" : 1
    }
  },
  "required" : [ "id", "name", "price" ],
  "$schema" : "http://json-schema.org/draft-04/schema#"
}
```

## Thanks to

Danilo Reinert for writing the original version: https://github.com/reinert/JJSchema
