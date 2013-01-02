package org.reinert.experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.reinert.experiments.App.Pessoa.Telefone;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsonSchema.factories.SchemaFactoryWrapper;
import com.fasterxml.jackson.databind.jsonSchema.types.JsonSchema;
import com.fasterxml.jackson.databind.jsonSchema.types.ObjectSchema;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws JsonProcessingException
    {
    	Pessoa danilo = new Pessoa();
    	danilo.setNome("Danilo Reinert");
    	danilo.setIdade(23);
    	ArrayList<Telefone> tels = new ArrayList<App.Pessoa.Telefone>();
    	Telefone tel1 = new Telefone();
    	tel1.setDdd((byte)79);
    	tel1.setNumero(88070809);
    	tels.add(tel1);
    	Telefone tel2 = new Telefone();
    	tel2.setDdd((byte)88);
    	tel2.setNumero(91319980);
    	tels.add(tel2);
    	danilo.setTels(tels);
    	ObjectMapper m = new ObjectMapper();
    	
    	SchemaFactoryWrapper visitor = new SchemaFactoryWrapper();
    	m.acceptJsonFormatVisitor(m.constructType(User.class), visitor);
    	JsonSchema js = visitor.finalSchema();
    	Map<String,JsonSchema> props = js.asObjectSchema().getProperties();
    	JsonSchema propVerified = props.get("verified");
    	propVerified.setRequired(true);
    	propVerified.asBooleanSchema().setTitle("DADADA");
    	//propVerified.asObjectSchema().setTitle("TITULOO");
    	
    	ObjectNode on = m.createObjectNode();
    	
    	com.fasterxml.jackson.databind.jsonschema.JsonSchema schema = new com.fasterxml.jackson.databind.jsonschema.JsonSchema(on);
    	
    	ObjectSchema os = js.asObjectSchema();
    	
    	
//        System.out.println( m.writeValueAsString(js) );
//        
//        System.out.println( m.writeValueAsString(os) );
//        
//        System.out.println( m.writeValueAsString(schema) );
        
        
        m.setSerializationInclusion(Include.NON_EMPTY);
        
        Mapeado mape = new Mapeado();
        mape.setDescricao("Essa eh um mape");
        mape.getProperties().put("minValue", 0);
        
        System.out.println( m.writeValueAsString(mape) );
        
        mape = new Mapeado();
        mape.getProperties().put("minValue", 2);
        System.out.println( m.writeValueAsString(mape) );
    }
    
    
    static class Pessoa {
    	@JsonProperty(required=true,value="VALOW")
    	String nome;
    	Integer idade;
    	ArrayList<Telefone> tels = new ArrayList<App.Pessoa.Telefone>();
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
    
    public static class User {
        public enum Gender { MALE, FEMALE };

        public static class Name {
          private String _first, _last;

          public String getFirst() { return _first; }
          public String getLast() { return _last; }

          public void setFirst(String s) { _first = s; }
          public void setLast(String s) { _last = s; }
        }

        private Gender _gender;
        private Name _name;
        private boolean _isVerified;
        private byte[] _userImage;

        public Name getName() { return _name; }
        public boolean isVerified() { return _isVerified; }
        public Gender getGender() { return _gender; }
        public byte[] getUserImage() { return _userImage; }

        public void setName(Name n) { _name = n; }
        public void setVerified(boolean b) { _isVerified = b; }
        public void setGender(Gender g) { _gender = g; }
        public void setUserImage(byte[] b) { _userImage = b; }
    }
    
    static class Mapeado {
    	@JsonProperty
    	String descricao = null;
    	
    	HashMap<String, Object> properties = new HashMap<String, Object>();
		public String getDescricao() {
			return descricao;
		}
		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}
		public HashMap<String, Object> getProperties() {
			return properties;
		}
		public void setProperties(HashMap<String, Object> properties) {
			this.properties = properties;
		}
    	
    }
}
