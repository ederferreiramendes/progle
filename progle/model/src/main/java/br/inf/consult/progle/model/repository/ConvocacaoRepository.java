package br.inf.consult.progle.model.repository;

import java.util.Optional;

import br.inf.consult.progle.model.entity.Convocacao;

public interface ConvocacaoRepository extends GenericCrudRepository<Convocacao, Long> {
	
	Optional<Convocacao> findById(Long id);

}
