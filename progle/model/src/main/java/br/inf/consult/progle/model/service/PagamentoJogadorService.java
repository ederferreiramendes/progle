package br.inf.consult.progle.model.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.progle.model.dto.PagamentoJogadorDTO;
import br.inf.consult.progle.model.entity.PagamentoJogador;
import br.inf.consult.progle.model.repository.PagamentoJogadorRepository;

@Service
public class PagamentoJogadorService extends GenericCrudService<PagamentoJogador, Long, PagamentoJogadorRepository> {

    @Autowired
    private PagamentoJogadorRepository pagamentoRepository;

  
    public void salvar(PagamentoJogadorDTO pagamentoDTO) {
    	
        PagamentoJogador pagamento = new PagamentoJogador();
        pagamento.setValor(pagamentoDTO.getValor());
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setMetodo(pagamentoDTO.getMetodo()); 
       

        super.salvar(pagamento);
    }

    private PagamentoJogadorDTO converterParaDto(PagamentoJogador pagamento) {
		PagamentoJogadorDTO dto = new PagamentoJogadorDTO();
		dto.setDataPagamento(pagamento.getDataPagamento());
		dto.setMetodo(pagamento.getMetodo());
		dto.setValor(pagamento.getValor());
		
		
		return dto;
	}
      
    public Optional<PagamentoJogadorDTO> buscarPagamentoPorId(Long id) {
        return pagamentoRepository.findById(id).map(this::converterParaDto);
    }
    
    
}


