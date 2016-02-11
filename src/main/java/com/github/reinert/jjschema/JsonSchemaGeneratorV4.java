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

package com.github.reinert.jjschema;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.SchemaVersion;


/**
 * Implements the JSON Schema generation according to draft v4
 *
 * @author reinert
 */
public class JsonSchemaGeneratorV4 extends JsonSchemaGenerator {

    @Override
    protected void processSchemaProperty(ObjectNode schema, Attributes props) {
        if (!props.$ref().isEmpty()) {
            schema.put("$ref", props.$ref());
        }
        if (autoPutVersion) {
            schema.put("$schema", SchemaVersion.DRAFTV4.getLocation().toString());
        }
        if (!props.id().isEmpty()) {
            schema.put("id", props.id());
        }
        if (props.required()) {
            schema.put("selfRequired", true);
        }
        if (!props.description().isEmpty()) {
            schema.put("description", props.description());
        }
        if (!props.pattern().isEmpty()) {
            schema.put("pattern", props.pattern());
        }
        if (!props.format().isEmpty()) {
            schema.put("format", props.format());
        }
        if (!props.title().isEmpty()) {
            schema.put("title", props.title());
        }
        if (props.maximum() > -1) {
            schema.put("maximum", props.maximum());
        }
        if (props.exclusiveMaximum()) {
            schema.put("exclusiveMaximum", true);
        }
        if (props.minimum() > -1) {
            schema.put("minimum", props.minimum());
        }
        if (props.exclusiveMinimum()) {
            schema.put("exclusiveMinimum", true);
        }
        if (props.enums().length > 0) {
            ArrayNode enumArray = schema.putArray("enum");
            String[] enums = props.enums();
            for (String v : enums) {
                enumArray.add(v);
            }
        }
        if (props.uniqueItems()) {
            schema.put("uniqueItems", true);
        }
        if (props.minItems() > 0) {
            schema.put("minItems", props.minItems());
        }
        if (props.maxItems() > -1) {
            schema.put("maxItems", props.maxItems());
        }
        if (props.multipleOf() > 0) {
            schema.put("multipleOf", props.multipleOf());
        }
        if (props.minLength() > 0) {
            schema.put("minLength", props.minLength());
        }
        if (props.maxLength() > -1) {
            schema.put("maxLength", props.maxLength());
        }
        if (props.readonly()) {
            schema.put("readonly", true);
        }
    }

}
