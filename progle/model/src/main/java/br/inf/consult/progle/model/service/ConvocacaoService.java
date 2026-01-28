package br.inf.consult.progle.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.progle.model.dto.CadastroClubeDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.dto.CadastroJuizDTO;
import br.inf.consult.progle.model.dto.ConvocacaoDTO;
import br.inf.consult.progle.model.entity.Convocacao;
import br.inf.consult.progle.model.entity.Endereco;
import br.inf.consult.progle.model.repository.ConvocacaoRepository;


@Service
public class ConvocacaoService extends GenericCrudService<Convocacao, Long, ConvocacaoRepository> {
	
	@Autowired
	private ConvocacaoRepository convocacaoRepository;
	
	public Long salvarConvocacao(ConvocacaoDTO novaConvocacao) {
	    Convocacao convocacao = new Convocacao();
	    convocacao.setDataPartida(novaConvocacao.getDataPartida());
	    convocacao.setTipoJogo(novaConvocacao.getTipoJogo());
	    

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

	private ConvocacaoDTO converterParaDto(Convocacao convocacao) {
	    ConvocacaoDTO dto = new ConvocacaoDTO();
	    dto.setDataPartida(convocacao.getDataPartida());
	    dto.setTipoJogo(convocacao.getTipoJogo());


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

	    if (convocacao.getJogador() != null) {
	        CadastroJogadorDTO jogadorDTO = new CadastroJogadorDTO();
	        jogadorDTO.setNome(convocacao.getJogador().getNome());
	        jogadorDTO.setApelido(convocacao.getJogador().getApelido());
	        jogadorDTO.setCpf(convocacao.getJogador().getCpf());
	        jogadorDTO.setEmail(convocacao.getJogador().getEmail());
	        jogadorDTO.setSexo(convocacao.getJogador().getSexo());
	        jogadorDTO.setContato(convocacao.getJogador().getContato());
	        jogadorDTO.setHistorico(convocacao.getJogador().getHistorico());
	        jogadorDTO.setDataNascimento(convocacao.getJogador().getDataNascimento());
	        jogadorDTO.setNivel(convocacao.getJogador().getNivel());
	        jogadorDTO.setValorAluguel(convocacao.getJogador().getValorAluguel());
	        dto.setJogadorDTO(jogadorDTO);
	    }

	    if (convocacao.getJuiz() != null) {
	        CadastroJuizDTO juizDTO = new CadastroJuizDTO();
	        juizDTO.setNome(convocacao.getJuiz().getNome());
	        juizDTO.setCpf(convocacao.getJuiz().getCpf());
	        juizDTO.setContato(convocacao.getJuiz().getContato());
	        juizDTO.setEmail(convocacao.getJuiz().getEmail());
	        juizDTO.setHistorico(convocacao.getJuiz().getHistorico());
	        juizDTO.setNivel(convocacao.getJuiz().getNivel());
	        juizDTO.setTipoJuiz(convocacao.getJuiz().getTipoJuizEnum().toString());
	        dto.setJuizDTO(juizDTO);
	    }

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
	    }

	    return dto;
	}
	
	public Optional<ConvocacaoDTO> buscarConvocacaoPorId(Long id) {
	    return convocacaoRepository.findById(id).map(this::converterParaDto);
	}
	
}
