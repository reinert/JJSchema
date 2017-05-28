JJSchema
===============

A Java JSON Schema and Hyper-Schema generator.
Currently, it is based on v4 draft.
Supports Java 8 Date and Time API.

Latest Release
----------------

```xml
<dependency>
  <groupId>com.github.reinert</groupId>
  <artifactId>jjschema</artifactId>
  <version>1.3</version>
</dependency>
```

Simple HOW TO
----------------

Suppose the following Class:

```java
@Attributes(title="Product", description="A product from Acme's catalog")
static class Product {
	@Attributes(required=true, description="The unique identifier for a product")
	private long id;
	@Attributes(required=true, description="Name of the product")
	private String name;
	@Attributes(required=true, minimum=0, exclusiveMinimum=true)
	private BigDecimal price;
	@Attributes(minItems=1,uniqueItems=true)
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
JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();
schemaFactory.setAutoPutDollarSchema(true);
JsonNode productSchema = schemaFactory.createSchema(Product.class);
System.out.println(productSchema);
```

The output:

```json
{
  "type" : "object",
  "description" : "A product from Acme's catalog",
  "title" : "Product",
  "properties" : {
    "id" : {
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

Thanks to
----------------

[![IntelliJ](https://lh6.googleusercontent.com/--QIIJfKrjSk/UJJ6X-UohII/AAAAAAAAAVM/cOW7EjnH778/s800/banner_IDEA.png)](http://www.jetbrains.com/idea/index.html)
