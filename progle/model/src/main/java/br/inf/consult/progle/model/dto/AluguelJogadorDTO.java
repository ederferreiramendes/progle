package br.inf.consult.progle.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AluguelJogadorDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private CadastroJogadorDTO jogadorDTO;
	private double valor;
	private String historico;
	private Double avaliacao;
	private String foto;
	private String cpf;
	private String nome;
	private Long idJogador;
	private String contato;
    private Integer raioAtuacao;
    
    public int getAvaliacaoInt() {
	    return (int) Math.floor(avaliacao != null ? avaliacao : 0);

	}
	
}
