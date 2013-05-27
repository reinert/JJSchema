/*
 * Copyright (c) 2013, Danilo Reinert <daniloreinert@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.reinert.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.Nullable;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import junit.framework.TestCase;

import java.util.List;

/**
 * @author reinert
 */
public class NullableArrayTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();

    public NullableArrayTest(String testName) {
        super(testName);
    }

    /**
     * Test if @Nullable works at Collection Types
     *
     * @throws com.fasterxml.jackson.core.JsonProcessingException
     */
    public void testGenerateSchema() {

        JsonNode schema = SchemaWrapperFactory.createWrapper(Something.class).asJson();
        System.out.println(schema);

        JsonNode expected = MAPPER.createArrayNode().add("array").add("null");

        assertEquals(expected, schema.get("properties").get("names").get("type"));

    }

    static class Something {

        private int id;
        @Nullable
        private List<String> names;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public List<String> getNames() {
            return names;
        }

        public void setNames(List<String> names) {
            this.names = names;
        }

    }
}
