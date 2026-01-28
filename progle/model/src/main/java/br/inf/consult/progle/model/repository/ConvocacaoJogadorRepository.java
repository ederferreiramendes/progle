package br.inf.consult.progle.model.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.inf.consult.progle.model.entity.Clube;
import br.inf.consult.progle.model.entity.ConvocacaoJogador;
import br.inf.consult.progle.model.entity.Jogador;

public interface ConvocacaoJogadorRepository extends GenericCrudRepository<ConvocacaoJogador, Long> {
	
	Optional<ConvocacaoJogador> findById(Long id);
	
	List<ConvocacaoJogador> findByClube(Clube clube);
	
	List<ConvocacaoJogador> findByJogador(Jogador jogador);
	
	List<ConvocacaoJogador> findByDataPartidaAndJogadorId(Date dataPartida, Long jogadorId);
	
	List<ConvocacaoJogador> findByJogadorId(Long idJogador);

	@Query("SELECT COALESCE(AVG(c.avaliacao), 0) FROM ConvocacaoJogador c WHERE c.jogador.id = :idJogador")
	Double calcularMediaAvaliacao(@Param("idJogador") Long idJogador);

}


