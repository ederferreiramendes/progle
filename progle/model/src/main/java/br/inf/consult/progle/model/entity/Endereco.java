package br.inf.consult.progle.model.entity;

import javax.persistence.Embeddable;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Embeddable
public class Endereco extends GenericEntity<Long> {	

	private String estado;
	
	private String cidade;
	
	private String bairro;
	
	private String rua;
	
	private String numero;
	
	private String cep;
	
	private String complemento;


}