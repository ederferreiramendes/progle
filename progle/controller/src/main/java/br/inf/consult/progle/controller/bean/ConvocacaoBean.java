package br.inf.consult.progle.controller.bean;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.enums.TipoJogoEnum;
import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.dto.ConvocacaoDTO;
import br.inf.consult.progle.model.service.ConvocacaoService;
import br.inf.consult.progle.model.service.JogadorService;
import br.inf.consult.progle.util.FacesUtil;
import br.inf.consult.progle.util.IntegracaoUtil;
import br.inf.consult.progle.util.IntegracaoUtil.Cep;
import lombok.Getter;
import lombok.Setter;

@Controller("convocacaoBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class ConvocacaoBean {
	
	@Autowired
	private ConvocacaoService convocacaoService;

	@Autowired
	private JogadorService jogadorService;

	private Long idConvocacao;
	private ConvocacaoDTO novaConvocacao;
	private String enderecoCompleto;
	private TipoJogoEnum tipoJogoEnum;
	private CadastroJogadorDTO jogadorDTO;
	private Long idJogador;

	
	@PostConstruct
	public void init() {
		this.novaConvocacao = new ConvocacaoDTO();
		this.novaConvocacao.setEnderecoDTO(new CadastroEnderecoDTO());

	}

	public void prosseguir() {
		if (convocacaoService.existe(idConvocacao)) {
			FacesUtil.registrarErro("msg.erro.convocacao.existente");
		} else {
			idConvocacao = convocacaoService.salvarConvocacao(novaConvocacao);
			FacesUtil.registrarMensagem("Detalhes da partida salvo com sucesso!");		
			ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
			try {
				externalContext.redirect("confirmacaoPagamentoJogador.xhtml?faces-redirect=true&idConvocacao=" + idConvocacao + "&idJogador=" + idJogador );
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void buscarJogadorPorId() {
		if (idJogador != null) {
			Optional<CadastroJogadorDTO> optionalJogadorDTO = jogadorService.buscarJogadorPorId(idJogador);
			if (optionalJogadorDTO.isPresent()) {
				jogadorDTO = optionalJogadorDTO.get();
			} else {
				FacesUtil.registrarErro("Jogador não encontrado para o ID fornecido.");
			}
		} else {
			FacesUtil.registrarErro("ID do jogador não informado.");
		}
	}

	public void carregarEnderecoPorCep() {
		String cep = novaConvocacao.getEnderecoDTO().getCep();

		IntegracaoUtil cepUtil = new IntegracaoUtil();
		Optional<Cep> cepEndereco = cepUtil.searchByCep(cep);

		if (cepEndereco.isPresent()) {
			String estadoSigla = cepEndereco.get().getEstado().getSigla();

			if (!"SP".equalsIgnoreCase(estadoSigla)) {
				FacesUtil.registrarErro("Somente CEPs do estado de São Paulo são permitidos.");
				return;
			}

			novaConvocacao.getEnderecoDTO().setRua(cepEndereco.get().getLogradouro());
			novaConvocacao.getEnderecoDTO().setBairro(cepEndereco.get().getBairro());
			novaConvocacao.getEnderecoDTO().setCidade(cepEndereco.get().getCidade().getNome());
			novaConvocacao.getEnderecoDTO().setEstado(estadoSigla);

			enderecoCompleto = "Rua: " + cepEndereco.get().getLogradouro() + ", Bairro: "
					+ cepEndereco.get().getBairro() + ", Cidade: " + cepEndereco.get().getCidade().getNome()
					+ ", Estado: " + estadoSigla;
		} else {
			FacesUtil.registrarErro("CEP não encontrado.");
		}
	}

	public String gerarLinkGoogleMaps() {
		CadastroEnderecoDTO endereco = novaConvocacao.getEnderecoDTO();
		String rua = endereco.getRua();
		String numero = endereco.getNumero();
		String bairro = endereco.getBairro();

		try {
			String enderecoCompleto = rua + ", " + numero + ", " + bairro;
			String enderecoUrl = URLEncoder.encode(enderecoCompleto, "UTF-8");
			return "https://www.google.com/maps/search/?api=1&query=" + enderecoUrl;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "#";
		}
	}

	public List<TipoJogoEnum> getTipoJogoOptions() {
		return Arrays.asList(TipoJogoEnum.values());

	}

}
