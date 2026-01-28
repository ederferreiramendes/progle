package br.inf.consult.progle.model.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import br.inf.consult.enums.PosicaoCampoEnum;
import br.inf.consult.enums.PosicaoFutsalEnum;
import br.inf.consult.enums.PosicaoSocietyEnum;
import br.inf.consult.enums.TipoJogoEnum;
import lombok.Data;

@Data
public class HistoricoConvocacaoJogadorDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String nomeJogador;
	
	private Date dataPartida;
	
	private Double valorContratacao;
	
	private CadastroEnderecoDTO localJogo;
	
	private TipoJogoEnum tipoJogo;
	
	private PosicaoCampoEnum[] posicoesCampo = new PosicaoCampoEnum[11];
	
    private PosicaoFutsalEnum[] posicoesFutsal = new PosicaoFutsalEnum[5];
    
    private PosicaoSocietyEnum[] posicoesSociety = new PosicaoSocietyEnum[6];
    
    private String foto;
	
    public String getPosicoesCampoFormatadas() {
        if (posicoesCampo == null || posicoesCampo.length == 0) {
            return "";
        }
        return Arrays.stream(posicoesCampo)
                     .map(PosicaoCampoEnum::name)
                     .reduce((a, b) -> a + ", " + b)
                     .orElse("");
    }
 
 public String getPosicoesFutsalFormatadas() {
	    if (posicoesFutsal == null || posicoesFutsal.length == 0) {
	        return "";
	    }
	    return Arrays.stream(posicoesFutsal)
	                 .map(PosicaoFutsalEnum::name)
	                 .reduce((a, b) -> a + ", " + b)
	                 .orElse("");
	}

	public String getPosicoesSocietyFormatadas() {
	    if (posicoesSociety == null || posicoesSociety.length == 0) {
	        return "";
	    }
	    return Arrays.stream(posicoesSociety)
	                 .map(PosicaoSocietyEnum::name)
	                 .reduce((a, b) -> a + ", " + b)
	                 .orElse("");
	}

	public String getDataPartidaData() {
	    if (dataPartida != null) {
	        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
	        return sdf.format(dataPartida);
	    }
	    return "";
	}

	public String getDataPartidaHora() {
	    if (dataPartida != null) {
	        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	        return sdf.format(dataPartida);
	    }
	    return "";
	}
	
	public String getLocalJogoFormatado() {
	    if (localJogo != null) {
	        StringBuilder enderecoFormatado = new StringBuilder();
	        if (localJogo.getRua() != null) {
	            enderecoFormatado.append(localJogo.getRua());
	        }
	        if (localJogo.getNumero() != null) {
	            enderecoFormatado.append(", ").append(localJogo.getNumero());
	        }
	        if (localJogo.getBairro() != null) {
	            enderecoFormatado.append(" - ").append(localJogo.getBairro());
	        }
	        if (localJogo.getCidade() != null) {
	            enderecoFormatado.append(", ").append(localJogo.getCidade());
	        }
	        if (localJogo.getEstado() != null) {
	            enderecoFormatado.append(" - ").append(localJogo.getEstado());
	        }
	        return enderecoFormatado.toString();
	    }
	    return "";
	}


}
