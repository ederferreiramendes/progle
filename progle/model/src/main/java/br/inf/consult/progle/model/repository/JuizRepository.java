package br.inf.consult.progle.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.inf.consult.progle.model.entity.Juiz;

public interface JuizRepository extends GenericCrudRepository<Juiz, Long> {

	Optional<Juiz> findById(Long id);
	
	List<Juiz> findByNomeContainingIgnoreCase(String nome);
	
    Optional<Juiz> findByCpf(String cpf);
    
    Double findAverageEstrelasById(Long id);

    @Query("SELECT COUNT(j) FROM Juiz j WHERE j.avaliacao = :estrelas")
    Long contarAvaliacoesPorEstrelas(@Param("estrelas") int estrelas);


}
