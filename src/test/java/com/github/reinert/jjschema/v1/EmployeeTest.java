package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.reinert.jjschema.Attributes;
import com.github.reinert.jjschema.exception.UnavailableVersion;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class EmployeeTest extends TestCase {
    private final ObjectMapper MAPPER = new ObjectMapper();
    ObjectWriter ow = MAPPER.writerWithDefaultPrettyPrinter();

    static class Employee {
        @Attributes(required = true, minLength = 5, maxLength = 50, description = "Name")
        private String name;

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

    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    {
        schemaFactory.setAutoPutDollarSchema(true);
    }

    @SuppressWarnings({"unchecked", "rawtypes", "serial"})
    public void testEmployeeSchema() throws UnavailableVersion, IOException {
        JsonNode employeeSchema = schemaFactory.createSchema(Employee.class);
        //System.out.println(ow.writeValueAsString(employeeSchema));
        String str = MAPPER.writeValueAsString(employeeSchema);
        Map<String, Object> result = (Map<String, Object>) MAPPER.readValue(str, Map.class);
        assertNotNull(result);
        assertEquals("object", result.get("type"));
        assertNotNull(result.get("required"));
        List required = (List) result.get("required");
        assertEquals("name", required.get(0));
        assertNotNull(result.get("properties"));
        Map properties = (Map) ((Map) result.get("properties")).get("name");
        assertEquals("string", properties.get("type"));
        assertEquals("Name", properties.get("description"));
        assertEquals(5, properties.get("minLength"));
        assertEquals(50, properties.get("maxLength"));
        properties = (Map) ((Map) result.get("properties")).get("retired");
        assertEquals("boolean", properties.get("type"));
    }

}
