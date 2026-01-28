package br.inf.consult.progle.model.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;

@Data
public class VisualizaJogadorDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    private String nome;
    private String apelido;
    @CPF
    private String cpf;
    private String senha;
    private String confirmaSenha;
    private String sexo;
    private String posicoesCampo;
    private String posicoesFutsal;
    private String posicoesSociety; 
    private String raioAtuacao;
    private String modalidade;
    private String dataNascimento;
    private String nivel;
    private String contato;
    private String enderecoDTO;
    private String dadosBancariosDTO;
	private String valorAluguel;
	private String foto;
	private String avaliacao;

}
