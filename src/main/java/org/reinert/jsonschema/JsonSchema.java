package org.reinert.jsonschema;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonInclude(Include.NON_DEFAULT)
public class JsonSchema {

	private String m$schema = "";
	private String m$ref = "";
    private String mId = "";
    private String mTitle = "";
    private String mDescription = "";
    private Object mDefault = null;
    private Integer mMultipleOf = 0;
    private Long mMaximum = -1l;
    private Boolean mExclusiveMaximum = false;
    private Integer mMinimum = -1;
    private Boolean mExclusiveMinimum = false;
    private Integer mMaxLength = 0;
    private Integer mMinLength = 0;
    private String mPattern = "";
    private Integer mMaxItems = 0;
    private Integer mMinItems = 0;
    private Boolean mUniqueItems = false;
    private JsonSchema mItems = null;
    private ArrayList<String> mRequired = null;
    @JsonIgnore
    private Boolean mSelfRequired = false;
    private String[] mEnum = null;
    private String mType = "";
    private HashMap<String, JsonSchema> mProperties = null;

    public JsonSchema() {
	}
    
    public JsonSchema(String $schema) {
    	m$schema = $schema;
	}

	public String get$schema() {
		return m$schema;
	}

	public void set$schema(String $schema) {
		m$schema = $schema;
	}

	public String get$ref() {
		return m$ref;
	}

	public void set$ref(String $ref) {
		m$ref = $ref;
	}

	public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public Object getDefault() {
        return mDefault;
    }

    public void setDefault(Object default1) {
        mDefault = default1;
    }

    public Integer getMultipleOf() {
        return mMultipleOf;
    }

    public void setMultipleOf(Integer multipleOf) {
        mMultipleOf = multipleOf;
    }

    public Long getMaximum() {
        return mMaximum;
    }

    public void setMaximum(Long maximum) {
        mMaximum = maximum;
    }

    public Integer getMinimum() {
        return mMinimum;
    }

    public void setMinimum(Integer minimum) {
        mMinimum = minimum;
    }

    public JsonSchema getItems() {
        return mItems;
    }

    public void setItems(JsonSchema items) {
        mItems = items;
    }

    public ArrayList<String> getRequired() {
        return mRequired;
    }

    public void setRequired(ArrayList<String> required) {
        mRequired = required;
    }

    public void addRequired(String field) {
        if (mRequired == null) {
            mRequired = new ArrayList<String>();
        }
        mRequired.add(field);
    }

    protected Boolean getSelfRequired() {
        return mSelfRequired;
    }

    protected void setSelfRequired(Boolean selfRequired) {
        mSelfRequired = selfRequired;
    }

    public String[] getEnum() {
        return mEnum;
    }

    public void setEnum(String[] values) {
        mEnum = values;
    }
    
    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public HashMap<String, JsonSchema> getProperties() {
        return mProperties;
    }

    public JsonSchema getProperty(String name) {
        return mProperties.get(name);
    }

    public boolean hasProperties() {
        return mProperties != null;
    }

    public void addProperty(String propertyName, JsonSchema propertyValue) {
        if (mProperties == null) {
            mProperties = new HashMap<String, JsonSchema>();
        }
        mProperties.put(propertyName, propertyValue);
    }

    public Boolean getExclusiveMaximum() {
        return mExclusiveMaximum;
    }

    public void setExclusiveMaximum(Boolean exclusiveMaximum) {
        mExclusiveMaximum = exclusiveMaximum;
    }

    public Boolean getExclusiveMinimum() {
        return mExclusiveMinimum;
    }

    public void setExclusiveMinimum(Boolean exclusiveMinimum) {
        mExclusiveMinimum = exclusiveMinimum;
    }

    public Integer getMaxLength() {
        return mMaxLength;
    }

    public void setMaxLength(Integer maxLength) {
        mMaxLength = maxLength;
    }

    public Integer getMinLength() {
        return mMinLength;
    }

    public void setMinLength(Integer minLength) {
        mMinLength = minLength;
    }

    public String getPattern() {
        return mPattern;
    }

    public void setPattern(String pattern) {
        mPattern = pattern;
    }

    public Integer getMaxItems() {
        return mMaxItems;
    }

    public void setMaxItems(Integer maxItems) {
        mMaxItems = maxItems;
    }

    public Integer getMinItems() {
        return mMinItems;
    }

    public void setMinItems(Integer minItems) {
        mMinItems = minItems;
    }

    public Boolean getUniqueItems() {
        return mUniqueItems;
    }

    public void setUniqueItems(Boolean uniqueItems) {
        mUniqueItems = uniqueItems;
    }

    public void setProperties(HashMap<String, JsonSchema> properties) {
        mProperties = properties;
    }

