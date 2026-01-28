package br.inf.consult.progle.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.enums.PosicaoCampoEnum;
import br.inf.consult.enums.PosicaoFutsalEnum;
import br.inf.consult.enums.PosicaoSocietyEnum;
import br.inf.consult.enums.TipoUsuarioEnum;
import br.inf.consult.progle.model.dto.AluguelJogadorDTO;
import br.inf.consult.progle.model.dto.CadastroDadoBancarioDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.dto.UsuarioSistemaDTO;
import br.inf.consult.progle.model.entity.DadoBancario;
import br.inf.consult.progle.model.entity.Endereco;
import br.inf.consult.progle.model.entity.Jogador;
import br.inf.consult.progle.model.entity.UsuarioSistema;
import br.inf.consult.progle.model.repository.ConvocacaoJogadorRepository;
import br.inf.consult.progle.model.repository.JogadorRepository;
import br.inf.consult.progle.model.repository.UsuarioSistemaRepository;
import br.inf.consult.progle.util.FacesUtil;


@Service
public class JogadorService extends GenericCrudService<Jogador, Long, JogadorRepository> {

	@Autowired
	private JogadorRepository jogadorRepository;
	
	@Autowired
	private ConvocacaoJogadorRepository convocacaoJogadorRepository;
	
	@Autowired UsuarioSistemaRepository usuarioSistemaRepository;
	
	private CadastroJogadorDTO converterParaDto(Jogador jogador) {
		CadastroJogadorDTO dto = new CadastroJogadorDTO();
		dto.setNome(jogador.getNome());
		dto.setApelido(jogador.getApelido());
		dto.setCpf(jogador.getCpf());
		dto.setSexo(jogador.getSexo());
		dto.setPosicoesCampo(jogador.getPosicaoCampo().toArray(new PosicaoCampoEnum[0]));
		dto.setPosicoesFutsal(jogador.getPosicaoFutsal().toArray(new PosicaoFutsalEnum[0]));
		dto.setPosicoesSociety(jogador.getPosicoesSociety().toArray(new PosicaoSocietyEnum[0]));
		dto.setRaioAtuacao(jogador.getRaioAtuacao());
		dto.setDataNascimento(jogador.getDataNascimento());
		dto.setNivel(jogador.getNivel());
		dto.setHistorico(jogador.getHistorico());
		dto.setContato(jogador.getContato());
		dto.setEmail(jogador.getEmail());
		dto.setValorAluguel(jogador.getValorAluguel());
		dto.setAvaliacao(jogador.getAvaliacao());
		dto.setFotoCaminho(jogador.getFotoCaminho());
		
		if (jogador.getEndereco() != null) {
			CadastroEnderecoDTO enderecoDTO = new CadastroEnderecoDTO();
			enderecoDTO.setCep(jogador.getEndereco().getCep());
			enderecoDTO.setRua(jogador.getEndereco().getRua());
			enderecoDTO.setNumero(jogador.getEndereco().getNumero());
			enderecoDTO.setBairro(jogador.getEndereco().getBairro());
			enderecoDTO.setCidade(jogador.getEndereco().getCidade());
			enderecoDTO.setEstado(jogador.getEndereco().getEstado());
			enderecoDTO.setComplemento(jogador.getEndereco().getComplemento());
			dto.setEnderecoDTO(enderecoDTO);
		}
		
		if (jogador.getDadoBancario() != null) {
			CadastroDadoBancarioDTO dadoBancarioDTO = new CadastroDadoBancarioDTO();
			dadoBancarioDTO.setBanco(jogador.getDadoBancario().getBanco());
			dadoBancarioDTO.setAgencia(jogador.getDadoBancario().getAgencia());
			dadoBancarioDTO.setTipoChavePix(jogador.getDadoBancario().getTipoChavePixEnum());
			dadoBancarioDTO.setChavePix(jogador.getDadoBancario().getChavePix());
			dto.setDadosBancariosDTO(dadoBancarioDTO);
		}
		
		if(jogador.getUsuarioSistema() != null) {
			UsuarioSistemaDTO usuarioSistemaDTO = new UsuarioSistemaDTO();
			usuarioSistemaDTO.setCpf(jogador.getUsuarioSistema().getCpf());
			usuarioSistemaDTO.setSenha(jogador.getUsuarioSistema().getSenha());
			dto.setUsuarioSistemaDTO(usuarioSistemaDTO);
		}
		
		return dto;
	}

