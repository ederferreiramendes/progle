package br.inf.consult.progle.controller.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.enums.ModalidadeEnum;
import br.inf.consult.enums.PosicaoCampoEnum;
import br.inf.consult.enums.PosicaoFutsalEnum;
import br.inf.consult.enums.PosicaoSocietyEnum;
import br.inf.consult.enums.SexoEnum;
import br.inf.consult.enums.TipoChavePixEnum;
import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.CadastroDadoBancarioDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.CadastroJogadorDTO;
import br.inf.consult.progle.model.service.JogadorService;
import br.inf.consult.progle.model.service.UsuarioSistemaService;
import br.inf.consult.progle.util.Constantes;
import br.inf.consult.progle.util.FacesUtil;
import br.inf.consult.progle.util.IntegracaoUtil;
import br.inf.consult.progle.util.IntegracaoUtil.Cep;
import lombok.Getter;
import lombok.Setter;

@Controller("cadastroJogadorBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class CadastroJogadorBean {

	@Autowired
	private JogadorService jogadorService;

	@Autowired
	private UsuarioSistemaService usuarioSistemaService;

	private Long idJogador;
	private CadastroJogadorDTO novoJogador;
	private CadastroDadoBancarioDTO dadosBancarios;
	private List<CadastroJogadorDTO> jogadoresCadastrados;
	private String opcaoEscolhida;
	private TipoChavePixEnum tipoChavePix;
	private String confirmaSenhaTemporaria;
	private boolean aceitouTermos;
	private String enderecoCompleto;
	private String fileNameFoto;

	@PostConstruct
	public void init() {
		this.novoJogador = new CadastroJogadorDTO();
		this.novoJogador.setEnderecoDTO(new CadastroEnderecoDTO());
		this.novoJogador.setDadosBancariosDTO(new CadastroDadoBancarioDTO());
		this.jogadoresCadastrados = new ArrayList<CadastroJogadorDTO>();
		this.novoJogador.getEnderecoDTO().setEstado("SP");
	}

	public String criar() {

		if (!novoJogador.getSenha().equals(confirmaSenhaTemporaria)) {
			FacesUtil.registrarErro("As senhas não coincidem.");
			return "";
		}

		if (jogadorService.existe(idJogador)) {
			FacesUtil.registrarErro("msg.erro.usuario.cadastrado");
			return "";

		}
		
		if(usuarioSistemaService.verificarCpfExistente(novoJogador.getCpf())) {
			FacesUtil.registrarErro("msg.erro.cpf.cadastrado");
			return "";

		}

		else {
			jogadorService.salvar(novoJogador);
			FacesUtil.registrarMensagem("msg.app.usuario.cadastrado");
			return "/login.xhtml?faces-redirect=true";
		}
	}

	public void editar() {
		if (jogadorService.existe(idJogador)) {
			jogadorService.editar(idJogador, novoJogador);
			FacesUtil.registrarMensagem("msg.app.update.sucesso");
		} else {
			FacesUtil.registrarErro("msg.erro.editar");
		}
	}

	public void buscarJogadorPorId() {
		if (idJogador != null) {
			Optional<CadastroJogadorDTO> optionalJogadorDTO = jogadorService.buscarJogadorPorId(idJogador);
			if (optionalJogadorDTO.isPresent()) {
				novoJogador = optionalJogadorDTO.get();
			} else {
				FacesUtil.registrarErro("msg.erro.buscar.jogador");
			}
		} else {
			FacesUtil.registrarErro("msg.erro.id.jogador.nao.informado");
		}
	}

	public List<SexoEnum> getSexoOptions() {
		return Arrays.asList(SexoEnum.values());
	}

	public List<ModalidadeEnum> getModalidadeOptions() {
		return Arrays.asList(ModalidadeEnum.values());
	}

	public List<PosicaoCampoEnum> getPosicoesCampoOptions() {
		return Arrays.asList(PosicaoCampoEnum.values());
	}

	public List<PosicaoFutsalEnum> getPosicoesFutsalOptions() {
		return Arrays.asList(PosicaoFutsalEnum.values());
	}

	public List<PosicaoSocietyEnum> getPosicoesSocietyOptions() {
		return Arrays.asList(PosicaoSocietyEnum.values());
	}

	public List<TipoChavePixEnum> getTiposChavePix() {
		return Arrays.asList(TipoChavePixEnum.values());
	}

	public boolean possuiModalidadeSociety() {
		for (ModalidadeEnum m : novoJogador.getModalidade()) {
			if (m == ModalidadeEnum.SOCIETY) {
				return true;
			}
		}
		return false;
	}

	public boolean possuiModalidadeCampo() {
		for (ModalidadeEnum m : novoJogador.getModalidade()) {
			if (m == ModalidadeEnum.CAMPO) {
				return true;
			}
		}
		return false;
	}

	public boolean possuiModalidadeFutsal() {
		for (ModalidadeEnum m : novoJogador.getModalidade()) {
			if (m == ModalidadeEnum.FUTSAL) {
				return true;
			}
		}
		return false;
	}


	public String getLinkGoogleMaps() {
		CadastroEnderecoDTO endereco = novoJogador.getEnderecoDTO();
		String rua = endereco.getRua();
		String numero = endereco.getNumero();
		String bairro = endereco.getBairro();

		try {
			String enderecoCompleto = rua + ", " + numero + ", " + bairro;
			String enderecoUrl = URLEncoder.encode(enderecoCompleto, "UTF-8");
//			return "https://www.google.com/maps/search/?api=1&query=" + enderecoUrl;
			return "https://www.google.com/maps/embed/v1/place?key=AIzaSyC22rCP8JRusQvFcyAKqeaAJv-m7FyDhlM&q="+enderecoUrl;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return "#";
		}
	}

	public void carregarEnderecoPorCep() {
		String cep = novoJogador.getEnderecoDTO().getCep();

		IntegracaoUtil cepUtil = new IntegracaoUtil();
		Optional<Cep> cepEndereco = cepUtil.searchByCep(cep);

		if (cepEndereco.isPresent()) {
			String estadoSigla = cepEndereco.get().getEstado().getSigla();

			if (!"SP".equalsIgnoreCase(estadoSigla)) {
				FacesUtil.registrarErro("Somente CEPs do estado de São Paulo são permitidos.");
				return;
			}

			novoJogador.getEnderecoDTO().setRua(cepEndereco.get().getLogradouro());
			novoJogador.getEnderecoDTO().setBairro(cepEndereco.get().getBairro());
			novoJogador.getEnderecoDTO().setCidade(cepEndereco.get().getCidade().getNome());
			novoJogador.getEnderecoDTO().setEstado(estadoSigla);

			enderecoCompleto = "Rua: " + cepEndereco.get().getLogradouro() + ", Bairro: "
					+ cepEndereco.get().getBairro() + ", Cidade: " + cepEndereco.get().getCidade().getNome()
					+ ", Estado: " + estadoSigla;
		} else {
			FacesUtil.registrarErro("CEP não encontrado.");
		}
	}

	public void handleFileUpload(FileUploadEvent event) {
		try {
			String uploadDir =  Constantes.UPLOAD_FOTO_DIR;
			File uploadFolder = new File(uploadDir);
			if (!uploadFolder.exists()) {
				uploadFolder.mkdirs();
			}

			String fileNameOrigin = event.getFile().getFileName(); 
			String extension = "";

			int i = fileNameOrigin.lastIndexOf('.');
			if (i > 0) {
			    extension = fileNameOrigin.substring(i + 1); 
			}
			
			this.fileNameFoto = novoJogador.getCpf() +(extension!= null  ? "." + extension : "");
			File file = new File(uploadDir, this.fileNameFoto);

			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(event.getFile().getContent());
				fos.close();
			}
		
			novoJogador.setFotoCaminho(this.fileNameFoto);

		} catch (IOException e) {
			FacesUtil.registrarErro("Erro ao enviar a foto: " + e.getMessage());
		}
	}

	public void validarCpf() {
		if (usuarioSistemaService.verificarCpfExistente(novoJogador.getCpf())) {
			FacesUtil.registrarErro("msg.erro.cpf.cadastrado");
		}
	}
}
