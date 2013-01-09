package org.reinert.jsonschema.tests.model;

import org.reinert.jsonschema.Media;
import org.reinert.jsonschema.SchemaProperty;


public class Empresa {

	@SchemaProperty(required=true, title="ID", minimum=10, maximun=50)
	private Short id;
	@SchemaProperty(required=true, description="O nome da empresa.", title="EMPRESA")
	private String nome;
	//private Collection<Funcionario> funcionarios;
	@SchemaProperty(title="FOTO", description="A foto da fachada empresa.")
	@Media(type="image/jpg", binaryEncoding="base64")
	private Byte[] foto;
	

	public Empresa(){}
	
	public Empresa(Short id, String nome) {
		setId(id);
		this.nome = nome;
	}
	
	public Byte[] getFoto() {
		return foto;
	}

	public void setFoto(Byte[] foto) {
		this.foto = foto;
	}

	public Short getId() {
		return this.id;
	}

	public String getNome() {
		return this.nome;
	}

	public void setId(Short id) {
		this.id = id;
//		this.selfLink = new AtomLink("self", defaultType, "empresas/" + id);
//		addLink(new AtomLink("funcionarios", defaultType, getSelfLink().getHref() + "/funcionarios"));
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

//	public void addFuncionario(Funcionario funcionario) {
//		getFuncionarios().add(funcionario);
//	}
//	
//	public void addFuncionarios(Collection<Funcionario> funcionarios) {
//		getFuncionarios().addAll(funcionarios);
//	}
//	
//	public int qtdFuncionarios() {
//		return getFuncionarios().size();
//	}
//	
//	public Collection<Funcionario> getFuncionarios() {
////		if (funcionarios == null)
////			funcionarios = new LinkedHashSet<Funcionario>();
//		return funcionarios;
//	}
//	
//	public void setFuncionarios(Collection<Funcionario> funcionarios) {
//		this.funcionarios = funcionarios;
//	}
//	
//	public void initFuncionarios() {
//		funcionarios = new LinkedHashSet<Funcionario>();
//	}

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Empresa other = (Empresa) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		if (id != null)
			builder.append("id:").append(id).append(", ");
		if (nome != null)
			builder.append("nome:").append(nome);
		builder.append("}");
		return builder.toString();
	}

}
