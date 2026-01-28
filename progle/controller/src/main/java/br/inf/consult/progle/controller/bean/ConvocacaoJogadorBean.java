package br.inf.consult.progle.controller.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.enums.TipoJogoEnum;
import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.CadastroClubeDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.dto.ConvocacaoJogadorDTO;
import br.inf.consult.progle.model.service.ClubeService;
import br.inf.consult.progle.model.service.ConvocacaoJogadorService;
import br.inf.consult.progle.model.service.JogadorService;
import br.inf.consult.progle.util.FacesUtil;
import br.inf.consult.progle.util.IntegracaoUtil;
import br.inf.consult.progle.util.IntegracaoUtil.Cep;
import lombok.Getter;
import lombok.Setter;

@Controller("convocacaoJogadorBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class ConvocacaoJogadorBean {
	
	@Autowired
	private ConvocacaoJogadorService convocacaoJogadorService;

	@Autowired
	private JogadorService jogadorService;
	
	@Autowired 
	private ClubeService clubeService;

	private Long idConvocacao;
	private ConvocacaoJogadorDTO novaConvocacao;
	private String enderecoCompleto;
	private TipoJogoEnum tipoJogoEnum;
	private CadastroJogadorDTO jogadorDTO;
	private CadastroClubeDTO clubeDTO;
	private Long idJogador;
	private Long idClube;

	
	@PostConstruct
	public void init() {
		novaConvocacao = new ConvocacaoJogadorDTO();
	    novaConvocacao.setClubeDTO(new CadastroClubeDTO());
		this.novaConvocacao.setEnderecoDTO(new CadastroEnderecoDTO());

	}

	public void prosseguir() {
	    try {
	    	if (true)
	    		return;
	        if (idClube == null || idJogador == null) {
	            FacesUtil.registrarErro("IDs de Clube e Jogador devem ser informados!");
	            return;
	        }

	        Optional<CadastroClubeDTO> optionalClube = clubeService.buscarClubePorId(idClube);
	        Optional<CadastroJogadorDTO> optionalJogador = jogadorService.buscarJogadorPorId(idJogador);

	        if (optionalClube.isEmpty() || optionalJogador.isEmpty()) {
	            FacesUtil.registrarErro("Clube ou Jogador não encontrados!");
	            return;
	        }

	        if (!verificarDataPartida()) {
	            return;
	        }

	        if (convocacaoJogadorService.existeConvocacaoNaMesmaData(idJogador, novaConvocacao.getDataPartida())) {
	            FacesUtil.registrarErro("Já existe uma convocação para essa data.");
	            return;
	        }

	        novaConvocacao.setClubeDTO(optionalClube.get());
	        novaConvocacao.setJogadorDTO(optionalJogador.get());

	        convocacaoJogadorService.salvarConvocacao(novaConvocacao, idClube, idJogador);

	        FacesUtil.registrarMensagem("Convocação salva com sucesso!");
	    } catch (Exception e) {
	        FacesUtil.registrarErro("Erro ao salvar a convocação: " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	
	public boolean verificarDataPartida() {
	    if (novaConvocacao.getDataPartida() == null) {
	        return false;
	    }

	    LocalDate dataSelecionada = novaConvocacao.getDataPartida().toInstant()
	            .atZone(ZoneId.systemDefault())
	            .toLocalDate();

	    LocalDate hoje = LocalDate.now();

	    if (!dataSelecionada.isAfter(hoje)) { 
	        FacesUtil.registrarErro("msg.erro.data.invalida");
	        return false;
	    }

	    return true; 
	}



	public void buscarJogadorPorId() {
		if (idJogador != null) {
			Optional<CadastroJogadorDTO> optionalJogadorDTO = jogadorService.buscarJogadorPorId(idJogador);
			if (optionalJogadorDTO.isPresent()) {
				jogadorDTO = optionalJogadorDTO.get();
			} else {
				FacesUtil.registrarErro("msg.erro.buscar.jogador");
			}
		} else {
			FacesUtil.registrarErro("msg.erro.id.jogador.nao.informado");
		}
	}
	
	public void buscarConvocacaoJogadorPorId() {
		if (idJogador != null) {
			Optional<CadastroClubeDTO> optionalClubeDTO = clubeService.buscarClubePorId(idClube);
			Optional<CadastroJogadorDTO> optionalJogadorDTO = jogadorService.buscarJogadorPorId(idJogador);
				clubeDTO = optionalClubeDTO.get();
				jogadorDTO = optionalJogadorDTO.get();
			} else {
				FacesUtil.registrarErro("msg.erro.buscar.jogador");
			}
		
	}

	public void carregarEnderecoPorCep() {
		String cep = novaConvocacao.getEnderecoDTO().getCep();

		IntegracaoUtil cepUtil = new IntegracaoUtil();
		Optional<Cep> cepEndereco = cepUtil.searchByCep(cep);

		if (cepEndereco.isPresent()) {
			String estadoSigla = cepEndereco.get().getEstado().getSigla();

			if (!"SP".equalsIgnoreCase(estadoSigla)) {
				FacesUtil.registrarErro("msg.erro.cep.sp");
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
			FacesUtil.registrarErro("msg.erro.cep.nao.encontrado");
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
	
	public String getMsgConvocacao() {
		String data = "";
		if (novaConvocacao.getDataPartida() != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			data =  novaConvocacao.getDataPartida().toInstant().atZone(ZoneId.systemDefault()).format(formatter);
		}
		return "PROOGLE - Convocação do jogador " + jogadorDTO.getNome() + " pelo clube " + clubeDTO.getNome()
		+ (!data.isBlank() ? " no dia " + data + " para o jogo do tipo " : "")
		+ (novaConvocacao.getTipoJogo() != null ? novaConvocacao.getTipoJogo().toString() : "") ;
	}

}
