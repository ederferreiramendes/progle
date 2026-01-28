package br.inf.consult.progle.model.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.enums.TipoJuizEnum;
import br.inf.consult.enums.TipoUsuarioEnum;
import br.inf.consult.progle.model.dto.AluguelJuizDTO;
import br.inf.consult.progle.model.dto.CadastroDadoBancarioDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.CadastroJuizDTO;
import br.inf.consult.progle.model.dto.UsuarioSistemaDTO;
import br.inf.consult.progle.model.entity.DadoBancario;
import br.inf.consult.progle.model.entity.Endereco;
import br.inf.consult.progle.model.entity.Juiz;
import br.inf.consult.progle.model.entity.UsuarioSistema;
import br.inf.consult.progle.model.repository.ConvocacaoJuizRepository;
import br.inf.consult.progle.model.repository.JuizRepository;
import br.inf.consult.progle.model.repository.UsuarioSistemaRepository;
import br.inf.consult.progle.util.FacesUtil;

@Service
public class JuizService extends GenericCrudService<Juiz, Long, JuizRepository> {

	@Autowired
	private JuizRepository juizRepository;
	
	@Autowired
	private ConvocacaoJuizRepository convocacaoJuizRepository;

	@Autowired
	private UsuarioSistemaRepository usuarioSistemaRepository;

	private CadastroJuizDTO converterParaDto(Juiz juiz) {
		CadastroJuizDTO dto = new CadastroJuizDTO();
		dto.setNome(juiz.getNome());
		dto.setCpf(juiz.getCpf());
		dto.setRaioAtuacao(juiz.getRaioAtuacao());
		dto.setTipoJuizEnum(juiz.getTipoJuizEnum().toArray(new TipoJuizEnum[0]));
		dto.setHistorico(juiz.getHistorico());
		dto.setContato(juiz.getContato());
		dto.setEmail(juiz.getEmail());
		dto.setValorAluguel(juiz.getValorAluguel());
		dto.setAvaliacao(juiz.getAvaliacao());
		dto.setValorAluguel(juiz.getValorAluguel());
		dto.setFotoCaminho(juiz.getFotoCaminho());


		if (juiz.getEndereco() != null) {
			CadastroEnderecoDTO enderecoDTO = new CadastroEnderecoDTO();
			enderecoDTO.setCep(juiz.getEndereco().getCep());
			enderecoDTO.setRua(juiz.getEndereco().getRua());
			enderecoDTO.setNumero(juiz.getEndereco().getNumero());
			enderecoDTO.setBairro(juiz.getEndereco().getBairro());
			enderecoDTO.setCidade(juiz.getEndereco().getCidade());
			enderecoDTO.setEstado(juiz.getEndereco().getEstado());
			enderecoDTO.setComplemento(juiz.getEndereco().getComplemento());
			dto.setEnderecoDTO(enderecoDTO);
		}

		if (juiz.getDadoBancario() != null) {
			CadastroDadoBancarioDTO dadoBancarioDTO = new CadastroDadoBancarioDTO();
			dadoBancarioDTO.setBanco(juiz.getDadoBancario().getBanco());
			dadoBancarioDTO.setAgencia(juiz.getDadoBancario().getAgencia());
			dadoBancarioDTO.setTipoChavePix(juiz.getDadoBancario().getTipoChavePixEnum());
			dadoBancarioDTO.setChavePix(juiz.getDadoBancario().getChavePix());
			dto.setDadosBancariosDTO(dadoBancarioDTO);
		}

		if (juiz.getUsuarioSistema() != null) {
			UsuarioSistemaDTO usuarioSistemaDTO = new UsuarioSistemaDTO();
			usuarioSistemaDTO.setCpf(juiz.getUsuarioSistema().getCpf());
			usuarioSistemaDTO.setSenha(juiz.getUsuarioSistema().getSenha());
			dto.setUsuarioSistemaDTO(usuarioSistemaDTO);
		}

		return dto;
	}

