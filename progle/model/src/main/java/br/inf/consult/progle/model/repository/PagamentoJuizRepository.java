package br.inf.consult.progle.model.repository;

import java.util.Optional;

import br.inf.consult.progle.model.entity.PagamentoJuiz;

public interface PagamentoJuizRepository extends GenericCrudRepository<PagamentoJuiz, Long>{
	
	Optional<PagamentoJuiz> findById(Long id);

}
