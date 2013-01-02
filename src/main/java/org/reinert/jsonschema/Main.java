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
		telsSchema.setItens(telefoneSchema);
		schema.addProperty("tels", telsSchema);
		
		ObjectMapper m = new ObjectMapper();
		System.out.println(m.writeValueAsString(schema));
	}
	
	static class Pessoa {
    	String nome;
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
    		byte ddd;
    		int numero;
			public byte getDdd() {
				return ddd;
			}
			public void setDdd(byte ddd) {
				this.ddd = ddd;
			}
			public int getNumero() {
				return numero;
			}
			public void setNumero(int numero) {
				this.numero = numero;
			}
    	}
    }

}
