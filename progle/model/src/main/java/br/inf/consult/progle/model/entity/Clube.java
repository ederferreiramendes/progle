package br.inf.consult.progle.model.entity;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_clubes")
@Data
@EqualsAndHashCode(callSuper = true)
public class Clube extends GenericEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String nome;

	private String email;

	@CPF(message = "O CPF informado não é válido.")
	private String cpf;

	@CNPJ
	private String cnpj;
	
	private String fotoCaminho;

	private String responsavel;

	private String contato;

	@Embedded
	private Endereco endereco;

	private String historico;

	@Transient
	private UsuarioSistema usuarioSistema;
}
