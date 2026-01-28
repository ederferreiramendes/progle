package br.inf.consult.progle.model.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.inf.consult.progle.model.entity.Jogador;

public interface JogadorRepository extends GenericCrudRepository<Jogador, Long> {
	
   Optional<Jogador> findById(Long id);
    
    List<Jogador> findByNomeContainingIgnoreCase(String nome);
    
    Optional<Jogador> findByCpf(String cpf);
    
    Double findAverageEstrelasById(Long id);

    @Query("SELECT COUNT(j) FROM Jogador j WHERE j.avaliacao = :estrelas")
    Long contarAvaliacoesPorEstrelas(@Param("estrelas") int estrelas);

    
}
