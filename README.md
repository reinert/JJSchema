Bean2JsonSchema
===============

A Java JSON Schema and Hyper-Schema generator.
It is a fresh project and I expect more contributors joining it.

How to use:

Suppose the following bean styled class:

    public class User {
          
        @SchemaProperty(required=true, title="ID", minimum=100000, maximun=999999)
    	private short id;
        
        @SchemaProperty(required=true, description="User's name")
        private String name;
	
        @SchemaProperty(description="User's sex", enums={"M","F"})
        private char sex;
	
        @SchemaProperty(description="User's personal photo")
        @Media(type="image/jpg", binaryEncoding="base64")
        private Byte[] photo;
        
        public short getId() {
            return id;
        }
        
        public void setId(short id) {
            this.id = id;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public char getSex() {
            return sex;
        }
        
        public void setSex(char sex) {
            this.sex = sex;
        }
        
        public Byte[] getPhoto() {
            return photo;
        }
        
        public void setPhoto(Byte[] photo) {
            this.photo = photo;
        }
    }

Type the following code:

    HyperSchema hyperSchema = HyperSchema.generateHyperSchema(User.class);
    System.out.println(hyperSchema.toString());


The output:

    {
        "properties":{
        "id":{
            "type":"integer",
            "maximum":999999,
            "minimum":100000,
            "title":"ID"
        },
        "sex":{
            "type":"string",
            "description":"User's sex",
            "enum":["M","F"]
        },
        "name":{
            "type":"string",
            "description":"User's name"
        },
        "photo":{
            "media":{
                "type":"image/jpg",
                "binaryEncoding":"base64"
            },
            "type":"object",
            "description":"User's personal photo"
        }
    },
    "type":"object",
    "required":["id","name"]
    }
