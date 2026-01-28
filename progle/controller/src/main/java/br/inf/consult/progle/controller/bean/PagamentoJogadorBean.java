package br.inf.consult.progle.controller.bean;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.enums.MetodoPagamentoEnum;
import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.dto.ConvocacaoJogadorDTO;
import br.inf.consult.progle.model.dto.HistoricoConvocacaoJogadorDTO;
import br.inf.consult.progle.model.dto.PagamentoJogadorDTO;
import br.inf.consult.progle.model.service.ConvocacaoJogadorService;
import br.inf.consult.progle.model.service.HistoricoConvocacaoJogadorService;
import br.inf.consult.progle.model.service.JogadorService;
import br.inf.consult.progle.model.service.PagamentoJogadorService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("pagamentoJogadorBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter

public class PagamentoJogadorBean {

	@Autowired
	private ConvocacaoJogadorService convocacaoService;

	@Autowired
	private PagamentoJogadorService pagamentoJogadorService;

	@Autowired
	private JogadorService jogadorService;
	
	@Autowired
	private HistoricoConvocacaoJogadorService historicoConvocacaoJogadorService;


	private Long idPagamento;
	private Long idConvocacao;
	private Long idJogador;
	private PagamentoJogadorDTO pagamentoDTO;
	private CadastroJogadorDTO jogadorDTO;
	private ConvocacaoJogadorDTO convocacaoDTO;
	private String opcaoEscolhida;
	private List<HistoricoConvocacaoJogadorDTO> historicoJogadores;
	

	@PostConstruct
	public void init() {
		this.pagamentoDTO = new PagamentoJogadorDTO();
		this.opcaoEscolhida = "";
		this.historicoJogadores = new ArrayList<>();
	    carregarHistorico();
	}
	
	public void carregarHistorico() {
	    this.historicoJogadores = historicoConvocacaoJogadorService.buscarTodosHistoricos();	    
	}
	

	public void criar() {
	    if (pagamentoJogadorService.existe(idPagamento)) {
	        FacesUtil.registrarErro("msg.erro.efetuar.pagamento");
	    } else {
	        if (jogadorDTO != null) {
	            pagamentoDTO.setValor(jogadorDTO.getValorAluguel());
	            pagamentoDTO.setNomeJogador(jogadorDTO.getNome());
	        }
	        pagamentoDTO.setMetodo(MetodoPagamentoEnum.valueOf(opcaoEscolhida));
	        pagamentoJogadorService.salvar(pagamentoDTO);
	        FacesUtil.registrarMensagem("msg.app.operacao.realizada");
	    }
	}

	public void buscarConvocacaoPorId() {
		if (idConvocacao != null) {
			Optional<ConvocacaoJogadorDTO> optionalConvocacaoDTO = convocacaoService.buscarConvocacaoJogadorPorId(idConvocacao);
			Optional<CadastroJogadorDTO> optionalJogadorDTO = jogadorService.buscarJogadorPorId(idJogador);
				convocacaoDTO = optionalConvocacaoDTO.get();
				jogadorDTO = optionalJogadorDTO.get();
			} else {
				FacesUtil.registrarErro("msg.erro.id.convocacao");
			}
		
	}
	
	public void uploadComprovante(org.primefaces.model.file.UploadedFile file) {
	    try {
	        if (file != null && file.getContent() != null) {
	            byte[] comprovanteBytes = file.getContent(); 
	            pagamentoDTO.setComprovante(comprovanteBytes); 
	            FacesUtil.registrarMensagem("msg.app.upload.comprovante");
	        } else {
	            FacesUtil.registrarErro("msg.erro.upload.comprovate");
	        }
	    } catch (Exception e) {
	        FacesUtil.registrarErro("msg.erro.salvar.comprovante " + e.getMessage());
	    }
	}

	public List<MetodoPagamentoEnum> getMetodoPagamentoOptions() {
		return Arrays.asList(MetodoPagamentoEnum.values());
	}

}
