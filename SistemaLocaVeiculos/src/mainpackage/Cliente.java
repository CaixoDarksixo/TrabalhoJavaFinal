package mainpackage;

import java.io.Serializable;

public class Cliente implements Serializable {
    private static final long serialVersionUID = 1L;
	private String nome, sobrenome, RG, CPF, Endereco;
	
	public Cliente(String nome, String sobrenome, String RG, String CPF, String Endereco) {
	    this.nome = nome;
	    this.sobrenome = sobrenome;
	    this.RG = RG;
	    this.CPF = CPF;
	    this.Endereco = Endereco;
	}
	
	public Cliente() {
	    this("", "", "", "", "");
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getRG() {
		return RG;
	}

	public void setRG(String rG) {
		RG = rG;
	}

	public String getCPF() {
		return CPF;
	}

	public void setCPF(String cPF) {
		CPF = cPF;
	}

	public String getEndereco() {
		return Endereco;
	}

	public void setEndereco(String endereco) {
		Endereco = endereco;
	}
}
