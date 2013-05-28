package com.github.reinert.jjschema.inheritance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.reinert.jjschema.v1.JsonSchemaFactory;
import com.github.reinert.jjschema.v1.JsonSchemaV4Factory;
import junit.framework.TestCase;

/**
 * @author Danilo Reinert
 */

public class InheritanceTest extends TestCase {

    static ObjectWriter WRITER = new ObjectMapper().writerWithDefaultPrettyPrinter();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    /**
     *
     */
    public void testGenerateSchema() throws JsonProcessingException {
        JsonNode generatedSchema = schemaFactory.createSchema(MusicItem.class);
        System.out.println(WRITER.writeValueAsString(generatedSchema));
    }
}