    @Override
    public String toString() {
        ObjectMapper m = new ObjectMapper();
        try {
            return m.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void bindProperties(SchemaProperty props) {
    	if (!props.$ref().isEmpty()) {
        	this.set$ref(props.$ref());
        }
    	if (!props.$schema().isEmpty()) {
        	this.set$schema(props.$schema());
        }
        if (!props.id().isEmpty()) {
            this.setId(props.id());
        }
        if (props.required()) {
            this.setSelfRequired(true);
        }
        if (!props.description().isEmpty()) {
            this.setDescription(props.description());
        }
        if (!props.pattern().isEmpty()) {
            this.setPattern(props.pattern());
        }
        if (!props.title().isEmpty()) {
            this.setTitle(props.title());
        }
        if (props.maximum() > -1) {
        	this.setMaximum(props.maximum());
        }
        if (props.exclusiveMaximum()) {
        	this.setExclusiveMaximum(true);
        }
        if (props.minimum() > -1) {
        	this.setMinimum(props.minimum());
        }
        if (props.exclusiveMinimum()) {
        	this.setExclusiveMinimum(true);
        }
        if (props.enums().length > 0) {
        	this.setEnum(props.enums());
        }
        if (props.uniqueItems()) {
        	this.setUniqueItems(true);
        }
        if (props.minItems() > 0) {
        	this.setMinItems(props.minItems());
        }
    }
    
    //TODO: compare with default values using reflection
    private void mergeSchema(JsonSchema schema, boolean forceOverride) {
    	if (forceOverride) {
    		if (!schema.get$ref().isEmpty() && !schema.get$ref().equals(m$ref)) {
    			set$ref(schema.get$ref());
    		}
    		if (!schema.get$schema().isEmpty() && !schema.get$schema().equals(m$schema)) {
    			set$schema(schema.get$schema());
    		}
    		if (schema.getDefault() != null && !schema.getDefault().equals(mDefault)) {
    			setDefault(schema.getDefault());
    		}
    		if (!schema.getDescription().isEmpty() && !schema.getDescription().equals(mDescription)) {
    			setDescription(schema.getDescription());
    		}
    		if (schema.getEnum() != null && !schema.getEnum().equals(mEnum)) {
    			setEnum(schema.getEnum());
    		}
    		if (!schema.getExclusiveMaximum().equals(mExclusiveMaximum)) {
    			setExclusiveMaximum(schema.getExclusiveMaximum());
    		}
    		if (!schema.getExclusiveMinimum().equals(mExclusiveMinimum)) {
    			setExclusiveMinimum(schema.getExclusiveMinimum());
    		}
    		if (!schema.getId().isEmpty() && !schema.getId().equals(mId)) {
    			setId(schema.getId());
    		}
    		if (schema.getItems() != null && !schema.getItems().equals(mItems)) {
    			setItems(schema.getItems());
    		}
    		if (schema.getMaximum() > -1l && !schema.getMaximum().equals(mMaximum)) {
    			setMaximum(schema.getMaximum());
    		}
    		if (schema.getMaxItems() > 0 && !schema.getMaxItems().equals(mMaxItems)) {
    			setMaxItems(schema.getMaxItems());
    		}
    		if (schema.getMaxLength() > 0 && !schema.getMaxLength().equals(mMaxLength)) {
    			setMaxLength(schema.getMaxLength());
    		}
    		if (getMinimum() > -1 && !schema.getMinimum().equals(mMinimum)) {
    			setMinimum(schema.getMinimum());
    		}
    		if (schema.getMinItems() > 0 && !schema.getMinItems().equals(mMinItems)) {
    			setMinItems(schema.getMinItems());
    		}
    		if ((schema.getMinLength() > 0) && !schema.getMinLength().equals(mMinLength)) {
    			setMinLength(schema.getMinLength());
    		}
    		if (!schema.getMultipleOf().equals(mMultipleOf)) {
    			setMultipleOf(schema.getMultipleOf());
    		}
    		if (!schema.getPattern().isEmpty() && !schema.getPattern().equals(mPattern)) {
    			setPattern(schema.getPattern());
    		}
    		if (schema.getRequired() != null && !schema.getRequired().equals(mRequired)) {
    			setRequired(schema.getRequired());
    		}
    		if (!schema.getSelfRequired().equals(mSelfRequired)) {
    			setSelfRequired(schema.getSelfRequired());
    		}
    		if (!schema.getTitle().isEmpty() && !schema.getTitle().equals(mTitle)) {
    			setTitle(schema.getTitle());
    		}
    		if (!schema.getType().equals(mType)) {
    			setType(schema.getType());
    		}
    		if (!schema.getUniqueItems().equals(mUniqueItems)) {
    			setUniqueItems(schema.getUniqueItems());
    		}

    		HashMap<String, JsonSchema> properties = schema.getProperties();
			if (properties != null) {
				if (mProperties == null) mProperties = new HashMap<String, JsonSchema>();
    			for (Entry<String, JsonSchema> entry : properties.entrySet()) {
    				String pName = entry.getKey();
    				JsonSchema pSchema = entry.getValue();
    				JsonSchema actualSchema = mProperties.get(pName);
    				if (actualSchema != null) {
    					pSchema.mergeSchema(actualSchema, true);
    				}
    				mProperties.put(pName, pSchema);
    			}
    		}
    	} else {
    		//TODO: implement not forcing overriding
    	}
    }

    public static <T> JsonSchema from(Class<T> type) {
        JsonSchema schema = new JsonSchema();

        if (type == Integer.class || type == int.class) {
            schema.setType("integer");
        } else if (type == Short.class || type == short.class) {
            schema.setType("integer");
        } else if (type == Byte.class || type == byte.class) {
            schema.setType("integer");
        } else if (type == Long.class || type == long.class) {
            schema.setType("integer");
        } else if (type == Float.class || type == float.class) {
            schema.setType("number");
        } else if (type == Double.class || type == double.class) {
            schema.setType("number");
        } else if (type == BigDecimal.class) {
            schema.setType("number");
        } else if (type == String.class || type == CharSequence.class) {
            schema.setType("string");
        } else if (type == Character.class || type == char.class) {
            schema.setType("string");
        } else if (type == Boolean.class || type == boolean.class) {
            schema.setType("boolean");
        } else if (Iterable.class.isAssignableFrom(type)) {
            schema.setType("array");
            if (!Collection.class.isAssignableFrom(type)) {
                // NOTE: Customized Iterable Class must declare the Collection object at first
                Field field = type.getDeclaredFields()[0];
                ParameterizedType genericType = (ParameterizedType) field.getGenericType();
                Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
                schema.setItems(from(genericClass));
            }
        } else if (type == Void.class || type == void.class) {
            return null;
        } else {
            schema.setType("object");
            // fill base object properties
            SchemaProperty sProp = type.getAnnotation(SchemaProperty.class);
            if(sProp != null)
                schema.bindProperties(sProp);
            
            Field[] fields = type.getDeclaredFields();
            Method[] methods = type.getMethods();
            HashMap<Method, Field> props = new HashMap<Method, Field>();
            // get valid properties (get method and respective field (if exists))
            for (Method method : methods) {
                Class<?> declaringClass = method.getDeclaringClass();
                if (declaringClass.equals(Object.class) || Collection.class.isAssignableFrom(declaringClass)) {
                    continue;
                }

                String methodName = method.getName();
                if (methodName.startsWith("get")) {
                    boolean hasField = false;
                    for (Field field : fields) {
                        String name = methodName.substring(3);
                        if (field.getName().equalsIgnoreCase(name)) {
                            props.put(method, field);
                            hasField = true;
                            break;
                        }
                    }
                    if (!hasField) {
                        props.put(method, null);
                    }
                }
            }
            
            for (Map.Entry<Method, Field> entry : props.entrySet()) {
                Field field = entry.getValue();
                Method method = entry.getKey();

                JsonSchema prop = generateSchema(method, field);

                String name = (field == null)
                        ? firstToLowCase(method.getName().replace("get", "")) : field.getName();
                if (prop.getSelfRequired()) {
                    schema.addRequired(name);
                }

                schema.addProperty(name, prop);
            }
            
            // Merge with parent class
            Class<? super T> superclass = type.getSuperclass();
			if (superclass != Object.class) {
            	JsonSchema parentSchema = from(superclass);
            	parentSchema.mergeSchema(schema, true);
            	schema = parentSchema;
            }
        }

        return schema;
    }

    static JsonSchema generateSchema(Method method, Field field) {
        JsonSchema schema = new JsonSchema();

        if (Collection.class.isAssignableFrom(method.getReturnType())) {
            schema.setType("array");
            ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
            Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
            schema.setItems(from(genericClass));
        } else {
            schema = (JsonSchema) from(method.getReturnType());
        }

        // Check the field annotations if the get method references a field or the   
        // method annotations on the other hand and bind them to the JsonSchema object
        Annotation[] ans = field != null ? field.getAnnotations() : method.getAnnotations();
        for (Annotation a : ans) {
            if (a.annotationType() == SchemaProperty.class) {
                SchemaProperty sProp = (SchemaProperty) a;
                schema.bindProperties(sProp);
                // The declaration of $schema is only necessary at the root object
                schema.set$schema("");
            }
        }

        return schema;
    }

    static String firstToLowCase(String string) {
        return Character.toLowerCase(string.charAt(0)) + (string.length() > 1 ? string.substring(1) : "");
    }
}
