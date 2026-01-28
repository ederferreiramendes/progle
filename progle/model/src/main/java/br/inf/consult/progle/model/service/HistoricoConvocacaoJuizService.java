package br.inf.consult.progle.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.progle.model.dto.HistoricoConvocacaoJuizDTO;
import br.inf.consult.progle.model.entity.HistoricoConvocacaoJuiz;
import br.inf.consult.progle.model.repository.HistoricoConvocacaoJuizRepository;


@Service
public class HistoricoConvocacaoJuizService extends GenericCrudService<HistoricoConvocacaoJuiz, Long, HistoricoConvocacaoJuizRepository>{
	
	@Autowired
    private HistoricoConvocacaoJuizRepository historicoJuizRepository;

	 public HistoricoConvocacaoJuiz converterParaHistoricoConvocacao(HistoricoConvocacaoJuizDTO historicoDTO) {
	        HistoricoConvocacaoJuiz historico = new HistoricoConvocacaoJuiz();
	        historico.setNomeJuiz(historicoDTO.getNomeJuiz());
	        historico.setDataPartida(historicoDTO.getDataPartida());
	        historico.setValorContratacao(historicoDTO.getValorContratacao());
	        historico.setLocalJogo(historicoDTO.getLocalJogo());
	        historico.setPosicoesCampo(historicoDTO.getPosicoesCampo());
	        historico.setPosicoesFutsal(historicoDTO.getPosicoesFutsal());
	        historico.setPosicoesSociety(historicoDTO.getPosicoesSociety());
	        historico.setTipoJogo(historicoDTO.getTipoJogo());
	        return historico;
	    }
	 
	 public void salvarHistorico(HistoricoConvocacaoJuizDTO historicoDTO) {
	        HistoricoConvocacaoJuiz historico = converterParaHistoricoConvocacao(historicoDTO);
	        historicoJuizRepository.save(historico);
	    }
	 
	 public List<HistoricoConvocacaoJuizDTO> buscarTodosHistoricos() {
		    List<HistoricoConvocacaoJuiz> historicos = historicoJuizRepository.findAll();
		    List<HistoricoConvocacaoJuizDTO> historicosDTO = new ArrayList<>();
		    
		    for (HistoricoConvocacaoJuiz historico : historicos) {
		        HistoricoConvocacaoJuizDTO dto = new HistoricoConvocacaoJuizDTO();

		        dto.setNomeJuiz(historico.getNomeJuiz());
		        dto.setDataPartida(historico.getDataPartida());
		        dto.setValorContratacao(historico.getValorContratacao());
		        dto.setLocalJogo(historico.getLocalJogo());
		        dto.setPosicoesCampo(historico.getPosicoesCampo());
		        dto.setPosicoesFutsal(historico.getPosicoesFutsal());
		        dto.setPosicoesSociety(historico.getPosicoesSociety());
		        dto.setTipoJogo(historico.getTipoJogo());
		        
		        historicosDTO.add(dto);
		    }
		    
		    return historicosDTO;
		}



}
