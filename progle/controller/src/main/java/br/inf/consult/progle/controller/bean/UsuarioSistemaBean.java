package br.inf.consult.progle.controller.bean;

import java.util.Optional;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.UsuarioSistemaDTO;
import br.inf.consult.progle.model.entity.UsuarioSistema;
import br.inf.consult.progle.model.service.UsuarioSistemaService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

@Controller("usuarioSistemaBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class UsuarioSistemaBean {
	
	 @Autowired
	 private UsuarioSistemaService usuarioService;

    private UsuarioSistemaDTO usuarioSistemaDTO;
    private Long idLogin;
    private Long idClube;
    private Long idJogador;
    private Long idJuiz;
    private String confirmaSenhaTemporaria;

   

    @PostConstruct
    public void init() {
        this.usuarioSistemaDTO = new UsuarioSistemaDTO();
    }

    public String login() {
        String cpf = usuarioSistemaDTO.getCpf();
        String senha = usuarioSistemaDTO.getSenha();
        UsuarioSistema usuario = usuarioService.autenticar(cpf, senha);

        if (usuario == null) {
            FacesUtil.registrarErro("msg.erro.login");
            return null;
        }

        this.idLogin = usuario.getId();

        switch (usuario.getTipoUsuarioEnum()) {
            case JOGADOR:
                this.idJogador = usuario.getJogador().getId();
                return "/telaInicialJogador.xhtml?faces-redirect=true&idJogador=" + idJogador + "&idLogin=" + idLogin;
            case JUIZ:
                this.idJuiz = usuario.getJuiz().getId();
                return "/telaInicialJuiz.xhtml?faces-redirect=true&idJuiz=" + idJuiz + "&idLogin=" + idLogin;
            case CLUBE:
                this.idClube = usuario.getClube().getId();
                return "/telaInicialClube.xhtml?faces-redirect=true&idClube=" + idClube + "&idLogin=" + idLogin;
            default:
                FacesUtil.registrarErro("msg.erro.login");
                return null;
        }
    }
    
    public void buscarLoginPorId() {
		if (idLogin == null) {
		    FacesUtil.registrarErro("msg.erro.id.nulo");
		    return;
		}
		if (idLogin != null) {
			Optional<UsuarioSistemaDTO> optionalUsuarioSistemaDTO = usuarioService.buscarLoginPorId(idLogin);
			usuarioSistemaDTO = optionalUsuarioSistemaDTO.get();
		} else {
			FacesUtil.registrarErro("msg.erro.id.login");
		}
	}
    
    public void editarSenha() {
		if(usuarioService.existe(idLogin)){
			usuarioService.editarSenha(idLogin, usuarioSistemaDTO);
			FacesUtil.registrarMensagem("msg.app.update.senha");
		}
		else {
			FacesUtil.registrarErro("msg.erro.editar.usuario");
		}
	}

}
