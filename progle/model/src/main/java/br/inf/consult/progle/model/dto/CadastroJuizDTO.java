package br.inf.consult.progle.model.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

import br.inf.consult.enums.ModalidadeEnum;
import br.inf.consult.enums.NivelEnum;
import br.inf.consult.enums.TipoJuizEnum;
import lombok.Data;

@Data
public class CadastroJuizDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String nome;
	@CPF
	private String cpf;
	private String contato;
    private String instagram;
	private CadastroEnderecoDTO enderecoDTO;
	private String senha;
	@Email
	private String email;
	private String confirmaSenha;
	private String historico;
	private String fotoCaminho;
	private Integer raioAtuacao;
	private NivelEnum nivel;
	private ModalidadeEnum[] modalidade = new ModalidadeEnum[3];
	private TipoJuizEnum [] tipoJuizEnum = new TipoJuizEnum[2];
	private CadastroDadoBancarioDTO dadosBancariosDTO;
	private double valorAluguel;
	private Double avaliacao;
	private UsuarioSistemaDTO usuarioSistemaDTO;
	private Long idJuiz;
	private String tipoJuiz;

	public int getAvaliacaoInt() {
	    return (int) Math.floor(avaliacao != null ? avaliacao : 0);
	}

	
	
}
