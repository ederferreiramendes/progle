package br.inf.consult.progle.controller.bean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.enums.ModalidadeEnum;
import br.inf.consult.enums.TipoChavePixEnum;
import br.inf.consult.enums.TipoJuizEnum;
import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.AluguelJuizDTO;
import br.inf.consult.progle.model.dto.CadastroClubeDTO;
import br.inf.consult.progle.model.dto.CadastroDadoBancarioDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.CadastroJuizDTO;
import br.inf.consult.progle.model.service.ClubeService;
import br.inf.consult.progle.model.service.JuizService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("aluguelJuizBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class AluguelJuizBean {

	@Autowired
	private JuizService juizService;
	@Autowired
	private ClubeService clubeService;

	private Long idJuiz;
	private Long idClube;
	private AluguelJuizDTO novoAluguelJuiz;
	private List<AluguelJuizDTO> alugueisJuizes;
	private CadastroJuizDTO juizDTO;
	private CadastroClubeDTO clubeDTO;
	private CadastroDadoBancarioDTO dadosBancarios;
	private String linkDetalhes;
	private String nomePesquisa;

	@PostConstruct
	public void init() {
	    this.juizDTO = new CadastroJuizDTO();
	    this.novoAluguelJuiz = new AluguelJuizDTO();
	    alugueisJuizes = juizService.buscarJuizes(nomePesquisa);
	}

	public void buscar() {
		if (nomePesquisa == null || nomePesquisa.isEmpty()) {
			alugueisJuizes = juizService.buscarJuizes("");
		} else {
			alugueisJuizes = juizService.buscarJuizes(nomePesquisa);
		}
	}


	public void buscarJuizPorId() {
		if (idJuiz != null) {
			Optional<CadastroJuizDTO> optionalJuizDTO = juizService.buscarJuizPorId(idJuiz);
			if (optionalJuizDTO.isPresent()) {
				juizDTO = optionalJuizDTO.get();
			} else {
				FacesUtil.registrarErro("Juiz não encontrado para o ID fornecido.");
			}
		} else {
			FacesUtil.registrarErro("ID do juiz não informado.");
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
		CadastroEnderecoDTO endereco = juizDTO.getEnderecoDTO();
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
	
	public void processarNovaAvaliacao(Long juizId) {
        juizService.atualizarMediaAvaliacao(juizId);
    }

	public String getLinkDetalhesId(Long idJuiz) {
		return "/progle/detalhesJuiz.xhtml?idJuiz=" + idJuiz;
	}

	public String redirecionarConvocacaoJuiz(Long idJuiz, Long idClube) {
		return "/convocacaoJuiz.xhtml?faces-redirect=true&idJuiz=" + idJuiz + "&idClube=" + idClube;
	}

	public List<ModalidadeEnum> getModalidadeOptions() {
		return Arrays.asList(ModalidadeEnum.values());
	}

	public List<TipoChavePixEnum> getTiposChavePix() {
		return Arrays.asList(TipoChavePixEnum.values());
	}

	public List<TipoJuizEnum> getTipoJuizOptions() {
		return Arrays.asList(TipoJuizEnum.values());
	}
}
