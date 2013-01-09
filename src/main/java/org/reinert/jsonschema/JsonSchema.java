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

import com.fasterxml.jackson.annotation.JsonIgnore;
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
	private ArrayList<String> mRequired = null;
	@JsonIgnore
	private Boolean mSelfRequired = false;
	private String[] mEnum = null;
	private String mType = null;
	private HashMap<String, JsonSchema> mProperties = null;
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getId()
	 */
	public String getId() {
		return mId;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setId(java.lang.String)
	 */
	public void setId(String id) {
		mId = id;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getTitle()
	 */
	public String getTitle() {
		return mTitle;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setTitle(java.lang.String)
	 */
	public void setTitle(String title) {
		mTitle = title;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getDescription()
	 */
	public String getDescription() {
		return mDescription;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		mDescription = description;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getDefault()
	 */
	public Object getDefault() {
		return mDefault;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setDefault(java.lang.Object)
	 */
	public void setDefault(Object default1) {
		mDefault = default1;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getMultipleOf()
	 */
	public Integer getMultipleOf() {
		return mMultipleOf;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setMultipleOf(java.lang.Integer)
	 */
	public void setMultipleOf(Integer multipleOf) {
		mMultipleOf = multipleOf;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getMaximum()
	 */
	public Long getMaximum() {
		return mMaximum;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setMaximum(java.lang.Long)
	 */
	public void setMaximum(Long maximum) {
		mMaximum = maximum;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getMinimum()
	 */
	public Integer getMinimum() {
		return mMinimum;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setMinimum(java.lang.Integer)
	 */
	public void setMinimum(Integer minimum) {
		mMinimum = minimum;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getItems()
	 */
	public JsonSchema getItems() {
		return mItems;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setItems(org.reinert.jsonschema.JsonSchema)
	 */
	public void setItems(JsonSchema items) {
		mItems = items;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getRequired()
	 */
	public ArrayList<String> getRequired() {
		return mRequired;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setRequired(java.util.ArrayList)
	 */
	public void setRequired(ArrayList<String> required) {
		mRequired = required;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#addRequired(java.lang.String)
	 */
	public void addRequired(String field) {
		if (mRequired == null)
			mRequired = new ArrayList<String>();
		mRequired.add(field);
	}
	protected Boolean getSelfRequired() {
		return mSelfRequired;
	}
	protected void setSelfRequired(Boolean selfRequired) {
		mSelfRequired = selfRequired;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getEnum()
	 */
	public String[] getEnum() {
		return mEnum;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setEnum(java.lang.String[])
	 */
	public void setEnum(String[] values) {
		mEnum = values;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getType()
	 */
	public String getType() {
		return mType;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setType(java.lang.String)
	 */
	public void setType(String type) {
		mType = type;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getProperties()
	 */
	public HashMap<String, JsonSchema> getProperties() {
		return mProperties;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getProperty(java.lang.String)
	 */
	public JsonSchema getProperty(String name) {
		return mProperties.get(name);
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#addProperty(java.lang.String, org.reinert.jsonschema.JsonSchemaImpl)
	 */
	public void addProperty(String propertyName, JsonSchema propertyValue) {
		if (mProperties == null) mProperties = new HashMap<String, JsonSchema>();
		mProperties.put(propertyName, propertyValue);
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getExclusiveMaximum()
	 */
	public Boolean getExclusiveMaximum() {
		return mExclusiveMaximum;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setExclusiveMaximum(java.lang.Boolean)
	 */
	public void setExclusiveMaximum(Boolean exclusiveMaximum) {
		mExclusiveMaximum = exclusiveMaximum;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getExclusiveMinimum()
	 */
	public Boolean getExclusiveMinimum() {
		return mExclusiveMinimum;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setExclusiveMinimum(java.lang.Boolean)
	 */
	public void setExclusiveMinimum(Boolean exclusiveMinimum) {
		mExclusiveMinimum = exclusiveMinimum;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getMaxLength()
	 */
	public Integer getMaxLength() {
		return mMaxLength;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setMaxLength(java.lang.Integer)
	 */
	public void setMaxLength(Integer maxLength) {
		mMaxLength = maxLength;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getMinLength()
	 */
	public Integer getMinLength() {
		return mMinLength;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setMinLength(java.lang.Integer)
	 */
	public void setMinLength(Integer minLength) {
		mMinLength = minLength;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getPattern()
	 */
	public String getPattern() {
		return mPattern;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setPattern(java.lang.String)
	 */
	public void setPattern(String pattern) {
		mPattern = pattern;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getMaxItems()
	 */
	public Integer getMaxItems() {
		return mMaxItems;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setMaxItems(java.lang.Integer)
	 */
	public void setMaxItems(Integer maxItems) {
		mMaxItems = maxItems;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getMinItems()
	 */
	public Integer getMinItems() {
		return mMinItems;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setMinItems(java.lang.Integer)
	 */
	public void setMinItems(Integer minItems) {
		mMinItems = minItems;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#getUniqueItems()
	 */
	public Boolean getUniqueItems() {
		return mUniqueItems;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setUniqueItems(java.lang.Boolean)
	 */
	public void setUniqueItems(Boolean uniqueItems) {
		mUniqueItems = uniqueItems;
	}
	/* (non-Javadoc)
	 * @see org.reinert.jsonschema.JsonSchema#setProperties(java.util.HashMap)
	 */
	public void setProperties(HashMap<String, JsonSchema> properties) {
		mProperties = properties;
	}
	
	public static <T> JsonSchema generateSchema(Class<T> type) {
		for (Annotation a : type.getAnnotations()) {
			System.out.println(a);
		}
		for (Annotation a : type.getDeclaredAnnotations()) {
			System.out.println(a);
		}
		
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
		else if (type == String.class || type == CharSequence.class) {
			schema.setType("string");
		}
		else if (type == Character.class || type == char.class) {
			schema.setType("string");
		}
		else if (type == Boolean.class || type == boolean.class) {
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
		else if (type == Void.class || type == void.class) {
			return null;
		}
		else {
			schema.setType("object");
			
//			for (Field field : type.getDeclaredFields()) {
//				JsonSchema prop = generateSchema(field);
//				if (prop.getSelfRequired()) {
//					schema.addRequired(field.getName());
//				}
//				schema.addProperty(field.getName(), prop);
//			}
			
			Field[] fields = type.getDeclaredFields();
			Method[] methods = type.getMethods();
			
			HashMap<Method, Field> props = new HashMap<Method, Field>();
			
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
			
			for (Map.Entry<Method, Field> entry : props.entrySet())
			{
			    Field field = entry.getValue();
				Method method = entry.getKey();
				
				JsonSchema prop = generateSchema(method, field);
				
				String name = (field == null) ? 
						firstToLowCase(method.getName().replace("get", "")) : field.getName();
				if (prop.getSelfRequired()) {
					schema.addRequired(name);
				}
				
				schema.addProperty(name, prop);
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
			schema = (JsonSchema) generateSchema(field.getType());
		}
		
		Annotation[] ans = field.getAnnotations();
		for (Annotation a : ans) {
			if (a.annotationType().equals(SchemaProperty.class)) {
				SchemaProperty props = (SchemaProperty) a;
				if (!props.id().isEmpty()) {
					schema.setId(props.id());
				}
				if (props.required()) {
					schema.setSelfRequired(true);
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
	
	static JsonSchema generateSchema(Method method, Field field) {
		
		JsonSchema schema = new JsonSchema();
		
		if (Collection.class.isAssignableFrom(method.getReturnType())) {
			schema.setType("array");
			ParameterizedType genericType = (ParameterizedType) method.getGenericReturnType();
	        Class<?> genericClass = (Class<?>) genericType.getActualTypeArguments()[0];
	        schema.setItems(generateSchema(genericClass));
		} else {
			schema = (JsonSchema) generateSchema(method.getReturnType());
		}
		
		Annotation[] ans = field != null ? field.getAnnotations() : method.getAnnotations();
		for (Annotation a : ans) {
			if (a.annotationType() == SchemaProperty.class) {
				SchemaProperty props = (SchemaProperty) a;
				if (!props.id().isEmpty()) {
					schema.setId(props.id());
				}
				if (props.required()) {
					schema.setSelfRequired(true);
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
	
	static String firstToLowCase(String string) {
		return Character.toLowerCase(string.charAt(0)) + (string.length() > 1 ? string.substring(1) : "");
	}
}
