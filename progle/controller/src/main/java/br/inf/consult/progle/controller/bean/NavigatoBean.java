package br.inf.consult.progle.controller.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.progle.config.ViewScope;
import lombok.Getter;
import lombok.Setter;

@Controller("navigatoBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class NavigatoBean {

	public String redirectToClube() {
		return "cadastroClube.xhtml?faces-redirect=true";
	}

	public String redirectToJogador() {
		return "cadastroJogador.xhtml?faces-redirect=true";
	}

	public String redirectToJuiz() {
		return "cadastroJuiz.xhtml?faces-redirect=true";
	}
	
	public String redirectToCadastroUsuario() {
	    return "cadastro.xhtml?faces-redirect=true"; 
	}
	
	public String redirectToLogin() {
	    return "login.xhtml?faces-redirect=true"; 
	}
	
	public String redirecionarAluguelJogador() {
	    return "aluguelJogador.xhtml?faces-redirect=true";
	}
	
	
	public String redirecionarTelaInicialClube() {
	    return "/telaInicialClube.xhtml?faces-redirect=true"; 
	}
	
	public String redirecionarTelaConvocacao() {
        return "/convocacaoJogador.xhtml?faces-redirect=true";
    }
	
	public String redirecionarDetalhesJogador() {
        return "/detalhesJogador.xhtml?faces-redirect=true";
    }

	public String redirecionarAluguelJuiz() {
        return "/aluguelJuiz.xhtml?faces-redirect=true";
    }
	
	public String redirecionarHistoricoContratacaoJogador() {
	    return "/historicoConvocacaoJogador.xhtml?faces-redirect=true";
	}
}
