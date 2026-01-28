package br.inf.consult.progle.model.repository;

import java.util.Optional;

import br.inf.consult.progle.model.entity.Clube;

public interface ClubeRepository extends GenericCrudRepository<Clube, Long>{
	
	Optional<Clube> findById(Long id);
    Optional<Clube> findByCpf(String cpf);


}
