package br.inf.consult.progle.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import br.inf.consult.progle.model.entity.GenericEntity;


@NoRepositoryBean
public interface GenericCrudRepository<T extends GenericEntity<I>, I> extends JpaRepository<T, I> {



}
