package br.inf.consult.progle.model.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.progle.model.dto.PagamentoDTO;
import br.inf.consult.progle.model.entity.Pagamento;
import br.inf.consult.progle.model.repository.PagamentoRepository;

@Service
public class PagamentoService extends GenericCrudService<Pagamento, Long, PagamentoRepository> {

    @Autowired
    private PagamentoRepository pagamentoRepository;

  
    public void salvar(PagamentoDTO pagamentoDTO) {
    	
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(pagamentoDTO.getValor());
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setMetodo(pagamentoDTO.getMetodo()); 
        pagamento.setComprovante(pagamentoDTO.getComprovante());
        pagamento.setNomeJogador(pagamentoDTO.getNomeJogador());

        super.salvar(pagamento);
    }

    private PagamentoDTO converterParaDto(Pagamento pagamento) {
		PagamentoDTO dto = new PagamentoDTO();
		dto.setDataPagamento(pagamento.getDataPagamento());
		dto.setMetodo(pagamento.getMetodo());
		dto.setValor(pagamento.getValor());
		dto.setComprovante(pagamento.getComprovante());
		dto.setNomeJogador(pagamento.getNomeJogador());
		
		return dto;
	}
      
    public Optional<PagamentoDTO> buscarPagamentoPorId(Long id) {
        return pagamentoRepository.findById(id).map(this::converterParaDto);
    }
    
    
}


