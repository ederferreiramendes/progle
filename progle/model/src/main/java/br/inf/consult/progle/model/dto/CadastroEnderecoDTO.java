package br.inf.consult.progle.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class CadastroEnderecoDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String estado;
	private String cidade;
	private String bairro;
	private String rua;
	private String numero;
	private String cep;
	private String complemento;

}
