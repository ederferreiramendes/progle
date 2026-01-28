
package br.inf.consult.progle.model.dto;

import java.io.Serializable;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.inf.consult.enums.TipoUsuarioEnum;
import br.inf.consult.progle.model.entity.Clube;
import br.inf.consult.progle.model.entity.Jogador;
import br.inf.consult.progle.model.entity.Juiz;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "nome",
    "cpf",
    "email",
    "senha",
})
public class UsuarioSistemaDTO implements Serializable
{

    private final static long serialVersionUID = 1L;

    @JsonProperty("nome")
    private String nome;
    @JsonProperty("cpf")
    private String cpf;
    @JsonProperty("email")
    private String email;
    @JsonProperty("senha")
    private String senha;
    @Enumerated(EnumType.STRING)
    private TipoUsuarioEnum tipoUsuario;
  
    private Jogador jogador;

    private Juiz juiz;

    private Clube clube;


}
