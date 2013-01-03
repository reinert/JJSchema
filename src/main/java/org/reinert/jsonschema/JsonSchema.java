package org.reinert.jsonschema;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_DEFAULT)
public class JsonSchema {
	private String mId = "";
	private String mTitle = "";
	private String mDescription = "";
	private Object mDefault = null;
	private Integer mMultipleOf = null;
	private Long mMaximum = -1l;
	private Boolean mExclusiveMaximum = false;
	private Integer mMinimum = -1;
	private Boolean mExclusiveMinimum = false;
	private Integer mMaxLength = null;
	private Integer mMinLength = null;
	private String mPattern = "";
	private Integer mMaxItems = null;
	private Integer mMinItems = null;
	private Boolean mUniqueItems = false;
	private JsonSchema mItems = null;
	private Boolean mRequired = false;
	private String[] mEnum = null;
	private String mType = null;
	private HashMap<String, JsonSchema> mProperties = null;
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
	public Boolean getRequired() {
		return mRequired;
	}
	public void setRequired(Boolean required) {
		mRequired = required;
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
	public void addProperty(String propertyName, JsonSchema propertyValue) {
		if (mProperties == null) mProperties = new HashMap<String, JsonSchema>();
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
	
	public static <T> JsonSchema generateSchema(Class<T> type) {
		JsonSchema schema = new JsonSchema();
		
		if (type == Integer.class || type == int.class) {
			schema.setType("integer");
		}
		else if (type == Short.class || type == short.class) {
			schema.setType("integer");
		}
		else if (type == Byte.class || type == byte.class) {
			schema.setType("integer");
		}
		else if (type == Long.class || type == long.class) {
			schema.setType("integer");
		}
		else if (type == Float.class || type == float.class) {
			schema.setType("number");
		}
		else if (type == Double.class || type == double.class) {
			schema.setType("number");
		}
		else if (type == BigDecimal.class) {
			schema.setType("number");
		}
		else if (type == String.class) {
			schema.setType("string");
		}
		else if (type == Character.class || type == char.class) {
			schema.setType("string");
		}
		else if (type == CharSequence.class) {
			schema.setType("string");
		}
		else if (type == Boolean.class) {
			schema.setType("boolean");
		}
		else if (Iterable.class.isAssignableFrom(type)) {
			schema.setType("array");
			
			if (!Collection.class.isAssignableFrom(type)) {
				Field field = type.getDeclaredFields()[0];
				ParameterizedType genericType = (ParameterizedType) field.getGenericType();
		        Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
		        schema.setItems(generateSchema(genericClass));
			}
		}
		else {
			schema.setType("object");
			
			for (Field field : type.getDeclaredFields()) {
				JsonSchema prop = generateSchema(field);
				schema.addProperty(field.getName(), prop);
			}
		}
		
		return schema;
	}
	
	static JsonSchema generateSchema(Field field) {
		JsonSchema schema = new JsonSchema();
		
		if (Collection.class.isAssignableFrom(field.getType())) {
			schema.setType("array");
			ParameterizedType genericType = (ParameterizedType) field.getGenericType();
	        Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
	        schema.setItems(generateSchema(genericClass));
		} else {
			schema = generateSchema(field.getType());
		}
		
		Annotation[] ans = field.getAnnotations();
		for (Annotation a : ans) {
			if (a.annotationType() == SchemaProperty.class) {
				SchemaProperty props = (SchemaProperty) a;
				if (!props.id().isEmpty()) {
					schema.setId(props.id());
				}
				if (!props.required()) {
					schema.setRequired(true);
				}
				if (!props.description().isEmpty()) {
					schema.setDescription(props.description());
				}
				if (!props.pattern().isEmpty()) {
					schema.setPattern(props.pattern());
				}
				if (!props.title().isEmpty()) {
					schema.setTitle(props.title());
				} 
//				else {
//					String fieldName = field.getName();
//					StringBuilder titleBuilder = new StringBuilder(fieldName.substring(0,1).toUpperCase());
//					titleBuilder.append(fieldName.substring(1));
//					schema.setTitle(titleBuilder.toString());
//				}
				if (props.maximun() > -1) {
					schema.setMaximum(props.maximun());
				}
				if (props.minimum() > -1) {
					schema.setMinimum(props.minimum());
				}
				if (props.enums().length > 0) {
					schema.setEnum(props.enums());
				}
			}
		}
		
		return schema;
	}
}
