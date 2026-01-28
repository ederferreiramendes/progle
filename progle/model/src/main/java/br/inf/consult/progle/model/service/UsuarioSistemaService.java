package br.inf.consult.progle.model.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.inf.consult.progle.model.dto.UsuarioSistemaDTO;
import br.inf.consult.progle.model.entity.UsuarioSistema;
import br.inf.consult.progle.model.repository.UsuarioSistemaRepository;

@Service
public class UsuarioSistemaService extends GenericCrudService<UsuarioSistema, Long, UsuarioSistemaRepository> {

	@Autowired
	private UsuarioSistemaRepository usuarioSistemaRepository;

	private UsuarioSistemaDTO converterParaDto(UsuarioSistema usuarioSistema) {
		UsuarioSistemaDTO dto = new UsuarioSistemaDTO();
		dto.setClube(usuarioSistema.getClube());
		dto.setCpf(usuarioSistema.getCpf());
		dto.setJogador(usuarioSistema.getJogador());
		dto.setJuiz(usuarioSistema.getJuiz());
		dto.setSenha(usuarioSistema.getSenha());
		dto.setTipoUsuario(usuarioSistema.getTipoUsuarioEnum());

		return dto;
	}

	public UsuarioSistemaDTO editarSenha(Long id, UsuarioSistemaDTO usuarioSistemaDTO) {

		UsuarioSistema usuarioSistemaExistente = usuarioSistemaRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Login " + id + " não encontrado"));

		usuarioSistemaExistente.setSenha(usuarioSistemaDTO.getSenha());

		UsuarioSistema usuarioSistemaAtualizado = usuarioSistemaRepository.save(usuarioSistemaExistente);

		return converterParaDto(usuarioSistemaAtualizado);
	}

	public Optional<UsuarioSistemaDTO> buscarLoginPorId(Long id) {
		return usuarioSistemaRepository.findById(id).map(this::converterParaDto);
	}

	public UsuarioSistema autenticar(String cpf, String senha) {
		return usuarioSistemaRepository.findByCpfAndSenha(cpf, senha);
	}

	public boolean verificarCpfExistente(String cpf) {
		return usuarioSistemaRepository.existsByCpf(cpf);
	}

}
