package br.inf.consult.progle.controller.bean;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.dto.UsuarioSistemaDTO;
import br.inf.consult.progle.model.service.JogadorService;
import br.inf.consult.progle.model.service.UsuarioSistemaService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("telaInicialJogadorBean")
@Scope("session")
@Getter
@Setter
public class TelaIncialJogadorBean {
	
	@Autowired
	private JogadorService jogadorService;
	@Autowired
	private UsuarioSistemaService usuarioSistemaService;
	
	private Long idJogador;
	private CadastroJogadorDTO jogadorDTO;
	private Long idLogin;
	private UsuarioSistemaDTO usuarioSistemaDTO;
	
	public void buscarJogadorLoginPorId() {
		if (idJogador == null) {
		    FacesUtil.registrarErro("msg.erro.id.nulo");
		    return;
		}
		
		if (idLogin == null) {
		    FacesUtil.registrarErro("msg.erro.id.login.nulo");
		    return;
		}
		
		if (idJogador != null) {
			Optional<CadastroJogadorDTO> optionalJogadorDTO = jogadorService.buscarJogadorPorId(idJogador);
			Optional<UsuarioSistemaDTO> optionalUsuarioSistemaDTO = usuarioSistemaService.buscarLoginPorId(idLogin);
			jogadorDTO = optionalJogadorDTO.get();
			usuarioSistemaDTO = optionalUsuarioSistemaDTO.get();
		} else {
			FacesUtil.registrarErro("msg.erro.id.usuario");
		}
	}
	
	
	public String redirecionarHistoricoJogadorClube(Long idJogador) {
		return  "/historicoConvocacaoJogadorClube.xhtml?faces-redirect=true&idJogador=" + idJogador;
	}
	
	public String redirecionarEditarJogador(Long idJogador) {
	    return "/editarJogador.xhtml?faces-redirect=true&idJogador=" + idJogador;
	}
	
	public String redirecionarEditarSenhaJogador(Long idLogin) {
	    return "/editarSenha.xhtml?faces-redirect=true&idLogin=" + idLogin;
	}
	
	public String redirecionarDuvidasFrequentesJogador(Long idJogador) {
	    return "/duvidasFrequentesJogador.xhtml?faces-redirect=true&idJogador=" + idJogador;
	}
}
