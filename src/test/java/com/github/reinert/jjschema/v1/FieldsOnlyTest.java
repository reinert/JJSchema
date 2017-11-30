package com.github.reinert.jjschema.v1;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import com.github.reinert.jjschema.exception.TypeException;
import com.github.reinert.jjschema.exception.UnavailableVersion;

import junit.framework.TestCase;

@SuppressWarnings({"unchecked", "rawtypes"})
public class FieldsOnlyTest extends TestCase {
    private final ObjectMapper MAPPER = new ObjectMapper();

    static class Employee {
        @Attributes(required = true, minLength = 5, maxLength = 50, description = "Name")
        private String name;

        public String lastName;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private boolean retired;

        public boolean isRetired() {
            return retired;
        }

        public void setRetired(boolean retired) {
            this.retired = retired;
        }
    }

    public void testAnnotatedFieldsOnly() throws UnavailableVersion, IOException, TypeException {
        JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema()
                .processFieldsOnly(true)
                .processAnnotatedOnly(true)
                .build();

        JsonNode schemaNode = v4generator.generateSchema(Employee.class);

        String str = MAPPER.writeValueAsString(schemaNode);
        Map<String, Object> result = MAPPER.readValue(str, Map.class);
        Map props = (Map) result.get(CustomSchemaWrapper.TAG_PROPERTIES);
        assertEquals(1, props.size());
    }

    public void testFieldsOnly() throws UnavailableVersion, IOException, TypeException {
        JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema()
                .processFieldsOnly(true)
                .build();

        JsonNode schemaNode = v4generator.generateSchema(Employee.class);

        String str = MAPPER.writeValueAsString(schemaNode);
        Map<String, Object> result = MAPPER.readValue(str, Map.class);
        Map<String,String> props = (Map) result.get(CustomSchemaWrapper.TAG_PROPERTIES);
        assertEquals(3, props.size());

        // Check properties are sorted (default behaviour)
        String[] keyArr = props.keySet().toArray(new String[0]);
        assertTrue(Arrays.deepEquals(new String[] {"lastName", "name", "retired"}, keyArr));
    }

    public void testNoSortedFields() throws UnavailableVersion, IOException, TypeException {
        JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema()
                .processFieldsOnly(true)
                .sortProperties(false)
                .build();

        JsonNode schemaNode = v4generator.generateSchema(Employee.class);

        String str = MAPPER.writeValueAsString(schemaNode);
        Map<String, Object> result = MAPPER.readValue(str, Map.class);
        Map<String,String> props = (Map) result.get(CustomSchemaWrapper.TAG_PROPERTIES);
        assertEquals(3, props.size());

        String[] keyArr = props.keySet().toArray(new String[0]);
        assertTrue(Arrays.deepEquals(new String[] {"name", "lastName", "retired"}, keyArr));
    }
}
