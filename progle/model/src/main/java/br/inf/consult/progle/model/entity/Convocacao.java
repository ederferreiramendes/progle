package br.inf.consult.progle.model.entity;

import java.util.Date;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.inf.consult.enums.TipoJogoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_convocacoes_jogadores")
@Data
@EqualsAndHashCode(callSuper = true)
public class Convocacao extends GenericEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataPartida;

	@Embedded
    private Endereco endereco;

	@Enumerated(EnumType.STRING)
	private TipoJogoEnum tipoJogo;
	
	@ManyToOne
	private Clube clube;
	
	@ManyToOne
	private Jogador jogador;
	
	@ManyToOne
	private Juiz juiz;
}
