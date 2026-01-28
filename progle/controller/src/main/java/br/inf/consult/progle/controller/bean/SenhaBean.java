package br.inf.consult.progle.controller.bean;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.util.FacesUtil;
import br.inf.consult.progle.util.Validate;
import lombok.Getter;
import lombok.Setter;

@Controller("senhaBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter

public class SenhaBean {
	

	private String senha;
    private String novaSenha;

    public void salvarNovaSenha() {
        if (Validate.verificarSenhasIguais(senha, novaSenha)) {
        	FacesUtil.registrarMensagem("msg.app.senha.atualizada");
        } else {
        	FacesUtil.registrarErro("msg.erro.senha.diferente");
        }
    }
}
