package br.inf.consult.progle.model.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

import br.inf.consult.enums.TipoJogoEnum;
import lombok.Data;

@Data
public class JogoDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private LocalDate data;
	private LocalTime hora;
	private String local;
	private TipoJogoEnum  tipo;
}
