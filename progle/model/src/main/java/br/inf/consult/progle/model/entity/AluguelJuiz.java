package br.inf.consult.progle.model.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_alugueis")
@Data
@EqualsAndHashCode(callSuper = true)
public class AluguelJuiz extends GenericEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
    @JoinColumn(name = "id_jogador")
    private Juiz juiz;

    @ManyToOne
    @JoinColumn(name = "id_time")
    private Clube time;

    @ManyToOne
    @JoinColumn(name = "id_jogo")
    private Jogo jogo;

    private Double valor; 

    private String status; 

}