	public void salvar(CadastroJogadorDTO cadastroJogadorDTO) {
		
		if (usuarioSistemaRepository.existsByCpf(cadastroJogadorDTO.getCpf())) {
			 FacesUtil.registrarErro("msg.erro.cpf.cadastrado");
			 return;
	    }

		Jogador jogador = new Jogador();
		jogador.setNome(cadastroJogadorDTO.getNome());
		jogador.setApelido(cadastroJogadorDTO.getApelido());
		jogador.setCpf(cadastroJogadorDTO.getCpf());
		jogador.setSexo(cadastroJogadorDTO.getSexo());
		jogador.setDataNascimento(cadastroJogadorDTO.getDataNascimento());
		jogador.setNivel(cadastroJogadorDTO.getNivel());
		jogador.setHistorico(cadastroJogadorDTO.getHistorico());
		jogador.setEmail(cadastroJogadorDTO.getEmail());

		jogador.setPosicaoCampo(new ArrayList<>(Arrays.asList(cadastroJogadorDTO.getPosicoesCampo())));
		jogador.setPosicaoFutsal(new ArrayList<>(Arrays.asList(cadastroJogadorDTO.getPosicoesFutsal())));
		jogador.setPosicoesSociety(new ArrayList<>(Arrays.asList(cadastroJogadorDTO.getPosicoesSociety())));
		jogador.setRaioAtuacao(cadastroJogadorDTO.getRaioAtuacao());

		jogador.setContato(cadastroJogadorDTO.getContato());
		jogador.setValorAluguel(cadastroJogadorDTO.getValorAluguel());
		jogador.setFotoCaminho(cadastroJogadorDTO.getFotoCaminho());
		
		DadoBancario dadoBancario = new DadoBancario();
		dadoBancario.setBanco(cadastroJogadorDTO.getDadosBancariosDTO().getBanco());
		dadoBancario.setAgencia(cadastroJogadorDTO.getDadosBancariosDTO().getAgencia());
		dadoBancario.setNumeroConta(cadastroJogadorDTO.getDadosBancariosDTO().getNumeroConta());
		dadoBancario.setChavePix(cadastroJogadorDTO.getDadosBancariosDTO().getChavePix());
		dadoBancario.setTipoChavePixEnum(cadastroJogadorDTO.getDadosBancariosDTO().getTipoChavePix());


		Endereco endereco = new Endereco();
		endereco.setRua(cadastroJogadorDTO.getEnderecoDTO().getRua());
		endereco.setNumero(cadastroJogadorDTO.getEnderecoDTO().getNumero());
		endereco.setCidade(cadastroJogadorDTO.getEnderecoDTO().getCidade());
		endereco.setEstado(cadastroJogadorDTO.getEnderecoDTO().getEstado());
		endereco.setCep(cadastroJogadorDTO.getEnderecoDTO().getCep());
		endereco.setBairro(cadastroJogadorDTO.getEnderecoDTO().getBairro());
		endereco.setComplemento(cadastroJogadorDTO.getEnderecoDTO().getComplemento());

		jogador.setEndereco(endereco);
		jogador.setDadoBancario(dadoBancario);
		
		Jogador jogadorSalvo = super.salvar(jogador);
		
		UsuarioSistema usuarioSistema = new UsuarioSistema();
        usuarioSistema.setCpf(cadastroJogadorDTO.getCpf());
        usuarioSistema.setSenha(cadastroJogadorDTO.getSenha());
        usuarioSistema.setTipoUsuarioEnum(TipoUsuarioEnum.JOGADOR);
        usuarioSistema.setJogador(jogadorSalvo);

        usuarioSistemaRepository.save(usuarioSistema);

		super.salvar(jogador);
	}
	
	public Optional<CadastroJogadorDTO> buscarJogadorPorId(Long id) {
		return jogadorRepository.findById(id).map(this::converterParaDto);
	}
	
	public boolean verificarCpfExistente(String cpf) {
	    return jogadorRepository.findByCpf(cpf).isPresent();
	}
	

