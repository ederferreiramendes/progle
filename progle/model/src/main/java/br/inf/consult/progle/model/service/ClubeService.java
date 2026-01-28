package br.inf.consult.progle.model.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.enums.TipoUsuarioEnum;
import br.inf.consult.progle.model.dto.CadastroClubeDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.entity.Clube;
import br.inf.consult.progle.model.entity.Endereco;
import br.inf.consult.progle.model.entity.UsuarioSistema;
import br.inf.consult.progle.model.repository.ClubeRepository;
import br.inf.consult.progle.model.repository.UsuarioSistemaRepository;
import br.inf.consult.progle.util.FacesUtil;

@Service
public class ClubeService extends GenericCrudService<Clube, Long, ClubeRepository> {

	@Autowired
	private ClubeRepository clubeRepository;
	@Autowired
	private UsuarioSistemaRepository usuarioSistemaRepository;
	
	private CadastroClubeDTO converterParaDto(Clube clube) {
		CadastroClubeDTO dto = new CadastroClubeDTO();
		dto.setNome(clube.getNome());
		dto.setCnpj(clube.getCnpj());
		dto.setCpf(clube.getCpf());
		dto.setResponsavel(clube.getResponsavel());
		dto.setContato(clube.getContato());
		dto.setEmail(clube.getEmail());
		dto.setHistorico(clube.getHistorico());
		dto.setFotoCaminho(clube.getFotoCaminho());

		if (clube.getEndereco() != null) {
			CadastroEnderecoDTO enderecoDTO = new CadastroEnderecoDTO();
			enderecoDTO.setCep(clube.getEndereco().getCep());
			enderecoDTO.setRua(clube.getEndereco().getRua());
			enderecoDTO.setNumero(clube.getEndereco().getNumero());
			enderecoDTO.setBairro(clube.getEndereco().getBairro());
			enderecoDTO.setCidade(clube.getEndereco().getCidade());
			enderecoDTO.setEstado(clube.getEndereco().getEstado());
			enderecoDTO.setComplemento(clube.getEndereco().getComplemento());
			dto.setEnderecoDTO(enderecoDTO);
		}

		return dto;
	}

	public void salvar(CadastroClubeDTO cadastroClubeDTO) {
		
		if (usuarioSistemaRepository.existsByCpf(cadastroClubeDTO.getCpf())) {
			 FacesUtil.registrarErro("msg.erro.cpf.cadastrado");
			 return;
	    }
		
		Clube clube = new Clube();
		clube.setCpf(cadastroClubeDTO.getCpf());
		clube.setCnpj(cadastroClubeDTO.getCnpj());
		clube.setNome(cadastroClubeDTO.getNome());
		clube.setResponsavel(cadastroClubeDTO.getResponsavel());
		clube.setContato(cadastroClubeDTO.getContato());
		clube.setEmail(cadastroClubeDTO.getEmail());
		clube.setFotoCaminho(cadastroClubeDTO.getFotoCaminho());

		clube.setHistorico(cadastroClubeDTO.getHistorico());

		Endereco endereco = new Endereco();
		endereco.setRua(cadastroClubeDTO.getEnderecoDTO().getRua());
		endereco.setNumero(cadastroClubeDTO.getEnderecoDTO().getNumero());
		endereco.setCidade(cadastroClubeDTO.getEnderecoDTO().getCidade());
		endereco.setEstado(cadastroClubeDTO.getEnderecoDTO().getEstado());
		endereco.setCep(cadastroClubeDTO.getEnderecoDTO().getCep());
		endereco.setBairro(cadastroClubeDTO.getEnderecoDTO().getBairro());
		endereco.setComplemento(cadastroClubeDTO.getEnderecoDTO().getComplemento());

		clube.setEndereco(endereco);

		Clube clubeSalvo = super.salvar(clube);

		UsuarioSistema usuarioSistema = new UsuarioSistema();
		usuarioSistema.setCpf(cadastroClubeDTO.getCpf());
		usuarioSistema.setSenha(cadastroClubeDTO.getSenha());
		usuarioSistema.setClube(clubeSalvo);
		usuarioSistema.setTipoUsuarioEnum(TipoUsuarioEnum.CLUBE);

		usuarioSistemaRepository.save(usuarioSistema);
	}
	
	public CadastroClubeDTO editar(Long id, CadastroClubeDTO cadastroClubeDTO) {
		Clube clubeExistente = clubeRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Clube com ID " + id + " não encontrado"));

		clubeExistente.setCpf(cadastroClubeDTO.getCpf());
		clubeExistente.setCnpj(cadastroClubeDTO.getCnpj());
		clubeExistente.setNome(cadastroClubeDTO.getNome());
		clubeExistente.setResponsavel(cadastroClubeDTO.getResponsavel());
		clubeExistente.setContato(cadastroClubeDTO.getContato());
		clubeExistente.setEmail(cadastroClubeDTO.getEmail());
		clubeExistente.setHistorico(cadastroClubeDTO.getHistorico());
		clubeExistente.setFotoCaminho(cadastroClubeDTO.getFotoCaminho());
		
		if (clubeExistente.getEndereco() == null) {
			clubeExistente.setEndereco(new Endereco());
		}
		Endereco endereco = clubeExistente.getEndereco();
		endereco.setRua(cadastroClubeDTO.getEnderecoDTO().getRua());
		endereco.setNumero(cadastroClubeDTO.getEnderecoDTO().getNumero());
		endereco.setCidade(cadastroClubeDTO.getEnderecoDTO().getCidade());
		endereco.setEstado(cadastroClubeDTO.getEnderecoDTO().getEstado());
		endereco.setCep(cadastroClubeDTO.getEnderecoDTO().getCep());
		endereco.setBairro(cadastroClubeDTO.getEnderecoDTO().getBairro());
		endereco.setComplemento(cadastroClubeDTO.getEnderecoDTO().getComplemento());

		Clube clubeAtualizado = clubeRepository.save(clubeExistente);

		return converterParaDto(clubeAtualizado);
	}
	
	public Optional<CadastroClubeDTO> buscarClubePorId(Long id) {
		return clubeRepository.findById(id).map(this::converterParaDto);
	}

	public boolean verificarCpfExistente(String cpf) {
		return usuarioSistemaRepository.findByCpf(cpf).isPresent();
	}

	public List<Clube> findAll() {
		return clubeRepository.findAll();
	}

}
