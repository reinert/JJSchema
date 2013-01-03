package org.reinert.jsonschema.tests.model;


public class Funcionario {

	private String chave;
	private Integer matricula;
	private String nome;
	private Integer ramal;
	private Integer telefoneParticular;
	private Integer telefoneEmpresa;
	private String email;
	private String cargo;
	private String lotacao;
	private String atividade;
//	private Empresa empresa;

	public Funcionario() {}
	
	public Funcionario(Integer matricula, String nome) {
		setMatricula(matricula);
		this.nome = nome;
	}

	public String getChave() {
		return this.chave;
	}

	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public Integer getMatricula() {
		return this.matricula;
	}

	public String getNome() {
		return this.nome;
	}

	public Integer getRamal() {
		return this.ramal;
	}

	public void setRamal(Integer ramal) {
		this.ramal = ramal;
	}

	public Integer getTelefoneParticular() {
		return this.telefoneParticular;
	}

	public void setTelefoneParticular(Integer telefoneParticular) {
		this.telefoneParticular = telefoneParticular;
	}

	public Integer getTelefoneEmpresa() {
		return this.telefoneEmpresa;
	}

	public void setTelefoneEmpresa(Integer telefoneEmpresa) {
		this.telefoneEmpresa = telefoneEmpresa;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCargo() {
		return this.cargo;
	}

	public void setCargo(String cargo) {
		this.cargo = cargo;
	}

	public String getLotacao() {
		return this.lotacao;
	}

	public void setLotacao(String lotacao) {
		this.lotacao = lotacao;
	}

	public String getAtividade() {
		return this.atividade;
	}

	public void setAtividade(String atividade) {
		this.atividade = atividade;
	}

//	public Short getIdEmpresa() {
//		return empresa == null ? null : empresa.getId();
//	}
//
//	public void setIdEmpresa(Short idEmpresa) {
//		empresa = new Empresa(idEmpresa, null);
//	}
//
//	public Empresa getEmpresa() {
//		return empresa;
//	}
//
//	public void setEmpresa(Empresa empresa) {
//		this.empresa = empresa;
//	}

	// public String getNomeEmpresa() {
	// return empresa == null ? null : empresa.getNome();
	// }
	//
	// public void setNomeEmpresa(String nomeEmpresa) {
	// if (empresa != null) {
	// empresa.setNome(nomeEmpresa);
	// } else {
	// empresa = new Empresa();
	// empresa.setNome(nomeEmpresa);
	// }
	// }

	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((matricula == null) ? 0 : matricula.hashCode());
		return result;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Funcionario other = (Funcionario) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}

	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("{");
		if (chave != null)
			builder.append("chave:").append(chave).append(", ");
		if (matricula != null)
			builder.append("matricula:").append(matricula).append(", ");
		if (nome != null)
			builder.append("nome:").append(nome).append(", ");
		if (ramal != null)
			builder.append("ramal:").append(ramal).append(", ");
		if (telefoneParticular != null)
			builder.append("telefoneParticular:").append(telefoneParticular)
					.append(", ");
		if (telefoneEmpresa != null)
			builder.append("telefoneEmpresa:").append(telefoneEmpresa)
					.append(", ");
		if (email != null)
			builder.append("email:").append(email).append(", ");
		if (cargo != null)
			builder.append("cargo:").append(cargo).append(", ");
		if (lotacao != null)
			builder.append("lotacao:").append(lotacao).append(", ");
		if (atividade != null)
			builder.append("atividade:").append(atividade).append(", ");
		builder.append("}");
		return builder.toString();
	}

}
