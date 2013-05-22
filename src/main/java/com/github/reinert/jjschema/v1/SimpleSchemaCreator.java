package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.reinert.jjschema.SimpleTypeMappings;

/**
 * Created with IntelliJ IDEA.
 * User: reinert
 * Date: 22/05/13
 * Time: 09:48
 *
 * @author Danilo Reinert
 */

public class SimpleSchemaCreator implements SchemaCreator {

    public <T> ObjectNode createSchema(Class<T> type) {
        String v = SimpleTypeMappings.forClass(type);

        // If it is not a default java type then return null
        if (v == null)
            return null;

        return SchemaCreator.MAPPER.createObjectNode().put("type", v);
    }

}
