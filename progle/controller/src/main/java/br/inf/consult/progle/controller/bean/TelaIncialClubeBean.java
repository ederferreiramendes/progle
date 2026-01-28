package br.inf.consult.progle.controller.bean;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.progle.model.dto.CadastroClubeDTO;
import br.inf.consult.progle.model.dto.UsuarioSistemaDTO;
import br.inf.consult.progle.model.service.ClubeService;
import br.inf.consult.progle.model.service.JogadorService;
import br.inf.consult.progle.model.service.JuizService;
import br.inf.consult.progle.model.service.UsuarioSistemaService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("telaInicialClubeBean")
@Scope("session")
@Getter
@Setter
public class TelaIncialClubeBean {

	@Autowired
	private ClubeService clubeService;
	@Autowired
	private UsuarioSistemaService usuarioSistemaService;
	@Autowired
	private JogadorService jogadorService;
	@Autowired
	private JuizService juizService;

	private Long idClube;
	private Long idLogin;
	private CadastroClubeDTO clubeDTO;
	private UsuarioSistemaDTO usuarioSistemaDTO;

	public void buscarClubeLoginPorId() {
		if (idClube == null) {
			FacesUtil.registrarErro("msg.erro.id.nulo");
			return;
		}

		if (idLogin == null) {
			FacesUtil.registrarErro("msg.erro.id.login.nulo");
			return;
		}

		if (idClube != null) {
			Optional<CadastroClubeDTO> optionalClubeDTO = clubeService.buscarClubePorId(idClube);
			Optional<UsuarioSistemaDTO> optionalUsuarioSistemaDTO = usuarioSistemaService.buscarLoginPorId(idLogin);
			clubeDTO = optionalClubeDTO.get();
			usuarioSistemaDTO = optionalUsuarioSistemaDTO.get();
		} else {
			FacesUtil.registrarErro("msg.erro.id.usuario");
		}
	}

	public String redirecionarAluguelJogador(Long idClube) {
		jogadorService.atualizarMediaDeTodosJogadores();
		return "/aluguelJogador.xhtml?faces-redirect=true&idClube=" + idClube;
	}

	public String redirecionarAluguelJuiz(Long idClube) {
		juizService.atualizarMediaDeTodosJuizes(); 
	    return "/aluguelJuiz.xhtml?faces-redirect=true&idClube=" + idClube;
	}

	public String redirecionarHistoricoJogadores(Long idClube) {
		return "/historicoConvocacaoJogador.xhtml?faces-redirect=true&idClube=" + idClube;
	}

	public String redirecionarHistoricoJuizes(Long idClube) {
		return "/historicoConvocacaoJuiz.xhtml?faces-redirect=true&idClube=" + idClube;
	}

	public String redirecionarEditarSenhaClube(Long idLogin) {
		return "/editarSenha.xhtml?faces-redirect=true&idLogin=" + idLogin;
	}

	public String redirecionarEditarClube(Long idClube) {
		return "/editarClube.xhtml?faces-redirect=true&idClube=" + idClube;
	}

	public String redirecionarDuvidasFrequentesClube(Long idClube) {
		return "/duvidasFrequentesClube.xhtml?faces-redirect=true&idClube=" + idClube;
	}

}
