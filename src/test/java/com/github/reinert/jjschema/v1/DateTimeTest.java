/*
 * Copyright (c) 2018, Danilo Reinert (daniloreinert@growbit.com)
 *
 * This software is dual-licensed under:
 *
 * - the Lesser General Public License (LGPL) version 3.0 or, at your option, any
 *   later version;
 * - the Apache Software License (ASL) version 2.0.
 *
 * The text of both licenses is available under the src/resources/ directory of
 * this project (under the names LGPL-3.0.txt and ASL-2.0.txt respectively).
 *
 * Direct link to the sources:
 *
 * - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
 * - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
 */

package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.exception.UnavailableVersion;
import junit.framework.TestCase;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

/**
 * @author reinert
 */
public class DateTimeTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public DateTimeTest(String testName) {
        super(testName);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void testGenerateSchema() throws UnavailableVersion, JsonProcessingException, IOException {
        JsonNode fromJavaType = schemaFactory.createSchema(DateTime.class);
//        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(fromJavaType));
        String str = MAPPER.writeValueAsString(fromJavaType);
        Map<String, Object> result = MAPPER.readValue(str, Map.class);
        assertNotNull(result);
        assertEquals("object", result.get("type"));
        assertNotNull(result.get("properties"));
        Map properties = (Map) result.get("properties");
        assertEquals("string", ((Map) properties.get("date")).get("type"));
        assertEquals("string", ((Map) properties.get("instant")).get("type"));
        assertEquals("string", ((Map) properties.get("localDate")).get("type"));
        assertEquals("string", ((Map) properties.get("zonedDateTime")).get("type"));
    }

    static class DateTime {
        private Date date;
        private Instant instant;
        private LocalDate localDate;
        private ZonedDateTime zonedDateTime;

        public Date getDate() {
            return date;
        }

        public Instant getInstant() {
            return instant;
        }

        public LocalDate getLocalDate() {
            return localDate;
        }

        public ZonedDateTime getZonedDateTime() {
            return zonedDateTime;
        }
    }
}
