package br.inf.consult.progle.model.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.progle.model.dto.CadastroClubeDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.ConvocacaoJuizDTO;
import br.inf.consult.progle.model.entity.Clube;
import br.inf.consult.progle.model.entity.ConvocacaoJuiz;
import br.inf.consult.progle.model.entity.Endereco;
import br.inf.consult.progle.model.entity.Juiz;
import br.inf.consult.progle.model.repository.ConvocacaoJuizRepository;

@Service
public class ConvocacaoJuizService extends GenericCrudService<ConvocacaoJuiz, Long, ConvocacaoJuizRepository> {

	@Autowired
	private ConvocacaoJuizRepository convocacaoJuizRepository;

	@Autowired
	private ClubeService clubeService;

	@Autowired
	private JuizService juizService;
	

	public Long salvarConvocacao(ConvocacaoJuizDTO novaConvocacao, Long idClube, Long idJuiz) {
		
		Clube clube = clubeService.buscar(idClube);
		Juiz juiz = juizService.buscar(idJuiz);

		ConvocacaoJuiz convocacao = new ConvocacaoJuiz();
		
		convocacao.setDataPartida(novaConvocacao.getDataPartida());
		convocacao.setTipoJogo(novaConvocacao.getTipoJogo());
		convocacao.setNomeJuiz(juiz.getNome());
		convocacao.setNomeClube(clube.getNome());
		convocacao.setValor(novaConvocacao.getJuizDTO().getValorAluguel());
		convocacao.setClube(clube);
		convocacao.setJuiz(juiz);
		convocacao.setNomeClube(clube.getNome());
		convocacao.setDataDaConvocacao(LocalDateTime.now());

		Endereco endereco = new Endereco();
		endereco.setRua(novaConvocacao.getEnderecoDTO().getRua());
		endereco.setCidade(novaConvocacao.getEnderecoDTO().getCidade());
		endereco.setEstado(novaConvocacao.getEnderecoDTO().getEstado());
		endereco.setCep(novaConvocacao.getEnderecoDTO().getCep());
		endereco.setBairro(novaConvocacao.getEnderecoDTO().getBairro());
		endereco.setNumero(novaConvocacao.getEnderecoDTO().getNumero());
		convocacao.setEndereco(endereco);

		super.salvar(convocacao);
		return convocacao.getId();
	}

	private ConvocacaoJuizDTO converterParaDto(ConvocacaoJuiz convocacao) {
		ConvocacaoJuizDTO dto = new ConvocacaoJuizDTO();
		dto.setDataPartida(convocacao.getDataPartida());
		dto.setTipoJogo(convocacao.getTipoJogo());
		dto.setNomeJuiz(convocacao.getNomeJuiz());
		dto.setNomeClube(convocacao.getNomeClube());
		dto.setAvaliacao(convocacao.getAvaliacao());
		dto.setId(convocacao.getId());

		if (convocacao.getClube() != null) {
			CadastroClubeDTO clubeDTO = new CadastroClubeDTO();
			clubeDTO.setNome(convocacao.getClube().getNome());
			clubeDTO.setCnpj(convocacao.getClube().getCnpj());
			clubeDTO.setCpf(convocacao.getClube().getCpf());
			clubeDTO.setContato(convocacao.getClube().getContato());
			clubeDTO.setEmail(convocacao.getClube().getEmail());
			clubeDTO.setResponsavel(convocacao.getClube().getResponsavel());
			clubeDTO.setHistorico(convocacao.getClube().getHistorico());
			dto.setClubeDTO(clubeDTO);
		}

		if (convocacao.getEndereco() != null) {
			CadastroEnderecoDTO enderecoDTO = new CadastroEnderecoDTO();
			enderecoDTO.setCep(convocacao.getEndereco().getCep());
			enderecoDTO.setRua(convocacao.getEndereco().getRua());
			enderecoDTO.setNumero(convocacao.getEndereco().getNumero());
			enderecoDTO.setBairro(convocacao.getEndereco().getBairro());
			enderecoDTO.setCidade(convocacao.getEndereco().getCidade());
			enderecoDTO.setEstado(convocacao.getEndereco().getEstado());
			dto.setEnderecoDTO(enderecoDTO);
		}

		return dto;
	}
	
