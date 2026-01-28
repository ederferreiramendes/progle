package br.inf.consult.progle.model.entity;


import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import br.inf.consult.enums.TipoChavePixEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Embeddable

public class DadoBancario extends GenericEntity<Long> {

	private String banco;
	
	private String numeroConta;
	
	private String agencia;
	
	private String chavePix;
	
	@Enumerated(EnumType.STRING)
	private TipoChavePixEnum tipoChavePixEnum;


}
