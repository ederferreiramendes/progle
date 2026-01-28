package br.inf.consult.progle.model.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import br.inf.consult.progle.model.entity.UsuarioSistema;

@Repository
public interface UsuarioSistemaRepository extends GenericCrudRepository<UsuarioSistema, Long> {
	
	Optional<UsuarioSistema> findById(Long id);
	
	UsuarioSistema findByCpfAndSenha(String cpf, String senha);
	
	boolean existsByCpf(String cpf);
	
	 Optional<UsuarioSistema> findByCpf(String cpf);
}
