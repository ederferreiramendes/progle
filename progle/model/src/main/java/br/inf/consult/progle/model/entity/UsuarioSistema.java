package br.inf.consult.progle.model.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.br.CPF;

import br.inf.consult.enums.TipoUsuarioEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "tb_usuarios_sistemas")
@Entity

public class UsuarioSistema extends GenericEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@CPF
	private String cpf;
	private String senha;
	@Enumerated(EnumType.STRING)
    private TipoUsuarioEnum tipoUsuarioEnum;
	
	@OneToOne
	@JoinColumn(name = "jogador_id")
	private Jogador jogador;

	@OneToOne
	@JoinColumn(name = "juiz_id")
	private Juiz juiz;

	@OneToOne
	@JoinColumn(name = "clube_id")
	private Clube clube;
	
	public enum TipoUsuario {
        JOGADOR, JUIZ, CLUBE
    }

}
