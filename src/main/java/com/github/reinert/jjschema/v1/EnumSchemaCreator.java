package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.math.BigDecimal;

/**
 * Created with IntelliJ IDEA.
 * User: reinert
 * Date: 22/05/13
 * Time: 09:48
 *
 * @author Danilo Reinert
 */

public class EnumSchemaCreator implements SchemaCreator {

    public <T> ObjectNode createSchema(Class<T> type) {
        // If it is not an Enum return null
        if (!type.isEnum())
            return null;

        ObjectNode schema = SchemaCreator.MAPPER.createObjectNode();
        ArrayNode enumArray = schema.putArray("enum");
        for (T constant : type.getEnumConstants()) {
            String value = constant.toString();
            // Check if value is numeric
            try {
                // First verifies if it is an integer
                Long integer = Long.parseLong(value);
                enumArray.add(integer);
            }
            // If not then verifies if it is an floating point number
            catch (NumberFormatException e) {
                try {
                    BigDecimal number = new BigDecimal(value);
                    enumArray.add(number);
                }
                // Otherwise add as String
                catch (NumberFormatException e1) {
                    enumArray.add(value);
                }
            }
        }
        return schema;
    }

}