	public List<ConvocacaoJuizDTO> buscarTodosHistoricos(Long idClube) {
	    Clube clube = clubeService.buscar(idClube);
	    List<ConvocacaoJuiz> convocacoes = convocacaoJuizRepository.findByClube(clube);
	    List<ConvocacaoJuizDTO> historicos = new ArrayList<>();

	    for (ConvocacaoJuiz convocacao : convocacoes) {
	        ConvocacaoJuizDTO dto = new ConvocacaoJuizDTO();
	        dto.setNomeJuiz(convocacao.getNomeJuiz());
	        dto.setDataDaConvocacao(convocacao.getDataDaConvocacao());
	        dto.setValor(convocacao.getValor());
	        dto.setTipoJogo(convocacao.getTipoJogo());
	        dto.setIdClube(clube.getId());
			dto.setAvaliacao(convocacao.getAvaliacao());
			dto.setIdJuiz(convocacao.getJuiz().getId());
			dto.setId(convocacao.getId());
	        

	        if (convocacao.getEndereco() != null) {
	            CadastroEnderecoDTO enderecoDTO = new CadastroEnderecoDTO();
	            enderecoDTO.setCep(convocacao.getEndereco().getCep());
	            enderecoDTO.setRua(convocacao.getEndereco().getRua());
	            enderecoDTO.setNumero(convocacao.getEndereco().getNumero());
	            enderecoDTO.setBairro(convocacao.getEndereco().getBairro());
	            enderecoDTO.setCidade(convocacao.getEndereco().getCidade());
	            enderecoDTO.setEstado(convocacao.getEndereco().getEstado());
	            enderecoDTO.setComplemento(convocacao.getEndereco().getComplemento());
	            dto.setEnderecoDTO(enderecoDTO);

	            String enderecoCompleto = String.format(
	                "%s, %s - %s, %s, %s - %s",
	                enderecoDTO.getRua(),
	                enderecoDTO.getNumero(),
	                enderecoDTO.getBairro(),
	                enderecoDTO.getCidade(),
	                enderecoDTO.getEstado(),
	                enderecoDTO.getCep()
	            );
	            dto.setEnderecoCompleto(enderecoCompleto); 
	        }
	        
	        if(convocacao.getJuiz() != null) {
	        	dto.setTipoJuizEnum(convocacao.getJuiz().getTipoJuizEnum());
	        }

	        historicos.add(dto);
	    }
	    return historicos;
	}
	
	public List<ConvocacaoJuizDTO> buscarTodosHistoricosJuiz(Long idJuiz) {
		Juiz juiz = juizService.buscar(idJuiz);
		List<ConvocacaoJuiz> convocacoes = convocacaoJuizRepository.findByJuiz(juiz);
		List<ConvocacaoJuizDTO> historicos = new ArrayList<>();

		for (ConvocacaoJuiz convocacao : convocacoes) {
			ConvocacaoJuizDTO dto = converterParaDto(convocacao);
			dto.setNomeClube(convocacao.getNomeClube());
			dto.setDataDaConvocacao(convocacao.getDataDaConvocacao());
			dto.setValor(convocacao.getValor());
			dto.setTipoJogo(convocacao.getTipoJogo());
			dto.setAvaliacao(convocacao.getAvaliacao());
			dto.setId(convocacao.getId());

			if (convocacao.getEndereco() != null) {
				CadastroEnderecoDTO enderecoDTO = dto.getEnderecoDTO();
				String enderecoCompleto = String.format("%s, %s - %s, %s, %s - %s", enderecoDTO.getRua(),
						enderecoDTO.getNumero(), enderecoDTO.getBairro(), enderecoDTO.getCidade(),
						enderecoDTO.getEstado(), enderecoDTO.getCep());
				dto.setEnderecoCompleto(enderecoCompleto);
			}

			historicos.add(dto);
		}
		return historicos;
	}

	
	public Optional<ConvocacaoJuizDTO> buscarConvocacaoJuizPorId(Long id) {
		return convocacaoJuizRepository.findById(id).map(this::converterParaDto);
	}
	
	public boolean existeConvocacaoNaMesmaData(Long juizId, Date dataPartida) {
		LocalDate dataPartidaLocal = new java.sql.Date(dataPartida.getTime()).toLocalDate();

		List<ConvocacaoJuiz> convocacoes = convocacaoJuizRepository.findByJuizId(juizId);

		for (ConvocacaoJuiz convocacao : convocacoes) {
			LocalDate dataConvocacao = new java.sql.Date(convocacao.getDataPartida().getTime()).toLocalDate();

			if (dataPartidaLocal.isEqual(dataConvocacao)) {
				return true;
			}
		}
		return false;
	}
	
	public List<ConvocacaoJuiz> buscarEntidadePorJuiz(Long idJuiz) {
		return convocacaoJuizRepository.findByJuizId(idJuiz);
	}

	public Double obterMediaAvaliacao(Long idJuiz) {
	    return convocacaoJuizRepository.calcularMediaAvaliacao(idJuiz);
	}
	
	
	@Transactional
	public void salvarAvaliacaoConvocacao(Long idConvocacao, int nota) {
		ConvocacaoJuiz convocacao = convocacaoJuizRepository.findById(idConvocacao).get();
		convocacao.setAvaliacao(nota);
		convocacaoJuizRepository.save(convocacao);

	}

}
