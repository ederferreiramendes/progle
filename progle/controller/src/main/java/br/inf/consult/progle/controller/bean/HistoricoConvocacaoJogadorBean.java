package br.inf.consult.progle.controller.bean;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.HistoricoConvocacaoJogadorDTO;
import br.inf.consult.progle.model.service.HistoricoConvocacaoJogadorService;
import lombok.Getter;
import lombok.Setter;

@Controller("historicoConvocacaoJogadorBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class HistoricoConvocacaoJogadorBean {

    @Autowired
    private HistoricoConvocacaoJogadorService historicoConvocacaoJogadorService;

    private List<HistoricoConvocacaoJogadorDTO> historicoJogadores;

    @PostConstruct
    public void init() {
        this.historicoJogadores = new ArrayList<>();
    }

}
