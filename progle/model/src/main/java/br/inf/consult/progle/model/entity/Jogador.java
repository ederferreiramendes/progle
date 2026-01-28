package br.inf.consult.progle.model.entity;

import java.util.Date;
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
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.br.CPF;

import br.inf.consult.enums.NivelEnum;
import br.inf.consult.enums.PosicaoCampoEnum;
import br.inf.consult.enums.PosicaoFutsalEnum;
import br.inf.consult.enums.PosicaoSocietyEnum;
import br.inf.consult.enums.SexoEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_jogadores")
@Data
@EqualsAndHashCode(callSuper = true)
public class Jogador extends GenericEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
	private String nome;

	@Column(length = 50)
	private String apelido;

	@CPF(message = "O CPF informado não é válido.")
	private String cpf;

	private String email;

	@Enumerated(EnumType.STRING)
	private SexoEnum sexo;

	@ElementCollection(targetClass = PosicaoCampoEnum.class)
	@JoinTable(name = "tb_posicoes_campo_jogador", joinColumns = @JoinColumn(name = "id_jogador"))
	@Column(name = "posicao", nullable = false)
	@Enumerated(EnumType.STRING)
	@OrderColumn(name = "posicao_order")
	private List<PosicaoCampoEnum> posicaoCampo;

	@ElementCollection(targetClass = PosicaoFutsalEnum.class)
	@JoinTable(name = "tb_posicoes_futsal_jogador", joinColumns = @JoinColumn(name = "id_jogador"))
	@Column(name = "posicao", nullable = false)
	@Enumerated(EnumType.STRING)
	@OrderColumn(name = "posicao_order")
	private List<PosicaoFutsalEnum> posicaoFutsal;

	@ElementCollection(targetClass = PosicaoSocietyEnum.class)
	@JoinTable(name = "tb_posicoes_society_jogador", joinColumns = @JoinColumn(name = "id_jogador"))
	@Column(name = "posicao", nullable = false)
	@Enumerated(EnumType.STRING)
	@OrderColumn(name = "posicao_order")
	private List<PosicaoSocietyEnum> posicoesSociety;

	@Enumerated(EnumType.STRING)
	private NivelEnum nivel;

	@Embedded
	private Endereco endereco;

	@Embedded
	private DadoBancario dadoBancario;

	private Integer raioAtuacao;

	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	private String contato;
	
	private String instagram;

	private double valorAluguel;

	private double avaliacao;

	private String historico;

	private String fotoCaminho;

	@Transient
	private UsuarioSistema usuarioSistema;

}
