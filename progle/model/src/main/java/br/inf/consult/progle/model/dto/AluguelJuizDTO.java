package br.inf.consult.progle.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AluguelJuizDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private CadastroJuizDTO juizDTO;
	private double valor;
	private String historico;
	private Double avaliacao;
	private String foto;
	private String cpf;
	private String nome;
	private Long idJuiz;
	private String contato;
    private Integer raioAtuacao;

    public int getAvaliacaoInt() {
	    return (int) Math.floor(avaliacao != null ? avaliacao : 0);

	}
}
