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
import br.inf.consult.progle.model.dto.ConvocacaoJogadorDTO;
import br.inf.consult.progle.model.entity.Clube;
import br.inf.consult.progle.model.entity.ConvocacaoJogador;
import br.inf.consult.progle.model.entity.Endereco;
import br.inf.consult.progle.model.entity.Jogador;
import br.inf.consult.progle.model.repository.ConvocacaoJogadorRepository;

@Service
public class ConvocacaoJogadorService extends GenericCrudService<ConvocacaoJogador, Long, ConvocacaoJogadorRepository> {

	@Autowired
	private ConvocacaoJogadorRepository convocacaoJogadorRepository;

	@Autowired
	private ClubeService clubeService;

	@Autowired
	private JogadorService jogadorService;

	public Long salvarConvocacao(ConvocacaoJogadorDTO novaConvocacao, Long idClube, Long idJogador) {
		Clube clube = clubeService.buscar(idClube);
		Jogador jogador = jogadorService.buscar(idJogador);

		ConvocacaoJogador convocacao = new ConvocacaoJogador();
		convocacao.setDataPartida(novaConvocacao.getDataPartida());
		convocacao.setTipoJogo(novaConvocacao.getTipoJogo());
		convocacao.setNomeJogador(jogador.getNome());
		convocacao.setNomeClube(clube.getNome());
		convocacao.setValor(novaConvocacao.getJogadorDTO().getValorAluguel());
		convocacao.setClube(clube);
		convocacao.setJogador(jogador);
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

	private ConvocacaoJogadorDTO converterParaDto(ConvocacaoJogador convocacao) {
		ConvocacaoJogadorDTO dto = new ConvocacaoJogadorDTO();
		dto.setDataPartida(convocacao.getDataPartida());
		dto.setTipoJogo(convocacao.getTipoJogo());
		dto.setNomeJogador(convocacao.getNomeJogador());
		dto.setNomeClube(convocacao.getNomeClube());
		dto.setAvaliacao(convocacao.getAvaliacao());
		dto.setPosicaoCampo(convocacao.getJogador().getPosicaoCampo());
		dto.setPosicaoFutsal(convocacao.getJogador().getPosicaoFutsal());
		dto.setPosicoesSociety(convocacao.getJogador().getPosicoesSociety());
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

	public List<ConvocacaoJogadorDTO> buscarTodosHistoricos(Long idClube) {
		Clube clube = clubeService.buscar(idClube);
		List<ConvocacaoJogador> convocacoes = convocacaoJogadorRepository.findByClube(clube);
		List<ConvocacaoJogadorDTO> historicos = new ArrayList<>();

		for (ConvocacaoJogador convocacao : convocacoes) {
			ConvocacaoJogadorDTO dto = converterParaDto(convocacao);
			dto.setNomeJogador(convocacao.getNomeJogador());
			dto.setDataDaConvocacao(convocacao.getDataDaConvocacao());
			dto.setValor(convocacao.getValor());
			dto.setTipoJogo(convocacao.getTipoJogo());
			dto.setAvaliacao(convocacao.getAvaliacao());
			dto.setIdJogador(convocacao.getJogador().getId());
			dto.setId(convocacao.getId());

			if (convocacao.getEndereco() != null) {
				CadastroEnderecoDTO enderecoDTO = dto.getEnderecoDTO();
				String enderecoCompleto = String.format("%s, %s - %s, %s, %s - %s", enderecoDTO.getRua(),
						enderecoDTO.getNumero(), enderecoDTO.getBairro(), enderecoDTO.getCidade(),
						enderecoDTO.getEstado(), enderecoDTO.getCep());
				dto.setEnderecoCompleto(enderecoCompleto);
			}

			if (convocacao.getJogador() != null) {
				dto.setPosicaoCampo(convocacao.getJogador().getPosicaoCampo());
				dto.setPosicaoFutsal(convocacao.getJogador().getPosicaoFutsal());
				dto.setPosicoesSociety(convocacao.getJogador().getPosicoesSociety());
			}

			historicos.add(dto);
		}
		return historicos;
	}

	public List<ConvocacaoJogadorDTO> buscarTodosHistoricosJogador(Long idJogador) {
		Jogador jogador = jogadorService.buscar(idJogador);
		List<ConvocacaoJogador> convocacoes = convocacaoJogadorRepository.findByJogador(jogador);
		List<ConvocacaoJogadorDTO> historicos = new ArrayList<>();

		for (ConvocacaoJogador convocacao : convocacoes) {
			ConvocacaoJogadorDTO dto = converterParaDto(convocacao);
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

	public Optional<ConvocacaoJogadorDTO> buscarConvocacaoJogadorPorId(Long id) {
		return convocacaoJogadorRepository.findById(id).map(this::converterParaDto);
	}

	public boolean existeConvocacaoNaMesmaData(Long jogadorId, Date dataPartida) {
		LocalDate dataPartidaLocal = new java.sql.Date(dataPartida.getTime()).toLocalDate();

		List<ConvocacaoJogador> convocacoes = convocacaoJogadorRepository.findByJogadorId(jogadorId);

		for (ConvocacaoJogador convocacao : convocacoes) {
			LocalDate dataConvocacao = new java.sql.Date(convocacao.getDataPartida().getTime()).toLocalDate();

			if (dataPartidaLocal.isEqual(dataConvocacao)) {
				return true;
			}
		}
		return false;
	}

	public List<ConvocacaoJogador> buscarEntidadePorJogador(Long idJogador) {
		return convocacaoJogadorRepository.findByJogadorId(idJogador);
	}

	public Double obterMediaAvaliacao(Long idJogador) {
	    return convocacaoJogadorRepository.calcularMediaAvaliacao(idJogador);
	}
	
	
	@Transactional
	public void salvarAvaliacaoConvocacao(Long idConvocacao, int nota) {
		ConvocacaoJogador convocacao = convocacaoJogadorRepository.findById(idConvocacao).get();
		convocacao.setAvaliacao(nota);
		convocacaoJogadorRepository.save(convocacao);

	}

}