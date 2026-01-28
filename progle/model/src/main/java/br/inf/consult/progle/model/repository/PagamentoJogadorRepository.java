package br.inf.consult.progle.model.repository;

import java.util.Optional;

import br.inf.consult.progle.model.entity.PagamentoJogador;

public interface PagamentoJogadorRepository extends GenericCrudRepository<PagamentoJogador, Long>{
	
	Optional<PagamentoJogador> findById(Long id);

}
