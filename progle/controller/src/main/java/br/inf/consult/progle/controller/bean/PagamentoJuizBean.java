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
import br.inf.consult.progle.model.dto.CadastroJuizDTO;
import br.inf.consult.progle.model.dto.ConvocacaoJuizDTO;
import br.inf.consult.progle.model.dto.HistoricoConvocacaoJuizDTO;
import br.inf.consult.progle.model.dto.PagamentoJuizDTO;
import br.inf.consult.progle.model.service.ConvocacaoJuizService;
import br.inf.consult.progle.model.service.HistoricoConvocacaoJuizService;
import br.inf.consult.progle.model.service.JuizService;
import br.inf.consult.progle.model.service.PagamentoJuizService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("pagamentoJuizBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter

public class PagamentoJuizBean {

	@Autowired
	private ConvocacaoJuizService convocacaoJuizService;

	@Autowired
	private PagamentoJuizService pagamentoJuizService;

	@Autowired
	private JuizService juizService;

	@Autowired
	private HistoricoConvocacaoJuizService historicoConvocacaoJuizService;

	private Long idPagamento;
	private Long idConvocacaoJuiz;
	private Long idJuiz;
	private PagamentoJuizDTO pagamentoJuizDTO;
	private CadastroJuizDTO juizDTO;
	private ConvocacaoJuizDTO convocacaoJuizDTO;
	private String opcaoEscolhida;
	private List<HistoricoConvocacaoJuizDTO> historicoJuizes;

	@PostConstruct
	public void init() {
		this.pagamentoJuizDTO = new PagamentoJuizDTO();
		this.opcaoEscolhida = "";
		this.historicoJuizes = new ArrayList<>();
		carregarHistorico();
	}

	public void carregarHistorico() {
		this.historicoJuizes = historicoConvocacaoJuizService.buscarTodosHistoricos();
	}

	public void criar() {
		if (pagamentoJuizService.existe(idPagamento)) {
			FacesUtil.registrarErro("msg.erro.efetuar.pagamento");
		} else {
			if (juizDTO != null) {
				pagamentoJuizDTO.setValor(juizDTO.getValorAluguel());
				pagamentoJuizDTO.setNomeJuiz(juizDTO.getNome());
			}
			pagamentoJuizDTO.setMetodo(MetodoPagamentoEnum.valueOf(opcaoEscolhida));
			pagamentoJuizService.salvar(pagamentoJuizDTO);
			FacesUtil.registrarMensagem("msg.app.operacao.realizada");
		}
	}

	public void buscarConvocacaoPorId() {
		if (idConvocacaoJuiz != null) {
			Optional<ConvocacaoJuizDTO> optionalConvocacaoJuizDTO = convocacaoJuizService.buscarConvocacaoJuizPorId(idConvocacaoJuiz);
			Optional<CadastroJuizDTO> optionalJuizDTO = juizService.buscarJuizPorId(idJuiz);
			convocacaoJuizDTO = optionalConvocacaoJuizDTO.get();
			juizDTO = optionalJuizDTO.get();
		} else {
			FacesUtil.registrarErro("msg.erro.id.convocacao");
		}

	}

	public void uploadComprovante(org.primefaces.model.file.UploadedFile file) {
		try {
			if (file != null && file.getContent() != null) {
				byte[] comprovanteBytes = file.getContent();
				pagamentoJuizDTO.setComprovante(comprovanteBytes);
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
