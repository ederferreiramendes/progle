package br.inf.consult.progle.model.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.progle.model.dto.PagamentoJuizDTO;
import br.inf.consult.progle.model.entity.PagamentoJuiz;
import br.inf.consult.progle.model.repository.PagamentoJuizRepository;

@Service
public class PagamentoJuizService extends GenericCrudService<PagamentoJuiz, Long, PagamentoJuizRepository> {

    @Autowired
    private PagamentoJuizRepository pagamentoJuizRepository;

  
    public void salvar(PagamentoJuizDTO pagamentoJuizDTO) {
    	
        PagamentoJuiz pagamento = new PagamentoJuiz();
        pagamento.setValor(pagamentoJuizDTO.getValor());
        pagamento.setDataPagamento(LocalDateTime.now());
        pagamento.setMetodo(pagamentoJuizDTO.getMetodo()); 
        pagamento.setComprovante(pagamentoJuizDTO.getComprovante());
        pagamento.setNomeJuiz(pagamentoJuizDTO.getNomeJuiz());

        super.salvar(pagamento);
    }

    private PagamentoJuizDTO converterParaDto(PagamentoJuiz pagamentoJuizDTO) {
		PagamentoJuizDTO dto = new PagamentoJuizDTO();
		dto.setDataPagamento(pagamentoJuizDTO.getDataPagamento());
		dto.setMetodo(pagamentoJuizDTO.getMetodo());
		dto.setValor(pagamentoJuizDTO.getValor());
		dto.setComprovante(pagamentoJuizDTO.getComprovante());
		dto.setNomeJuiz(pagamentoJuizDTO.getNomeJuiz());
		
		return dto;
	}
      
    public Optional<PagamentoJuizDTO> buscarPagamentoPorId(Long id) {
        return pagamentoJuizRepository.findById(id).map(this::converterParaDto);
    }
    
    
}


