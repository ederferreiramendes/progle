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
import br.inf.consult.progle.model.dto.CadastroJuizDTO;
import br.inf.consult.progle.model.dto.ConvocacaoJuizDTO;
import br.inf.consult.progle.model.service.ClubeService;
import br.inf.consult.progle.model.service.ConvocacaoJuizService;
import br.inf.consult.progle.model.service.JuizService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("historicoJuizBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class HistoricoJuizBean {
	
	@Autowired
	private ConvocacaoJuizService convocacaoJuizService;
	
	@Autowired 
	private ClubeService clubeService;
	
	@Autowired
	private JuizService juizService;


	private List<ConvocacaoJuizDTO> historicoJuizes;
	private Long idClube;
	private CadastroClubeDTO clubeDTO;
	private Long idJuiz;
	private CadastroJuizDTO juizDTO;
	private ConvocacaoJuizDTO convocacaoJuizDTO;
	private Date dataDaConvocacao;


	@PostConstruct
	public void init() {
		if (convocacaoJuizService == null) {
			throw new IllegalStateException("convocacaoJuizService is not injected!");
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
	        historicoJuizes = convocacaoJuizService.buscarTodosHistoricos(idClube);
	    } else {
	        FacesUtil.registrarErro("msg.erro.clube.nao.encontrado");
	    }    
	    
	}
	
	
	
	public void buscarJuizPorId() {
	    if (idJuiz == null) {
	        FacesUtil.registrarErro("msg.erro.id.juiz");
	        return;
	    }
	    Optional<CadastroJuizDTO> optionalJuizDTO = juizService.buscarJuizPorId(idJuiz);
	    if (optionalJuizDTO.isPresent()) {
	        juizDTO = optionalJuizDTO.get();
	        historicoJuizes = convocacaoJuizService.buscarTodosHistoricosJuiz(idJuiz);
	    } else {
	        FacesUtil.registrarErro("msg.erro.juiz.nao.encontrado");
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
	        convocacaoJuizService.salvarAvaliacaoConvocacao(idConvocacao, avaliacao);
	        FacesUtil.registrarMensagem("Avaliação salva com sucesso!");
	    } catch (Exception e) {
	        FacesUtil.registrarErro(e.getMessage());
	    }
	}


}
