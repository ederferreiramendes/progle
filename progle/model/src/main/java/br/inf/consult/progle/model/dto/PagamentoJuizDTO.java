package br.inf.consult.progle.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import br.inf.consult.enums.MetodoPagamentoEnum;
import lombok.Data;

@Data

public class PagamentoJuizDTO implements Serializable{

	private static final long serialVersionUID = 1L;

    private Double valor;
    private LocalDateTime dataPagamento;
    private MetodoPagamentoEnum metodo;
    private byte[] comprovante;
    private String nomeJuiz;

}
