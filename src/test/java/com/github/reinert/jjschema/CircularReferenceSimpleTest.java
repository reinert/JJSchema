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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.reinert.jjschema;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.JsonSchemaGenerator;
import com.github.reinert.jjschema.ManagedReference;
import com.github.reinert.jjschema.SchemaGeneratorBuilder;
import junit.framework.TestCase;

import java.io.IOException;
import java.util.List;

/**
 * @author reinert
 */
public class CircularReferenceSimpleTest extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaGenerator v4generator = SchemaGeneratorBuilder.draftV4Schema().setAutoPutSchemaVersion(false).build();

    public CircularReferenceSimpleTest(String testName) {
        super(testName);
    }

    /**
     * Test if @Nullable works at Collection Types
     *
     * @throws JsonProcessingException
     */
    public void testGenerateSchema() throws IOException {

        JsonNode schema = v4generator.generateSchema(Sale.class);
//        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
        
        assertEquals(0, v4generator.getFowardReferences().size());
        assertEquals(0, v4generator.getBackwardReferences().size());
        
        schema = v4generator.generateSchema(SaleItem.class);
        System.out.println(MAPPER.writerWithDefaultPrettyPrinter().writeValueAsString(schema));
        
        assertEquals(0, v4generator.getFowardReferences().size());
        assertEquals(0, v4generator.getBackwardReferences().size());
    }

    static class Sale {
    	int id;
    	@JsonManagedReference
    	List<SaleItem> saleItems;

    	public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public List<SaleItem> getSaleItems() {
			return saleItems;
		}
		public void setSaleItems(List<SaleItem> saleItems) {
			this.saleItems = saleItems;
		}
    }
    
    static class SaleItem {
    	int idSale;
    	int seqNumber;
    	@JsonBackReference
    	Sale parent;
		
    	public int getIdSale() {
			return idSale;
		}
		public void setIdSale(int idSale) {
			this.idSale = idSale;
		}
		public int getSeqNumber() {
			return seqNumber;
		}
		public void setSeqNumber(int seqNumber) {
			this.seqNumber = seqNumber;
		}
		public Sale getParent() {
			return parent;
		}
		public void setParent(Sale parent) {
			this.parent = parent;
		}
    }
}
