/*
 * Copyright (c) 2014, Danilo Reinert (daniloreinert@growbit.com)
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

package com.github.nilportugues.jjschema.v1;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.github.nilportugues.jjschema.model.Person;
import com.github.nilportugues.jjschema.model.Task;
import com.github.nilportugues.jjschema.model.TaskList;
import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.IOException;

/**
 * @author reinert
 */
public class CircularReferenceComplexTest extends TestCase {

    static ObjectWriter WRITER = new ObjectMapper().writerWithDefaultPrettyPrinter();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public CircularReferenceComplexTest(String testName) {
        super(testName);
    }

    /**
     * Test if @JsonManagedReference and @JsonBackReference works at a Complex Circular Reference case.
     * This feature is not stable yet.
     *
     * @throws java.io.IOException
     */
    public void testGenerateSchema() throws IOException {

        JsonNode taskSchema = schemaFactory.createSchema(Task.class);
        Assert.assertNotNull(WRITER.writeValueAsString(taskSchema));

        JsonNode personSchema = schemaFactory.createSchema(Person.class);
        Assert.assertNotNull(WRITER.writeValueAsString(personSchema));

        JsonNode taskListSchema = schemaFactory.createSchema(TaskList.class);
        Assert.assertNotNull(WRITER.writeValueAsString(taskListSchema));
    }

}
