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

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.fge.jsonschema.SchemaVersion;


/**
* Implements the JSON Schema generation according to draft v4
* @author reinert
*/
public class JsonSchemaGeneratorV4 extends JsonSchemaGenerator {

	@Override
	protected void bind(ObjectNode schema, SchemaProperty props) {
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
        	schema.put("minLength", props.minItems());
        }
        if (props.maxLength() > -1) {
        	schema.put("maxLength", props.maxItems());
        }
    }

}
