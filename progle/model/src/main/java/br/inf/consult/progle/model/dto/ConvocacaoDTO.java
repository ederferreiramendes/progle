package br.inf.consult.progle.model.dto;

import java.io.Serializable;
import java.util.Date;

import br.inf.consult.enums.TipoJogoEnum;
import lombok.Data;

@Data
public class ConvocacaoDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Date dataPartida;

    private CadastroEnderecoDTO enderecoDTO;
    
	private TipoJogoEnum tipoJogo;
	
	private CadastroClubeDTO clubeDTO;
	
	private CadastroJogadorDTO jogadorDTO;
	
	private CadastroJuizDTO juizDTO;
 

}
