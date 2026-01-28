package br.inf.consult.progle.controller.bean;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.progle.model.dto.CadastroJuizDTO;
import br.inf.consult.progle.model.dto.UsuarioSistemaDTO;
import br.inf.consult.progle.model.service.JuizService;
import br.inf.consult.progle.model.service.UsuarioSistemaService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("telaInicialJuizBean")
@Scope("session")
@Getter
@Setter
public class TelaIncialJuizBean {
	
	@Autowired
	private JuizService juizService;
	@Autowired
	private UsuarioSistemaService usuarioSistemaService;
	
	private Long idJuiz;
	private CadastroJuizDTO juizDTO;
	private Long idLogin;
	private UsuarioSistemaDTO usuarioSistemaDTO;
	
	public void buscarJuizLoginPorId() {
		if (idJuiz == null) {
		    FacesUtil.registrarErro("msg.erro.id.nulo");
		    return;
		}
		
		if (idLogin == null) {
		    FacesUtil.registrarErro("msg.erro.id.login.nulo");
		    return;
		}
		
		if (idJuiz != null) {
			Optional<CadastroJuizDTO> optionalJuizDTO = juizService.buscarJuizPorId(idJuiz);
			Optional<UsuarioSistemaDTO> optionalUsuarioSistemaDTO = usuarioSistemaService.buscarLoginPorId(idLogin);
			juizDTO = optionalJuizDTO.get();
			usuarioSistemaDTO = optionalUsuarioSistemaDTO.get();
		} else {
			FacesUtil.registrarErro("msg.erro.id.usuario");
		}
	}
	
	
	
	public String redirecionarHistoricoJuizClube(Long idJuiz) {
		return  "/historicoConvocacaoJuizClube.xhtml?faces-redirect=true&idJuiz=" + idJuiz;
	}
	
	public String redirecionarEditarJuiz(Long idJuiz) {
	    return "/editarJuiz.xhtml?faces-redirect=true&idJuiz=" + idJuiz;
	}
	
	public String redirecionarEditarSenhaJuiz(Long idLogin) {
	    return "/editarSenha.xhtml?faces-redirect=true&idLogin=" + idLogin;
	}
	
	public String redirecionarDuvidasFrequentesJuiz(Long idJuiz) {
	    return "/duvidasFrequentesJuiz.xhtml?faces-redirect=true&idJuiz=" + idJuiz;
	}
}
