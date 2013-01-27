package com.github.reinert.jjschema;

import java.util.HashMap;
import java.util.Map;

public class JsonSchemaGeneratorV4 extends JsonSchemaGenerator {

	@Override
	protected AbstractJsonSchema createInstance() {
		AbstractJsonSchema schema = new JsonSchemaV4();
		return schema;
	}

    //TODO: compare with default values using constants
	@Override
	protected AbstractJsonSchema mergeSchema(AbstractJsonSchema parent, AbstractJsonSchema child, boolean forceOverride) {
    	JsonSchemaV4 parentV4 = (JsonSchemaV4) parent;
    	//JsonSchemaV4 _child = (JsonSchemaV4) child;
    	if (forceOverride) {
    		if (child.get$ref() != null && !child.get$ref().equals(parent.get$ref())) {
    			parent.set$ref(child.get$ref());
    		}
    		if (child.get$schema() != null && !child.get$schema().equals(parent.get$schema())) {
    			parent.set$schema(child.get$schema());
    		}
    		if (child.getDefault() != null && !child.getDefault().equals(parent.getDefault())) {
    			parent.setDefault(child.getDefault());
    		}
    		if (child.getDescription() != null && !child.getDescription().equals(parent.getDescription())) {
    			parent.setDescription(child.getDescription());
    		}
    		if (child.getEnum() != null && !child.getEnum().equals(parent.getEnum())) {
    			parent.setEnum(child.getEnum());
    		}
    		if (child.getExclusiveMaximum() != null && !child.getExclusiveMaximum().equals(parent.getExclusiveMaximum())) {
    			parent.setExclusiveMaximum(child.getExclusiveMaximum());
    		}
    		if (child.getExclusiveMinimum() != null && !child.getExclusiveMinimum().equals(parent.getExclusiveMinimum())) {
    			parent.setExclusiveMinimum(child.getExclusiveMinimum());
    		}
    		if (child.getId() != null && !child.getId().equals(parent.getId())) {
    			parent.setId(child.getId());
    		}
    		if (child.getItems() != null && !child.getItems().equals(parent.getItems())) {
    			parent.setItems(child.getItems());
    		}
    		if (child.getMaximum() != null && !child.getMaximum().equals(parent.getMaximum())) {
    			parent.setMaximum(child.getMaximum());
    		}
    		if (child.getMaxItems() != null && !child.getMaxItems().equals(parent.getMaxItems())) {
    			parent.setMaxItems(child.getMaxItems());
    		}
    		if (child.getMaxLength() != null && !child.getMaxLength().equals(parent.getMaxLength())) {
    			parent.setMaxLength(child.getMaxLength());
    		}
    		if (parent.getMinimum() != null && !child.getMinimum().equals(parent.getMinimum())) {
    			parent.setMinimum(child.getMinimum());
    		}
    		if (child.getMinItems() != null && !child.getMinItems().equals(parent.getMinItems())) {
    			parent.setMinItems(child.getMinItems());
    		}
    		if ((child.getMinLength() != null) && !child.getMinLength().equals(parent.getMinLength())) {
    			parent.setMinLength(child.getMinLength());
    		}
    		if (child.getMultipleOf() != null && !child.getMultipleOf().equals(parent.getMultipleOf())) {
    			parent.setMultipleOf(child.getMultipleOf());
    		}
    		if (child.getPattern() != null && !child.getPattern().equals(parent.getPattern())) {
    			parent.setPattern(child.getPattern());
    		}
    		if (child.getRequired() != null && !child.getRequired().equals(parent.getRequired())) {
    			parentV4.setRequired(child.getRequired());
    		}
    		if (child.getSelfRequired() != null && !child.getSelfRequired().equals(parent.getSelfRequired())) {
    			parent.setSelfRequired(child.getSelfRequired());
    		}
    		if (child.getTitle() != null && !child.getTitle().equals(parent.getTitle())) {
    			parent.setTitle(child.getTitle());
    		}
    		if (child.getType() != null && !child.getType().equals(parent.getType())) {
    			parent.setType(child.getType());
    		}
    		if (child.getUniqueItems() != null && !child.getUniqueItems().equals(parent.getUniqueItems())) {
    			parent.setUniqueItems(child.getUniqueItems());
    		}

    		Map<String, JsonSchema> properties = child.getProperties();
			if (properties != null) {
				if (parent.getProperties() == null) parent.setProperties(new HashMap<String, JsonSchema>());
    			for (Map.Entry<String, JsonSchema> entry : properties.entrySet()) {
    				String pName = entry.getKey();
    				AbstractJsonSchema pSchema = (AbstractJsonSchema) entry.getValue();
    				AbstractJsonSchema actualSchema = (AbstractJsonSchema) parent.getProperties().get(pName);
    				if (actualSchema != null) {
    					mergeSchema(pSchema, actualSchema, true);
    				}
    				parent.getProperties().put(pName, pSchema);
    			}
    		}
    	} else {
    		//TODO: implement not forcing overriding
    	}
    	
		return parent;
    }

	@Override
	protected void bind(AbstractJsonSchema schema, SchemaProperty props) {
    	if (!props.$ref().isEmpty()) {
        	schema.set$ref(props.$ref());
        }
    	if (!props.$schema().isEmpty()) {
        	schema.set$schema(props.$schema());
        }
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
        if (props.maximum() > -1) {
        	schema.setMaximum(props.maximum());
        }
        if (props.exclusiveMaximum()) {
        	schema.setExclusiveMaximum(true);
        }
        if (props.minimum() > -1) {
        	schema.setMinimum(props.minimum());
        }
        if (props.exclusiveMinimum()) {
        	schema.setExclusiveMinimum(true);
        }
        if (props.enums().length > 0) {
        	schema.setEnum(props.enums());
        }
        if (props.uniqueItems()) {
        	schema.setUniqueItems(true);
        }
        if (props.minItems() > 0) {
        	schema.setMinItems(props.minItems());
        }
    }

}
