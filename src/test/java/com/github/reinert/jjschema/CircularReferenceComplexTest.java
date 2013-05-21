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

package com.github.reinert.jjschema;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.reinert.jjschema.model.Person;
import com.github.reinert.jjschema.model.Task;
import com.github.reinert.jjschema.model.TaskList;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author reinert
 */
public class CircularReferenceComplexTest extends TestCase {

    static ObjectWriter WRITER = new ObjectMapper().writerWithDefaultPrettyPrinter();
    JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().setAutoPutSchemaVersion(false).build();

    public CircularReferenceComplexTest(String testName) {
        super(testName);
    }

    /**
     * Test if @JsonManagedReference and @JsonBackReference works at a Complex Circular Reference case.
     * This feature is not stable yet.
     *
     * @throws IOException
     */
    public void testGenerateSchema() throws IOException {

        JsonNode taskSchema = v4generator.generateSchema(Task.class);
        System.out.println(WRITER.writeValueAsString(taskSchema));

        assertEquals(0, v4generator.getFowardReferences().size());
        assertEquals(0, v4generator.getBackwardReferences().size());

        JsonNode personSchema = v4generator.generateSchema(Person.class);
        System.out.println(WRITER.writeValueAsString(personSchema));

        assertEquals(0, v4generator.getFowardReferences().size());
        assertEquals(0, v4generator.getBackwardReferences().size());

        JsonNode taskListSchema = v4generator.generateSchema(TaskList.class);
        System.out.println(WRITER.writeValueAsString(taskListSchema));

        assertEquals(0, v4generator.getFowardReferences().size());
        assertEquals(0, v4generator.getBackwardReferences().size());
    }

}
