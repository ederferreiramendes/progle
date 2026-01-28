package br.inf.consult.progle.controller.bean;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.enums.MetodoPagamentoEnum;
import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.dto.ConvocacaoDTO;
import br.inf.consult.progle.model.dto.HistoricoConvocacaoDTO;
import br.inf.consult.progle.model.dto.PagamentoDTO;
import br.inf.consult.progle.model.service.ConvocacaoService;
import br.inf.consult.progle.model.service.HistoricoConvocacaoService;
import br.inf.consult.progle.model.service.JogadorService;
import br.inf.consult.progle.model.service.PagamentoService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("pagamentoBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class PagamentoBean {

	@Autowired
	private ConvocacaoService convocacaoService;

	@Autowired
	private PagamentoService pagamentoService;

	@Autowired
	private JogadorService jogadorService;
	
	@Autowired
	private HistoricoConvocacaoService historicoConvocacaoService;


	private Long idPagamento;
	private Long idConvocacao;
	private Long idJogador;
	private PagamentoDTO pagamentoDTO;
	private CadastroJogadorDTO jogadorDTO;
	private ConvocacaoDTO convocacaoDTO;
	private String opcaoEscolhida;
	private List<HistoricoConvocacaoDTO> historicoJogadores;
	

	@PostConstruct
	public void init() {
		this.pagamentoDTO = new PagamentoDTO();
		this.opcaoEscolhida = "";
		this.historicoJogadores = new ArrayList<>();
	    carregarHistorico();
	}
	
	public void carregarHistorico() {
	    this.historicoJogadores = historicoConvocacaoService.buscarTodosHistoricos();	    
	}
	

	public void criar() {
	    if (pagamentoService.existe(idPagamento)) {
	        FacesUtil.registrarErro("msg.erro.efetuar.pagamento");
	    } else {
	        if (jogadorDTO != null) {
	            pagamentoDTO.setValor(jogadorDTO.getValorAluguel());
	            pagamentoDTO.setNomeJogador(jogadorDTO.getNome());

	            HistoricoConvocacaoDTO historicoDTO = new HistoricoConvocacaoDTO();
	            historicoDTO.setNomeJogador(jogadorDTO.getNome());
	            historicoDTO.setDataPartida(new Date());
	            historicoDTO.setValorContratacao(jogadorDTO.getValorAluguel());
	            historicoDTO.setLocalJogo(convocacaoDTO.getEnderecoDTO());
	            historicoDTO.setTipoJogo(convocacaoDTO.getTipoJogo());

	            historicoJogadores.add(historicoDTO);
	            historicoConvocacaoService.salvarHistorico(historicoDTO);
	        }
	        pagamentoDTO.setMetodo(MetodoPagamentoEnum.valueOf(opcaoEscolhida));
	        pagamentoService.salvar(pagamentoDTO);
	        FacesUtil.registrarMensagem("Operação realizada com sucesso");
	    }
	}

	public void buscarConvocacaoPorId() {
		if (idConvocacao != null) {
			Optional<ConvocacaoDTO> optionalConvocacaoDTO = convocacaoService.buscarConvocacaoPorId(idConvocacao);
			Optional<CadastroJogadorDTO> optionalJogadorDTO = jogadorService.buscarJogadorPorId(idJogador);
				convocacaoDTO = optionalConvocacaoDTO.get();
				jogadorDTO = optionalJogadorDTO.get();
			} else {
				FacesUtil.registrarErro("Convocação não encontrada para o ID fornecido.");
			}
		
	}
	
	public void uploadComprovante(org.primefaces.model.file.UploadedFile file) {
	    try {
	        if (file != null && file.getContent() != null) {
	            byte[] comprovanteBytes = file.getContent(); 
	            pagamentoDTO.setComprovante(comprovanteBytes); 
	            FacesUtil.registrarMensagem("Comprovante salvo com sucesso.");
	        } else {
	            FacesUtil.registrarErro("Falha no upload. O arquivo está vazio.");
	        }
	    } catch (Exception e) {
	        FacesUtil.registrarErro("Erro ao salvar o comprovante: " + e.getMessage());
	    }
	}

	public List<MetodoPagamentoEnum> getMetodoPagamentoOptions() {
		return Arrays.asList(MetodoPagamentoEnum.values());
	}

}