	public List<AluguelJogadorDTO> buscarJogadores(String nome) {
	    List<Jogador> jogadores;
	    
	    if (nome != null && !nome.isBlank()) {
	        jogadores = jogadorRepository.findByNomeContainingIgnoreCase(nome);
	    } else {
	        jogadores = jogadorRepository.findAll();
	    }

	    List<AluguelJogadorDTO> listaRetorno = new ArrayList<>();

	    for (Jogador jogador : jogadores) {
	    	atualizarMediaDeTodosJogadores();

	        AluguelJogadorDTO aluguelJogadorDTO = new AluguelJogadorDTO();
	        aluguelJogadorDTO.setIdJogador(jogador.getId());
	        aluguelJogadorDTO.setNome(jogador.getNome());
	        aluguelJogadorDTO.setContato(jogador.getContato());
	        aluguelJogadorDTO.setAvaliacao(jogador.getAvaliacao()); 
	        aluguelJogadorDTO.setValor(jogador.getValorAluguel());
	        aluguelJogadorDTO.setHistorico(jogador.getHistorico());
	        aluguelJogadorDTO.setFoto(jogador.getFotoCaminho());
	        aluguelJogadorDTO.setRaioAtuacao(jogador.getRaioAtuacao());

	        listaRetorno.add(aluguelJogadorDTO);
	    }

	    return listaRetorno;
	}

	
	public CadastroJogadorDTO editar(Long id, CadastroJogadorDTO cadastroJogadorDTO) {
	    Jogador jogadorExistente = jogadorRepository.findById(id)
	            .orElseThrow(() -> new IllegalArgumentException("Jogador com ID " + id + " não encontrado"));

	    jogadorExistente.setCpf(cadastroJogadorDTO.getCpf());
	    jogadorExistente.setNome(cadastroJogadorDTO.getNome());
	    jogadorExistente.setApelido(cadastroJogadorDTO.getApelido());
	    jogadorExistente.setEmail(cadastroJogadorDTO.getEmail());
	    jogadorExistente.setSexo(cadastroJogadorDTO.getSexo());
	    jogadorExistente.setDataNascimento(cadastroJogadorDTO.getDataNascimento());
	    jogadorExistente.setContato(cadastroJogadorDTO.getContato());
	    jogadorExistente.setValorAluguel(cadastroJogadorDTO.getValorAluguel());
	    jogadorExistente.setHistorico(cadastroJogadorDTO.getHistorico());
	    jogadorExistente.setNivel(cadastroJogadorDTO.getNivel());
	    jogadorExistente.setAvaliacao(cadastroJogadorDTO.getAvaliacao());
	    jogadorExistente.setFotoCaminho(cadastroJogadorDTO.getFotoCaminho());
	    jogadorExistente.setRaioAtuacao(cadastroJogadorDTO.getRaioAtuacao());		
	    
	    jogadorExistente.setPosicaoCampo(new ArrayList<>(Arrays.asList(cadastroJogadorDTO.getPosicoesCampo())));
	    jogadorExistente.setPosicaoFutsal(new ArrayList<>(Arrays.asList(cadastroJogadorDTO.getPosicoesFutsal())));
	    jogadorExistente.setPosicoesSociety(new ArrayList<>(Arrays.asList(cadastroJogadorDTO.getPosicoesSociety())));

	    if (jogadorExistente.getEndereco() == null) {
	        jogadorExistente.setEndereco(new Endereco());
	    }
	    Endereco endereco = jogadorExistente.getEndereco();
	    endereco.setRua(cadastroJogadorDTO.getEnderecoDTO().getRua());
	    endereco.setNumero(cadastroJogadorDTO.getEnderecoDTO().getNumero());
	    endereco.setCidade(cadastroJogadorDTO.getEnderecoDTO().getCidade());
	    endereco.setEstado(cadastroJogadorDTO.getEnderecoDTO().getEstado());
	    endereco.setCep(cadastroJogadorDTO.getEnderecoDTO().getCep());
	    endereco.setBairro(cadastroJogadorDTO.getEnderecoDTO().getBairro());
	    endereco.setComplemento(cadastroJogadorDTO.getEnderecoDTO().getComplemento());
	    
	    if(jogadorExistente.getDadoBancario() == null) {
	    	jogadorExistente.setDadoBancario(new DadoBancario());
	    }
	    
	    DadoBancario dadoBancario = jogadorExistente.getDadoBancario();
	    dadoBancario.setBanco(cadastroJogadorDTO.getDadosBancariosDTO().getBanco());
		dadoBancario.setAgencia(cadastroJogadorDTO.getDadosBancariosDTO().getAgencia());
		dadoBancario.setNumeroConta(cadastroJogadorDTO.getDadosBancariosDTO().getNumeroConta());
		dadoBancario.setChavePix(cadastroJogadorDTO.getDadosBancariosDTO().getChavePix());
		dadoBancario.setTipoChavePixEnum(cadastroJogadorDTO.getDadosBancariosDTO().getTipoChavePix());

	    Jogador jogadorAtualizado = jogadorRepository.save(jogadorExistente);

	    return converterParaDto(jogadorAtualizado);
	}
	
	@Transactional
	public void atualizarMediaAvaliacao(Long jogadorId) {
	    Double media = convocacaoJogadorRepository.calcularMediaAvaliacao(jogadorId); // Buscar a média correta
	    if (media != null) {
	        jogadorRepository.findById(jogadorId).ifPresent(jogador -> {
	            jogador.setAvaliacao(Math.round(media * 10.0) / 10.0); // Arredonda para 1 casa decimal
	            jogadorRepository.save(jogador);
	        });
	    }
	}
	
	public Long contarAvaliacoes(int estrelas) {
	    return jogadorRepository.contarAvaliacoesPorEstrelas(estrelas);
	}


    @Transactional
    public void atualizarMediaDeTodosJogadores() {
        List<Jogador> jogadores = jogadorRepository.findAll();
        for (Jogador jogador : jogadores) {
            atualizarMediaAvaliacao(jogador.getId());
        }
    }
}
