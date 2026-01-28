package br.inf.consult.progle.model.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.br.CPF;

import br.inf.consult.enums.NivelEnum;
import br.inf.consult.enums.TipoJuizEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_juizes")
@Data
@EqualsAndHashCode(callSuper = true)
public class Juiz extends GenericEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String nome;

	@CPF(message = "O CPF informado não é válido.")
	private String cpf;

	@Embedded
	private Endereco endereco;

	private String historico;

	private String email;

	private String contato;
	
	private String instagram;

	private Integer raioAtuacao;

	@Enumerated(EnumType.STRING)
	private NivelEnum nivel;	

	@ElementCollection(targetClass = TipoJuizEnum.class)
	@JoinTable(name = "tb_tipo_juiz", joinColumns = @JoinColumn(name = "id_juiz"))
	@Column(name = "tipo_juiz", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<TipoJuizEnum> tipoJuizEnum;

	@Embedded
	private DadoBancario dadoBancario;

	private double valorAluguel;

	private double avaliacao;

	
	private String fotoCaminho;
	
	@Transient
	private UsuarioSistema usuarioSistema;


}
