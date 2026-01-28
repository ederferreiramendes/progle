package br.inf.consult.progle.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

import br.inf.consult.enums.PosicaoCampoEnum;
import br.inf.consult.enums.PosicaoFutsalEnum;
import br.inf.consult.enums.PosicaoSocietyEnum;
import br.inf.consult.enums.TipoJogoEnum;
import lombok.Data;

@Data
public class ConvocacaoJogadorDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
    private Long id; 

	private Date dataPartida;
	
	private LocalDateTime dataDaConvocacao;

    private CadastroEnderecoDTO enderecoDTO;
    
    private String enderecoCompleto;
    
	private TipoJogoEnum tipoJogo;
	
	private Double valor;
	
	private CadastroClubeDTO clubeDTO;
	
	private CadastroJogadorDTO jogadorDTO;
	
	private String nomeJogador;
	
	private String nomeClube;
	
	private Long idClube;
	
	private List<PosicaoCampoEnum> posicaoCampo;
    private List<PosicaoFutsalEnum> posicaoFutsal;
    private List<PosicaoSocietyEnum> posicoesSociety;
	
	public ConvocacaoJogadorDTO() {
	    this.clubeDTO = new CadastroClubeDTO();
	}
	
	private int avaliacao;
	
	private Long idJogador;
	


}