	public void salvar(CadastroJuizDTO cadastroJuizDTO) {

		if (usuarioSistemaRepository.existsByCpf(cadastroJuizDTO.getCpf())) {
			FacesUtil.registrarErro("msg.erro.cpf.cadastrado");
			return;
		}

		DadoBancario dadoBancario = new DadoBancario();
		dadoBancario.setAgencia(cadastroJuizDTO.getDadosBancariosDTO().getAgencia());
		dadoBancario.setNumeroConta(cadastroJuizDTO.getDadosBancariosDTO().getNumeroConta());
		dadoBancario.setBanco(cadastroJuizDTO.getDadosBancariosDTO().getBanco());
		dadoBancario.setChavePix(cadastroJuizDTO.getDadosBancariosDTO().getChavePix());
		dadoBancario.setTipoChavePixEnum(cadastroJuizDTO.getDadosBancariosDTO().getTipoChavePix());

		Juiz juiz = new Juiz();
		juiz.setCpf(cadastroJuizDTO.getCpf());
		juiz.setEmail(cadastroJuizDTO.getEmail());
		juiz.setNome(cadastroJuizDTO.getNome());
		juiz.setContato(cadastroJuizDTO.getContato());
		juiz.setNivel(cadastroJuizDTO.getNivel());
		juiz.setValorAluguel(cadastroJuizDTO.getValorAluguel());
		juiz.setFotoCaminho(cadastroJuizDTO.getFotoCaminho());


		juiz.setHistorico(cadastroJuizDTO.getHistorico());

		juiz.setRaioAtuacao(cadastroJuizDTO.getRaioAtuacao());

		juiz.setTipoJuizEnum(new ArrayList<>(Arrays.asList(cadastroJuizDTO.getTipoJuizEnum())));

		Endereco endereco = new Endereco();
		endereco.setRua(cadastroJuizDTO.getEnderecoDTO().getRua());
		endereco.setNumero(cadastroJuizDTO.getEnderecoDTO().getNumero());
		endereco.setCidade(cadastroJuizDTO.getEnderecoDTO().getCidade());
		endereco.setEstado(cadastroJuizDTO.getEnderecoDTO().getEstado());
		endereco.setCep(cadastroJuizDTO.getEnderecoDTO().getCep());
		endereco.setBairro(cadastroJuizDTO.getEnderecoDTO().getBairro());
		endereco.setComplemento(cadastroJuizDTO.getEnderecoDTO().getComplemento());

		juiz.setEndereco(endereco);
		juiz.setDadoBancario(dadoBancario);

		Juiz juizSalvoJuiz = super.salvar(juiz);

		UsuarioSistema usuarioSistema = new UsuarioSistema();
		usuarioSistema.setCpf(cadastroJuizDTO.getCpf());
		usuarioSistema.setSenha(cadastroJuizDTO.getSenha());
		usuarioSistema.setTipoUsuarioEnum(TipoUsuarioEnum.JUIZ);
		usuarioSistema.setJuiz(juizSalvoJuiz);

		usuarioSistemaRepository.save(usuarioSistema);
		super.salvar(juiz);

	}

	public Optional<CadastroJuizDTO> buscarJuizPorId(Long id) {
		return juizRepository.findById(id).map(this::converterParaDto);
	}

	public boolean verificarCpfExistente(String cpf) {
		return juizRepository.findByCpf(cpf).isPresent();
	}

