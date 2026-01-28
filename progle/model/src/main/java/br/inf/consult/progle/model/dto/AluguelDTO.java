package br.inf.consult.progle.model.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class AluguelDTO implements Serializable{

	private static final long serialVersionUID = 1L;

    private int avaliacao;
    private String foto;
    private String cpf;
    private double valor;
    private String historico;
    private String status; 
    private CadastroJogadorDTO jogadorDTO;
    
}
