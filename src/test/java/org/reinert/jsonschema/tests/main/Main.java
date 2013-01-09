package org.reinert.jsonschema.tests.main;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

import javax.ws.rs.Path;

import org.reinert.jsonschema.HyperSchema;
import org.reinert.jsonschema.JsonSchema;
import org.reinert.jsonschema.JsonSchema;
import org.reinert.jsonschema.SchemaProperty;
import org.reinert.jsonschema.tests.main.Main.Pessoa;
import org.reinert.jsonschema.tests.model.Empresa;
import org.reinert.jsonschema.tests.model.Empresas;
import org.reinert.jsonschema.tests.model.Funcionario;
import org.reinert.jsonschema.tests.model.Funcionarios;
import org.reinert.jsonschema.tests.resource.EmpresaResource;
import org.reinert.jsonschema.tests.resource.EmpresasResource;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {

	/**
	 * @param args
	 * @throws JsonProcessingException 
	 */
	public static void main(String[] args) throws JsonProcessingException {
		JsonSchema schema = new JsonSchema();
		schema.setType("object");
		schema.setTitle("Pessoa");
		
		JsonSchema nomeSchema = new JsonSchema();
		nomeSchema.setType("string");
		nomeSchema.setTitle("Nome");
//		nomeSchema.setRequired(true);
		schema.addProperty("nome", nomeSchema);
		
		JsonSchema telsSchema = new JsonSchema();
		telsSchema.setType("array");
		telsSchema.setTitle("Tels");
		JsonSchema telefoneSchema = new JsonSchema();
		telefoneSchema.setTitle("Telefone");
		telefoneSchema.setType("object");
		JsonSchema dddSchema = new JsonSchema();
		dddSchema.setTitle("DDD");
		dddSchema.setType("number");
		telefoneSchema.addProperty("ddd", dddSchema);
		JsonSchema numeroSchema = new JsonSchema();
		numeroSchema.setTitle("Número");
		numeroSchema.setType("integer");
		telefoneSchema.addProperty("numero", numeroSchema);
		telsSchema.setItems(telefoneSchema);
		schema.addProperty("tels", telsSchema);
		
		ObjectMapper m = new ObjectMapper();
//		System.out.println(m.writeValueAsString(schema));
		
//		
//		JsonSchema gen = JsonSchema.generateSchema(Pessoa.class);
//		System.out.println(m.writeValueAsString(gen));
//		
//		Class<Empresas> etype = Empresas.class;
//		JsonSchema genSch = JsonSchema.generateSchema(etype);
//		System.out.println(m.writeValueAsString(genSch));
//		
		Class<EmpresasResource> type = EmpresasResource.class;
		HyperSchema hyper = HyperSchema.generateHyperSchema(type);
		System.out.println(m.writeValueAsString(hyper));
	}
	
	@Path("dada")
	static class Pessoa {
		@SchemaProperty(title="Nomiu", required=true, enums={"JOAO", "MARIA"})
    	String nome;
		@SchemaProperty(description="A idade do sujeito", required=true, minimum=12, maximun=902901920989l)
    	Integer idade;
    	ArrayList<Telefone> tels = new ArrayList<Telefone>();
    	public String getNome() {
    		return nome;
    	}
    	public void setNome(String nome) {
    		this.nome = nome;
    	}
    	public Integer getIdade() {
    		return idade;
    	}
    	public void setIdade(Integer idade) {
    		this.idade = idade;
    	}
    	public ArrayList<Telefone> getTels() {
			return tels;
		}
		public void setTels(ArrayList<Telefone> tels) {
			this.tels = tels;
		}

		static class Telefone {
			@SchemaProperty(required=true)
    		Byte ddd;
			@SchemaProperty(required=true)
    		Integer numero;
			public Byte getDdd() {
				return ddd;
			}
			public void setDdd(Byte ddd) {
				this.ddd = ddd;
			}
			public Integer getNumero() {
				return numero;
			}
			public void setNumero(Integer numero) {
				this.numero = numero;
			}
    	}
    }

}
