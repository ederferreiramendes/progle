package br.inf.consult.progle.controller.bean;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.CadastroClubeDTO;
import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.dto.ConvocacaoJogadorDTO;
import br.inf.consult.progle.model.service.ClubeService;
import br.inf.consult.progle.model.service.ConvocacaoJogadorService;
import br.inf.consult.progle.model.service.JogadorService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("historicoJogadorBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class HistoricoJogadorBean {
	
	@Autowired
	private ConvocacaoJogadorService convocacaoJogadorService;
	
	@Autowired 
	private ClubeService clubeService;
	
	@Autowired
	private JogadorService jogadorService;


	private List<ConvocacaoJogadorDTO> historicoJogadores;
	private Long idClube;
	private CadastroClubeDTO clubeDTO;
	private Long idJogador;
	private CadastroJogadorDTO jogadorDTO;
	private ConvocacaoJogadorDTO convocacaoJogadorDTO;
	private Date dataDaConvocacao;
	
	@PostConstruct
	public void init() {
		if (convocacaoJogadorService == null) {
			throw new IllegalStateException("convocacaoJogadorService is not injected!");
		}
	}
	
	public void buscarClubePorId() {
	    if (idClube == null) {
	        FacesUtil.registrarErro("msg.erro.id.clube");
	        return;
	    }
	    Optional<CadastroClubeDTO> optionalClubeDTO = clubeService.buscarClubePorId(idClube);
	    if (optionalClubeDTO.isPresent()) {
	        clubeDTO = optionalClubeDTO.get();
	        historicoJogadores = convocacaoJogadorService.buscarTodosHistoricos(idClube);
	    } else {
	        FacesUtil.registrarErro("msg.erro.clube.nao.encontrado");
	    }    
	    
	}
	
	public void buscarJogadorPorId() {
	    if (idJogador == null) {
	        FacesUtil.registrarErro("msg.erro.id.jogador");
	        return;
	    }
	    Optional<CadastroJogadorDTO> optionalJogadorDTO = jogadorService.buscarJogadorPorId(idJogador);
	    if (optionalJogadorDTO.isPresent()) {
	        jogadorDTO = optionalJogadorDTO.get();
	        historicoJogadores = convocacaoJogadorService.buscarTodosHistoricosJogador(idJogador);
	    } else {
	        FacesUtil.registrarErro("msg.erro.jogador.nao.encontrado");
	    }    
	    
	}
	
	public String formatarDataConvocacao(LocalDateTime data) {
	    if (data != null) {
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	        return data.format(formatter);
	    }
	    return "";
	}
	
	public void salvarAvaliacao(Long idConvocacao, int avaliacao) {
	    try {
	        convocacaoJogadorService.salvarAvaliacaoConvocacao(idConvocacao, avaliacao);
	        FacesUtil.registrarMensagem("Avaliação salva com sucesso!");
	    } catch (Exception e) {
	        FacesUtil.registrarErro(e.getMessage());
	    }
	}
}
