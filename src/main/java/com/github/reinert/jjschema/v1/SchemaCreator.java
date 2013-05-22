package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Created with IntelliJ IDEA.
 * User: reinert
 * Date: 22/05/13
 * Time: 09:48
 *
 * @author Danilo Reinert
 */

public interface SchemaCreator {

    static ObjectMapper MAPPER = new ObjectMapper();

    <T> ObjectNode createSchema(Class<T> type);
}
