package org.reinert.jsonschema.tests.main;

import java.util.ArrayList;

import org.reinert.jsonschema.HyperSchema;
import org.reinert.jsonschema.tests.model.Empresa;
import org.reinert.jsonschema.tests.model.Empresas;
import org.reinert.jsonschema.tests.resource.EmpresaAllResource;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HyperTests {

	/**
	 * @param args
	 * @throws JsonProcessingException 
	 */
	public static void main(String[] args) throws JsonProcessingException {
		ObjectMapper m = new ObjectMapper();
		System.out.println(HyperSchema.generateHyperSchema(EmpresaAllResource.class).toString());
		
		Empresa emp1 = new Empresa((short)1, "PRIMEIRA");
		Empresa emp2 = new Empresa((short)2, "SEGUNDA");
		
		ArrayList<Empresa> empresas = new ArrayList<Empresa>();
		empresas.add(emp1);
		empresas.add(emp2);
		
		Empresas emps = new Empresas(empresas);
		System.out.println();
		System.out.println(m.writeValueAsString(emps));
	}

}
