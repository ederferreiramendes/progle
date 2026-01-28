package br.inf.consult.progle.model.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.inf.consult.enums.MetodoPagamentoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_pagamentos_jogadores")
@Data
@EqualsAndHashCode(callSuper = true)
public class Pagamento extends GenericEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	
    private Double valor;

    @Column(name = "data_pagamento")
    
    private LocalDateTime dataPagamento;
    

    @Enumerated(EnumType.STRING)
    private MetodoPagamentoEnum metodo;
    
    private byte[] comprovante;
    
    private String nomeJogador;

    
}
