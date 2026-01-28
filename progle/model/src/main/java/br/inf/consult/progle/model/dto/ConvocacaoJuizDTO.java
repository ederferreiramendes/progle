package br.inf.consult.progle.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import br.inf.consult.enums.TipoJogoEnum;
import br.inf.consult.enums.TipoJuizEnum;
import lombok.Data;

@Data
public class ConvocacaoJuizDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private Date dataPartida;
	
	private LocalDateTime dataDaConvocacao;

    private CadastroEnderecoDTO enderecoDTO;
    
    private String enderecoCompleto;
    
	private TipoJogoEnum tipoJogo;
	
	private Double valor;
	
	private CadastroClubeDTO clubeDTO;
	
	private CadastroJuizDTO juizDTO;
	
	private String nomeJuiz;
	
	private String nomeClube;
	
	private Long idClube;
	
	private List<TipoJuizEnum> tipoJuizEnum;
	
	private int avaliacao;
	
	private Long idJuiz;

	
	public ConvocacaoJuizDTO() {
	    this.clubeDTO = new CadastroClubeDTO();
	}
	

}
