package org.reinert.jsonschema;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;

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

    public Boolean getSelfRequired() {
        return mSelfRequired;
    }

    public void setSelfRequired(Boolean selfRequired) {
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

    public void bind(SchemaProperty props) {
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
    
}
