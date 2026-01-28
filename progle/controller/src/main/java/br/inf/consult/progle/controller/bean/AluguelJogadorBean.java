package br.inf.consult.progle.controller.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.enums.ModalidadeEnum;
import br.inf.consult.enums.PosicaoCampoEnum;
import br.inf.consult.enums.PosicaoFutsalEnum;
import br.inf.consult.enums.PosicaoSocietyEnum;
import br.inf.consult.enums.SexoEnum;
import br.inf.consult.enums.TipoChavePixEnum;
import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.AluguelJogadorDTO;
import br.inf.consult.progle.model.dto.CadastroClubeDTO;
import br.inf.consult.progle.model.dto.CadastroDadoBancarioDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.service.ClubeService;
import br.inf.consult.progle.model.service.JogadorService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("aluguelJogadorBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class AluguelJogadorBean {
	
	@Autowired
	private JogadorService jogadorService;
	@Autowired
	private ClubeService clubeService;

	private Long idJogador;
	private Long idClube;
	private AluguelJogadorDTO novoAluguelJogador;
	private List<AluguelJogadorDTO> alugueisJogadores;
	private CadastroJogadorDTO jogadorDTO;
	private CadastroClubeDTO clubeDTO;
	private CadastroDadoBancarioDTO dadosBancarios;
	private String linkDetalhes;
	private String nomePesquisa;

	
	@PostConstruct
	public void init() {
		this.jogadorDTO = new CadastroJogadorDTO();

		this.novoAluguelJogador = new AluguelJogadorDTO();
		alugueisJogadores = new ArrayList<>();

		nomePesquisa = novoAluguelJogador.getNome();
		jogadorService.atualizarMediaDeTodosJogadores(); 
		alugueisJogadores = jogadorService.buscarJogadores(nomePesquisa);
		
	}
	

	public void buscar() {
		if (nomePesquisa == null || nomePesquisa.isEmpty()) {
			alugueisJogadores = jogadorService.buscarJogadores("");
		} else {
			alugueisJogadores = jogadorService.buscarJogadores(nomePesquisa);
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

	public void buscarClubePorId() {
		if (idClube == null) {
		    FacesUtil.registrarErro("ID do clube não foi informado.");
		    return;
		}
		if (idClube != null) {
			Optional<CadastroClubeDTO> optionalClubeDTO = clubeService.buscarClubePorId(idClube);
			clubeDTO = optionalClubeDTO.get();
		} else {
			FacesUtil.registrarErro("msg.erro.id.clube.nao.informado");
		}
	}
	


	public String gerarLinkGoogleMaps() {
		CadastroEnderecoDTO endereco = jogadorDTO.getEnderecoDTO();
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
	
	public void processarNovaAvaliacao(Long jogadorId) {
        jogadorService.atualizarMediaAvaliacao(jogadorId);
    }

	public String getLinkDetalhesId(Long idJogador) {
		return "/progle/detalhesJogador.xhtml?idJogador=" + idJogador;
	}

	public String redirecionarConvocacaoJogador(Long idJogador, Long idClube) {
		return "/convocacaoJogador.xhtml?faces-redirect=true&idJogador=" + idJogador + "&idClube=" +idClube;
	}

	public List<SexoEnum> getSexoOptions() {
		return Arrays.asList(SexoEnum.values());
	}

	public List<ModalidadeEnum> getModalidadeOptions() {
		return Arrays.asList(ModalidadeEnum.values());
	}

	public List<PosicaoCampoEnum> getPosicoesCampoOptions() {
		return Arrays.asList(PosicaoCampoEnum.values());
	}

	public List<PosicaoFutsalEnum> getPosicoesFutsalOptions() {
		return Arrays.asList(PosicaoFutsalEnum.values());
	}

	public List<PosicaoSocietyEnum> getPosicoesSocietyOptions() {
		return Arrays.asList(PosicaoSocietyEnum.values());
	}

	public List<TipoChavePixEnum> getTiposChavePix() {
		return Arrays.asList(TipoChavePixEnum.values());
	}
}