	public List<AluguelJuizDTO> buscarJuizes(String nome) {

	    atualizarMediaDeTodosJuizes();

	    List<Juiz> juizes = (nome != null && !nome.isBlank())
	            ? juizRepository.findByNomeContainingIgnoreCase(nome)
	            : juizRepository.findAll();

	    List<AluguelJuizDTO> listaRetorno = new ArrayList<>();
	    for (Juiz juiz : juizes) {
	        AluguelJuizDTO aluguelJuizDTO = new AluguelJuizDTO();
	        aluguelJuizDTO.setIdJuiz(juiz.getId());
	        aluguelJuizDTO.setNome(juiz.getNome());
	        aluguelJuizDTO.setContato(juiz.getContato());
	        aluguelJuizDTO.setAvaliacao(juiz.getAvaliacao()); // agora atualizado
	        aluguelJuizDTO.setHistorico(juiz.getHistorico());
	        aluguelJuizDTO.setValor(juiz.getValorAluguel());
	        aluguelJuizDTO.setFoto(juiz.getFotoCaminho());
	        aluguelJuizDTO.setRaioAtuacao(juiz.getRaioAtuacao());
	        listaRetorno.add(aluguelJuizDTO);
	    }
	    return listaRetorno;
	}
	
	
	public CadastroJuizDTO editar(Long id, CadastroJuizDTO cadastroJuizDTO) {
		Juiz juizExistente = juizRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Juiz com ID " + id + " não encontrado"));

		juizExistente.setCpf(cadastroJuizDTO.getCpf());
		juizExistente.setNome(cadastroJuizDTO.getNome());
		juizExistente.setContato(cadastroJuizDTO.getContato());
		juizExistente.setValorAluguel(cadastroJuizDTO.getValorAluguel());
		juizExistente.setHistorico(cadastroJuizDTO.getHistorico());
		juizExistente.setNivel(cadastroJuizDTO.getNivel());
		juizExistente.setRaioAtuacao(cadastroJuizDTO.getRaioAtuacao());
		juizExistente.setFotoCaminho(cadastroJuizDTO.getFotoCaminho());
		
		juizExistente.setTipoJuizEnum(new ArrayList<>(Arrays.asList(cadastroJuizDTO.getTipoJuizEnum())));

		if (juizExistente.getEndereco() == null) {
			juizExistente.setEndereco(new Endereco());
		}
		Endereco endereco = juizExistente.getEndereco();
		endereco.setRua(cadastroJuizDTO.getEnderecoDTO().getRua());
		endereco.setNumero(cadastroJuizDTO.getEnderecoDTO().getNumero());
		endereco.setCidade(cadastroJuizDTO.getEnderecoDTO().getCidade());
		endereco.setEstado(cadastroJuizDTO.getEnderecoDTO().getEstado());
		endereco.setCep(cadastroJuizDTO.getEnderecoDTO().getCep());
		endereco.setBairro(cadastroJuizDTO.getEnderecoDTO().getBairro());
		endereco.setComplemento(cadastroJuizDTO.getEnderecoDTO().getComplemento());

		if (juizExistente.getDadoBancario() == null) {
			juizExistente.setDadoBancario(new DadoBancario());
		}
		DadoBancario dadoBancario = juizExistente.getDadoBancario();
		dadoBancario.setBanco(cadastroJuizDTO.getDadosBancariosDTO().getBanco());
		dadoBancario.setAgencia(cadastroJuizDTO.getDadosBancariosDTO().getAgencia());
		dadoBancario.setNumeroConta(cadastroJuizDTO.getDadosBancariosDTO().getNumeroConta());
		dadoBancario.setChavePix(cadastroJuizDTO.getDadosBancariosDTO().getChavePix());
		dadoBancario.setTipoChavePixEnum(cadastroJuizDTO.getDadosBancariosDTO().getTipoChavePix());

		Juiz juizAtualizado = juizRepository.save(juizExistente);

		return converterParaDto(juizAtualizado);
	}
	
	@Transactional
	public void atualizarMediaAvaliacao(Long juizId) {
	    Double media = convocacaoJuizRepository.calcularMediaAvaliacao(juizId); 
	    if (media != null) {
	        juizRepository.findById(juizId).ifPresent(juiz -> {
	            juiz.setAvaliacao(Math.round(media * 10.0) / 10.0);
	            juizRepository.save(juiz);
	        });
	    }
	}
	
	public Long contarAvaliacoes(int estrelas) {
	    return juizRepository.contarAvaliacoesPorEstrelas(estrelas);
	}


    @Transactional
    public void atualizarMediaDeTodosJuizes() {
        List<Juiz> juizes = juizRepository.findAll();
        for (Juiz juiz : juizes) {
            atualizarMediaAvaliacao(juiz.getId());
        }
    }

}
