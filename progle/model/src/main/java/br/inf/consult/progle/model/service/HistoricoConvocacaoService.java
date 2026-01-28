package br.inf.consult.progle.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import br.inf.consult.progle.model.dto.HistoricoConvocacaoDTO;
import br.inf.consult.progle.model.entity.HistoricoConvocacao;
import br.inf.consult.progle.model.repository.HistoricoConvocacaoRepository;

@Service
public class HistoricoConvocacaoService extends GenericCrudService<HistoricoConvocacao, Long, HistoricoConvocacaoRepository>{
	
    private HistoricoConvocacaoRepository historicoRepository;

	 public HistoricoConvocacao converterParaHistoricoConvocacao(HistoricoConvocacaoDTO historicoDTO) {
	        HistoricoConvocacao historico = new HistoricoConvocacao();
	        historico.setNomeJogador(historicoDTO.getNomeJogador());
	        historico.setDataPartida(historicoDTO.getDataPartida());
	        historico.setValorContratacao(historicoDTO.getValorContratacao());
	        historico.setLocalJogo(historicoDTO.getLocalJogo());
	        historico.setPosicoesCampo(historicoDTO.getPosicoesCampo());
	        historico.setPosicoesFutsal(historicoDTO.getPosicoesFutsal());
	        historico.setPosicoesSociety(historicoDTO.getPosicoesSociety());
	        historico.setTipoJogo(historicoDTO.getTipoJogo());
	        return historico;
	    }
	 
	 public void salvarHistorico(HistoricoConvocacaoDTO historicoDTO) {
	        HistoricoConvocacao historico = converterParaHistoricoConvocacao(historicoDTO);
	        historicoRepository.save(historico);
	    }
	 
	 public List<HistoricoConvocacaoDTO> buscarTodosHistoricos() {
		    List<HistoricoConvocacao> historicos = historicoRepository.findAll();
		    List<HistoricoConvocacaoDTO> historicosDTO = new ArrayList<>();
		    
		    for (HistoricoConvocacao historico : historicos) {
		        HistoricoConvocacaoDTO dto = new HistoricoConvocacaoDTO();

		        dto.setNomeJogador(historico.getNomeJogador());
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
