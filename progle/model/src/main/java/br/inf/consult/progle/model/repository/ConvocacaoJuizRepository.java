package br.inf.consult.progle.model.repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.inf.consult.progle.model.entity.Clube;
import br.inf.consult.progle.model.entity.ConvocacaoJuiz;
import br.inf.consult.progle.model.entity.Juiz;

public interface ConvocacaoJuizRepository extends GenericCrudRepository<ConvocacaoJuiz, Long> {
	
	Optional<ConvocacaoJuiz> findById(Long id);
	
	List<ConvocacaoJuiz> findByClube(Clube clube);
	
	List<ConvocacaoJuiz> findByJuiz(Juiz juiz);
	
	List<ConvocacaoJuiz> findByDataPartidaAndJuizId(Date dataPartida, Long juizId);
	
	List<ConvocacaoJuiz> findByJuizId(Long idJuiz);

	@Query("SELECT COALESCE(AVG(c.avaliacao), 0) FROM ConvocacaoJuiz c WHERE c.juiz.id = :idJuiz")
	Double calcularMediaAvaliacao(@Param("idJuiz") Long idJuiz);

}
