package com.github.reinert.jjschema.v1;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.reinert.jjschema.exception.UnavailableVersion;

import junit.framework.TestCase;

/**
 * @author lordvlad
 */
public class GenericsTest  extends TestCase {

    static ObjectMapper MAPPER = new ObjectMapper();
    JsonSchemaFactory schemaFactory = new JsonSchemaV4Factory();

    public GenericsTest(String testName) {
        super(testName);
    }

    /**
     * Test generating a json schema for a class that uses generic types and a collection
     * of generic types as properties. Expect the types to be resolved instead of using
     * "object" as property types.
     * 
     * @throws UnavailableVersion
     * @throws JsonProcessingException
     * @throws IOException
     */
    public void testGenerateSchema() throws UnavailableVersion, JsonProcessingException, IOException {

        final InputStream in = SimpleExampleTest.class.getResourceAsStream("/generics_example.json");
        if (in == null)
            throw new IOException("resource not found");
        JsonNode fromResource = MAPPER.readTree(in);
        JsonNode fromJavaType = schemaFactory.createSchema(GenericExample.class);

        assertEquals(fromResource, fromJavaType);
    }
    
    static class Tuple<A, B> {
    	private A first;
    	private B second;
    	public A getFirst() {return first;}
    	public B getSecond() {return second;}
    	public void setFirst(A a) {first=a;}
    	public void setSecond(B b) {second=b;}
    }
    
    static class GenericExample {
    	private Tuple<String, Integer> tuple;
    	private List<Tuple<String,Boolean>> listOfTuples;
		public Tuple<String, Integer> getTuple() {
			return tuple;
		}
		public void setTuple(Tuple<String, Integer> tuple) {
			this.tuple = tuple;
		}
		public List<Tuple<String, Boolean>> getListOfTuples() {
			return listOfTuples;
		}
		public void setListOfTuples(List<Tuple<String, Boolean>> listOfTuples) {
			this.listOfTuples = listOfTuples;
		}
    	
    }
}
