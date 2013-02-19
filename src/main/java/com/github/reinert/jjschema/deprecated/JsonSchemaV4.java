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

package com.github.reinert.jjschema.deprecated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.util.ArrayList;

@JsonInclude(Include.NON_DEFAULT)
public class JsonSchemaV4 extends AbstractJsonSchema implements JsonSchema {

    private ArrayList<String> mRequired;
    
    public JsonSchemaV4() {
	}

    public ArrayList<String> getRequired() {
        return mRequired;
    }

	protected void setRequired(ArrayList<String> required) {
		mRequired = required;
	}

	public void addRequired(String field) {
        if (mRequired == null) {
            mRequired = new ArrayList<String>();
        }
        mRequired.add(field);
    }
    
}
