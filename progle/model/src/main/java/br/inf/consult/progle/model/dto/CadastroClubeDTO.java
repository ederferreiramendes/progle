package br.inf.consult.progle.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class CadastroClubeDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	@CPF
    private String cpf;
	@CNPJ
	private String cnpj;
	private String responsavel;
	private String contato;
	private CadastroEnderecoDTO enderecoDTO;
	private String senha;
	@Email
	private String email;
	private String confirmaSenha;
	private String historico;
	private String fotoCaminho;
    private Long idClube;
    private UsuarioSistemaDTO usuarioSistemaDTO;


}
