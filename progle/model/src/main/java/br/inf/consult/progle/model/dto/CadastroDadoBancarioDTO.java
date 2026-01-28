package br.inf.consult.progle.model.dto;

import java.io.Serializable;

import br.inf.consult.enums.TipoChavePixEnum;
import br.inf.consult.progle.model.entity.Jogador;
import lombok.Data;

@Data
public class CadastroDadoBancarioDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private String banco;
	private String numeroConta;
	private String agencia;
	private String chavePix;
	private Jogador jogador; 
	private TipoChavePixEnum tipoChavePix;

}
