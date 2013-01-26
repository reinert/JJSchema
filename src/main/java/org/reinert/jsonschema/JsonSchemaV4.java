package org.reinert.jsonschema;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;

@JsonInclude(Include.NON_DEFAULT)
public class JsonSchemaV4 extends AbstractJsonSchema implements JsonSchema {

    private ArrayList<String> mRequired;
    
    public JsonSchemaV4() {
	}

    public ArrayList<String> getRequired() {
        return mRequired;
    }

	protected void setRequired(ArrayList<String> required) {
		mRequired = required;
	}

	public void addRequired(String field) {
        if (mRequired == null) {
            mRequired = new ArrayList<String>();
        }
        mRequired.add(field);
    }
    
}
