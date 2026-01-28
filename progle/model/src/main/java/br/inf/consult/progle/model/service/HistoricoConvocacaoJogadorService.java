package br.inf.consult.progle.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.HistoricoConvocacaoJogadorDTO;
import br.inf.consult.progle.model.entity.HistoricoConvocacaoJogador;
import br.inf.consult.progle.model.repository.ConvocacaoJogadorRepository;
import br.inf.consult.progle.model.repository.HistoricoConvocacaoJogadorRepository;
import br.inf.consult.progle.model.repository.PagamentoJogadorRepository;

@Service
public class HistoricoConvocacaoJogadorService extends GenericCrudService<HistoricoConvocacaoJogador, Long, HistoricoConvocacaoJogadorRepository>{
	
	@Autowired
    private HistoricoConvocacaoJogadorRepository historicoConvocacaoJogadorRepository;
	
	@Autowired
    private ConvocacaoJogadorRepository convocacaoJogadorRepository;

    @Autowired
    private PagamentoJogadorRepository pagamentoJogadorRepository;

	 public HistoricoConvocacaoJogador converterParaHistoricoConvocacao(HistoricoConvocacaoJogadorDTO historicoDTO) {
	        HistoricoConvocacaoJogador historico = new HistoricoConvocacaoJogador();
	        historico.setNomeJogador(historicoDTO.getNomeJogador());
	        historico.setDataPartida(historicoDTO.getDataPartida());
	        historico.setValorContratacao(historicoDTO.getValorContratacao());
	        historico.setPosicoesCampo(historicoDTO.getPosicoesCampo());
	        historico.setPosicoesFutsal(historicoDTO.getPosicoesFutsal());
	        historico.setPosicoesSociety(historicoDTO.getPosicoesSociety());
	        historico.setTipoJogo(historicoDTO.getTipoJogo());
	        
	        CadastroEnderecoDTO endereco = new CadastroEnderecoDTO();
	        endereco.setEstado(historicoDTO.getLocalJogo().getEstado());
	        endereco.setCidade(historicoDTO.getLocalJogo().getCidade());
	        endereco.setBairro(historicoDTO.getLocalJogo().getBairro());
	        endereco.setRua(historicoDTO.getLocalJogo().getRua());
	        endereco.setNumero(historicoDTO.getLocalJogo().getNumero());
	        endereco.setCep(historicoDTO.getLocalJogo().getCep());

	        
	        historico.setLocalJogo(endereco);
	        return historico;
	    }
	 
	 public void salvarHistorico(HistoricoConvocacaoJogadorDTO historicoDTO) {
	        HistoricoConvocacaoJogador historico = converterParaHistoricoConvocacao(historicoDTO);
	        historicoConvocacaoJogadorRepository.save(historico);
	    }
	 
	 public List<HistoricoConvocacaoJogadorDTO> buscarTodosHistoricos() {
		    List<HistoricoConvocacaoJogador> historicos = historicoConvocacaoJogadorRepository.findAll();
		    List<HistoricoConvocacaoJogadorDTO> historicosDTO = new ArrayList<>();

		    for (HistoricoConvocacaoJogador historico : historicos) {
		        HistoricoConvocacaoJogadorDTO dto = new HistoricoConvocacaoJogadorDTO();

		        dto.setNomeJogador(historico.getNomeJogador());
		        dto.setDataPartida(historico.getDataPartida());
		        dto.setValorContratacao(historico.getValorContratacao());
		        dto.setPosicoesCampo(historico.getPosicoesCampo());
		        dto.setPosicoesFutsal(historico.getPosicoesFutsal());
		        dto.setPosicoesSociety(historico.getPosicoesSociety());
		        dto.setTipoJogo(historico.getTipoJogo());

		        CadastroEnderecoDTO enderecoDTO = new CadastroEnderecoDTO();
		        if (historico.getLocalJogo() != null) {
		            enderecoDTO.setEstado(historico.getLocalJogo().getEstado());
		            enderecoDTO.setCidade(historico.getLocalJogo().getCidade());
		            enderecoDTO.setBairro(historico.getLocalJogo().getBairro());
		            enderecoDTO.setRua(historico.getLocalJogo().getRua());
		            enderecoDTO.setNumero(historico.getLocalJogo().getNumero());
		            enderecoDTO.setCep(historico.getLocalJogo().getCep());
		        }
		        dto.setLocalJogo(enderecoDTO);

		        historicosDTO.add(dto);
		    }

		    return historicosDTO;
		}
	 

}
