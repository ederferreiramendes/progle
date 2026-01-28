package br.inf.consult.progle.controller.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.primefaces.event.FileUploadEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.CadastroClubeDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.UsuarioSistemaDTO;
import br.inf.consult.progle.model.service.ClubeService;
import br.inf.consult.progle.model.service.UsuarioSistemaService;
import br.inf.consult.progle.util.Constantes;
import br.inf.consult.progle.util.FacesUtil;
import br.inf.consult.progle.util.IntegracaoUtil;
import br.inf.consult.progle.util.IntegracaoUtil.Cep;
import lombok.Getter;
import lombok.Setter;

@Controller("cadastroClubeBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class CadastroClubeBean {

	@Autowired
	private ClubeService clubeService;
	@Autowired
	private UsuarioSistemaService usuarioSistemaService;

	private Long idClube;
	private Long idLogin;
	private CadastroClubeDTO novoClube;
	private UsuarioSistemaDTO usuarioSistemaDTO;
	private boolean aceitouTermos;
	private String confirmaSenhaTemporaria;
	private String enderecoCompleto;
	private String fileNameFoto;



	@PostConstruct
	public void init() {
		this.novoClube = new CadastroClubeDTO();
		this.novoClube.setEnderecoDTO(new CadastroEnderecoDTO());
		this.novoClube.setUsuarioSistemaDTO(new UsuarioSistemaDTO());
		this.novoClube.getEnderecoDTO().setEstado("SP");
	}

	public String criar() {

		if (!novoClube.getSenha().equals(confirmaSenhaTemporaria)) {
			FacesUtil.registrarErro("As senhas não coincidem.");
			return "";
		}

		if (clubeService.existe(idClube)) {
			FacesUtil.registrarErro("msg.erro.usuario.cadastrado");
			return "";

		}
		
		if(usuarioSistemaService.verificarCpfExistente(novoClube.getCpf())) {
			FacesUtil.registrarErro("msg.erro.cpf.cadastrado");
			return "";

		}

		else {
			clubeService.salvar(novoClube);
			FacesUtil.registrarMensagem("msg.app.usuario.cadastrado");
			return "/login.xhtml?faces-redirect=true";
		}
	}

	public void buscarClubePorId() {
		if (idClube == null) {
			FacesUtil.registrarErro("ID do clube não foi informado.");
			return;
		}
		if (idClube != null) {
			Optional<CadastroClubeDTO> optionalClubeDTO = clubeService.buscarClubePorId(idClube);
			novoClube = optionalClubeDTO.get();
		} else {
			FacesUtil.registrarErro("msg.erro.id.clube.nao.informado");
		}
	}

	public void editar() {
		if (clubeService.existe(idClube)) {
			clubeService.editar(idClube, novoClube);
			FacesUtil.registrarMensagem("msg.app.update.sucesso");
		} else {
			FacesUtil.registrarErro("msg.erro.editar.clube");
		}
	}

	public void confirmarTermos() {
		this.aceitouTermos = true;
	}

	public void cancelarConfirmacaoTermo() {
		this.aceitouTermos = false;
	}

	public String getLinkGoogleMaps() {
		CadastroEnderecoDTO endereco = novoClube.getEnderecoDTO();
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
		String cep = novoClube.getEnderecoDTO().getCep();

		IntegracaoUtil cepUtil = new IntegracaoUtil();
		Optional<Cep> cepEndereco = cepUtil.searchByCep(cep);

		if (cepEndereco.isPresent()) {
			String estadoSigla = cepEndereco.get().getEstado().getSigla();

			if (!"SP".equalsIgnoreCase(estadoSigla)) {
				FacesUtil.registrarErro("Somente CEPs do estado de São Paulo são permitidos.");
				return;
			}

			novoClube.getEnderecoDTO().setRua(cepEndereco.get().getLogradouro());
			novoClube.getEnderecoDTO().setBairro(cepEndereco.get().getBairro());
			novoClube.getEnderecoDTO().setCidade(cepEndereco.get().getCidade().getNome());
			novoClube.getEnderecoDTO().setEstado(estadoSigla);

			enderecoCompleto = "Rua: " + cepEndereco.get().getLogradouro() + ", Bairro: "
					+ cepEndereco.get().getBairro() + ", Cidade: " + cepEndereco.get().getCidade().getNome()
					+ ", Estado: " + estadoSigla;
		} else {
			FacesUtil.registrarErro("CEP não encontrado.");
		}
	}

	public void validarCpf() {
		if (usuarioSistemaService.verificarCpfExistente(novoClube.getCpf())) {
			FacesUtil.registrarErro("msg.erro.cpf.cadastrado");
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
			
			
			this.fileNameFoto = novoClube.getCpf() +(extension!= null  ? "." + extension : "");
			File file = new File(uploadDir, this.fileNameFoto);

			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(event.getFile().getContent());
				fos.close();
			}
		
			novoClube.setFotoCaminho(this.fileNameFoto);

		} catch (IOException e) {
			FacesUtil.registrarErro("Erro ao enviar a foto: " + e.getMessage());
		}
	}
	


}
