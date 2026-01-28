package br.inf.consult.progle.model.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.inf.consult.enums.TipoJogoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_jogos")
@Data
@EqualsAndHashCode(callSuper = true)
public class Jogo extends GenericEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private LocalDate data;
	
	private LocalTime hora;
	
	private String local;
	
	private TipoJogoEnum  tipo;


}
