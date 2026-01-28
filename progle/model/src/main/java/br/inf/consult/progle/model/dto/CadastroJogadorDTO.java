package br.inf.consult.progle.model.dto;


import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

import javax.validation.constraints.Email;

import org.hibernate.validator.constraints.br.CPF;

import br.inf.consult.enums.ModalidadeEnum;
import br.inf.consult.enums.NivelEnum;
import br.inf.consult.enums.PosicaoCampoEnum;
import br.inf.consult.enums.PosicaoFutsalEnum;
import br.inf.consult.enums.PosicaoSocietyEnum;
import br.inf.consult.enums.SexoEnum;
import lombok.Data;

@Data
public class CadastroJogadorDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
    private String nome;
    private String apelido;
    @CPF
    private String cpf;
    private String senha;
    private String confirmaSenha;
    @Email
    private String email;
    private SexoEnum sexo;
    private PosicaoCampoEnum[] posicoesCampo = new PosicaoCampoEnum[11];
    private PosicaoFutsalEnum[] posicoesFutsal = new PosicaoFutsalEnum[5];
    private PosicaoSocietyEnum[] posicoesSociety = new PosicaoSocietyEnum[6];    
    private Integer raioAtuacao;
    private ModalidadeEnum[] modalidade = new ModalidadeEnum[3];
    private Date dataNascimento;
    private NivelEnum nivel;
    private String contato;
    private String instagram;
    private CadastroEnderecoDTO enderecoDTO;
    private CadastroDadoBancarioDTO dadosBancariosDTO;
	private double valorAluguel;
	private String fotoCaminho;
	private Double avaliacao;
	private String historico;
	private Long idJogador;
	private UsuarioSistemaDTO usuarioSistemaDTO;
	

	
	
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
		
		public int getAvaliacaoInt() {
		    return (int) Math.floor(avaliacao != null ? avaliacao : 0);
		}


}
