package org.reinert.jsonschema;

import java.util.ArrayList;

import org.reinert.experiments.App;

import com.fasterxml.jackson.annotation.JsonProperty;
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
		nomeSchema.setRequired(true);
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
		
		JsonSchema gen = JsonSchema.generateSchema(Pessoa.class);
		gen.getProperty("idade").setRequired(true);
		System.out.println(m.writeValueAsString(gen));
	}
	
	static class Pessoa {
		@SchemaProperty(title="Nome", required=true, enums={"JOAO", "MARIA"})
    	String nome;
		@SchemaProperty(description="A idade do sujeito", minimum=12, maximun=902901920989l)
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
    		Byte ddd;
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
