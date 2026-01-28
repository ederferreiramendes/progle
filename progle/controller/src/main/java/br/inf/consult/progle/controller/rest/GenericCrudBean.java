package br.inf.consult.progle.controller.rest;

import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import br.inf.consult.progle.exception.AplicacaoExceptionHandler;
import br.inf.consult.progle.model.entity.GenericEntity;
import br.inf.consult.progle.model.service.GenericCrudService;
import br.inf.consult.progle.util.FacesUtil;
import lombok.Getter;
import lombok.Setter;

/**
 * @author efmendes
 *
 * @param <T>
 * @param <S>
 * @param <I>
 */
public abstract class GenericCrudBean<T extends GenericEntity<I>, I, S extends GenericCrudService<T, I, ?>>
        {

    private static final long serialVersionUID = -3853594377194808570L;

    @Autowired
    protected S service;

    @Getter
    @Setter
    private transient T entity;

    @Getter
    @Setter
    private transient List<T> list;

    @PostConstruct
    @SuppressWarnings("unchecked")
    public void init() {
        try {
            this.entity = ((Class<T>) ((ParameterizedType) getClass().getGenericSuperclass())
                    .getActualTypeArguments()[0]).newInstance();
            this.list = service.listar();
        } catch (Exception e) {
            new AplicacaoExceptionHandler().tratarErro(e);
        }
    }

    public void carregarIncluir() {
        init();
    }

    public void carregarAlterar(T model) {
        this.entity = model;
    }


    public void salvar() {
        service.salvar(entity);
        init();
    }

    public void remover(I id) {
    	entity = service.buscar(id);
        service.remover(entity);
        init();
        registrarMensagemSucesso();
    }

    private void registrarMensagemSucesso() {
        FacesUtil.registrarMensagem("msg.app.sucesso.operacao");

    }

    public void visualizar(I id) {
        this.entity = buscar(id);
    }

    public T buscar(I id) {
        return service.buscar(id);
    }

}