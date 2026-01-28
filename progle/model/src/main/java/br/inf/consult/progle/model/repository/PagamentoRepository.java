package br.inf.consult.progle.model.repository;

import java.util.Optional;

import br.inf.consult.progle.model.entity.Pagamento;

public interface PagamentoRepository extends GenericCrudRepository<Pagamento, Long>{
	
	Optional<Pagamento> findById(Long id);

}
