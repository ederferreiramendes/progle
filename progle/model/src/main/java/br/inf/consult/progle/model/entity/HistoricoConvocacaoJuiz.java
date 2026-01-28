package br.inf.consult.progle.model.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OrderColumn;
import javax.persistence.Table;

import br.inf.consult.enums.PosicaoCampoEnum;
import br.inf.consult.enums.PosicaoFutsalEnum;
import br.inf.consult.enums.PosicaoSocietyEnum;
import br.inf.consult.enums.TipoJogoEnum;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "tb_historicos_contratacoes_juizes")
@Data
@EqualsAndHashCode(callSuper = true)

public class HistoricoConvocacaoJuiz extends GenericEntity<Long> {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
    
    private String nomeJuiz;
    
    private Date dataPartida;
    
    private Double valorContratacao;
    
    private CadastroEnderecoDTO localJogo;
    
    private TipoJogoEnum tipoJogo;
    
    @ElementCollection(targetClass = PosicaoCampoEnum.class)
    @JoinTable(name = "tb_posicoes_campo_jogador", joinColumns = @JoinColumn(name = "id_jogador"))
    @Column(name = "posicao", nullable = false)
    @Enumerated(EnumType.STRING)
    @OrderColumn(name = "posicao_order") 
    private PosicaoCampoEnum[] posicoesCampo = new PosicaoCampoEnum[11];

    @ElementCollection(targetClass = PosicaoFutsalEnum.class)
    @JoinTable(name = "tb_posicoes_futsal_jogador", joinColumns = @JoinColumn(name = "id_jogador"))
    @Column(name = "posicao", nullable = false)
    @Enumerated(EnumType.STRING)
    @OrderColumn(name = "posicao_order") 
    private PosicaoFutsalEnum[] posicoesFutsal = new PosicaoFutsalEnum[5];

    @ElementCollection(targetClass = PosicaoSocietyEnum.class)
    @JoinTable(name = "tb_posicoes_society_jogador", joinColumns = @JoinColumn(name = "id_jogador"))
    @Column(name = "posicao", nullable = false)
    @Enumerated(EnumType.STRING)
    @OrderColumn(name = "posicao_order") 
    private PosicaoSocietyEnum[] posicoesSociety = new PosicaoSocietyEnum[6];
    
    private String foto;
    
    @ManyToOne
    private Clube clube;


}
