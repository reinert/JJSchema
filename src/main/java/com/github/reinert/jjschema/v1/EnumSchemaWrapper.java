package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.node.ArrayNode;

import java.math.BigDecimal;

/**
 * @author Danilo Reinert
 */

public class EnumSchemaWrapper extends SchemaWrapper {

    public <T> EnumSchemaWrapper(Class<T> type) {
        super(type);
        processEnum(type);
    }

    @Override
    public boolean isEnumWrapper() {
        return true;
    }

    @Override
    protected String extractType(Class<?> type) {
        return null;
    }

    private <T> void processEnum(Class<T> type) {
        ArrayNode enumArray = node.putArray("enum");
        for (T constant : type.getEnumConstants()) {
            String value = constant.toString();
            // Check if value is numeric
            try {
                // First verifies if it is an integer
                Long integer = Long.parseLong(value);
                enumArray.add(integer);
                node.put("type", "integer");
            }
            // If not then verifies if it is an floating point number
            catch (NumberFormatException e) {
                try {
                    BigDecimal number = new BigDecimal(value);
                    enumArray.add(number);
                    node.put("type", "number");
                }
                // Otherwise add as String
                catch (NumberFormatException e1) {
                    enumArray.add(value);
                    node.put("type", "string");
                }
            }
        }
    }
}
