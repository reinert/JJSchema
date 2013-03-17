JJSchema
===============

A Java JSON Schema and Hyper-Schema generator.
Currently, it is based on v4 draft.

Lastest Release
----------------

    <dependency>
      <groupId>com.github.reinert</groupId>
      <artifactId>jjschema</artifactId>
      <version>0.6</version>
    </dependency>

Simple HOW TO
--------------

Suppose the following bean styled class:

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

Type the following code:

    JsonNode productSchema = Schema.v4SchemaFrom(Product.class);
    System.out.println(productSchema);


The output:

    {
        "type":"object",
        "$schema":"http://json-schema.org/draft-04/schema#",
        "title":"Product",
        "description":"A product from Acme's catalog",
        "required":["price","name","id"],
        "properties":{
            "id":{
                "type":"integer",
                "description":"The unique identifier for a product"
            },
            "tags":{
                "type":"array",
                "items":{"type":"string"},
                "minItems":1,
                "uniqueItems":true
            },
            "price":{
                "type":"number",
                "minimum":0,
                "exclusiveMinimum":true
            },
            "name":{
                "type":"string",
                "description":"Name of the product"
            }
        }
    }
