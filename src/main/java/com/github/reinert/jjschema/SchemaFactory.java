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
import com.github.reinert.jjschema.deprecated.JsonSchema;
import com.github.reinert.jjschema.deprecated.JsonSchemaPojoGenerator;
import com.github.reinert.jjschema.deprecated.JsonSchemaPojoGeneratorV4;

public class SchemaFactory {

	private static JsonSchemaPojoGenerator genV4 = new JsonSchemaPojoGeneratorV4();
	private static JsonSchemaGenerator nodeGenV4 = new JsonSchemaGeneratorV4();
	private static HyperSchemaGenerator hyperGenV4 = new HyperSchemaGenerator(nodeGenV4);
	
	public static <T> JsonSchema v4PojoSchemaFrom(Class<T> type) {
		return genV4.generateSchema(type);
	}

	public static <T> JsonNode v4SchemaFrom(Class<T> type) {
		return nodeGenV4.generateSchema(type);
	}
	
	public static <T> JsonNode v4HyperSchemaFrom(Class<T> type) {
		return hyperGenV4.generateHyperSchema(type);
	}
}
