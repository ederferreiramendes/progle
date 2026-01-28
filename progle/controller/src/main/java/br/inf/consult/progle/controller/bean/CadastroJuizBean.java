package br.inf.consult.progle.controller.bean;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.extensions.event.ClipboardErrorEvent;
import org.primefaces.extensions.event.ClipboardSuccessEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import br.inf.consult.enums.ModalidadeEnum;
import br.inf.consult.enums.TipoChavePixEnum;
import br.inf.consult.enums.TipoJuizEnum;
import br.inf.consult.progle.config.ViewScope;
import br.inf.consult.progle.model.dto.CadastroDadoBancarioDTO;
import br.inf.consult.progle.model.dto.CadastroEnderecoDTO;
import br.inf.consult.progle.model.dto.CadastroJuizDTO;
import br.inf.consult.progle.model.service.JuizService;
import br.inf.consult.progle.model.service.UsuarioSistemaService;
import br.inf.consult.progle.util.Constantes;
import br.inf.consult.progle.util.FacesUtil;
import br.inf.consult.progle.util.IntegracaoUtil;
import br.inf.consult.progle.util.IntegracaoUtil.Cep;
import lombok.Getter;
import lombok.Setter;

@Controller("cadastroJuizBean")
@Scope(value = ViewScope.VALUE)
@Getter
@Setter
public class CadastroJuizBean {

	@Autowired
	private JuizService juizService;

	@Autowired
	private UsuarioSistemaService usuarioSistemaService;

	private Long idJuiz;
	private CadastroJuizDTO novoJuiz;
	private boolean aceitouTermos;
	private CadastroDadoBancarioDTO dadosBancariosDTO;
	private String opcaoEscolhida;
	private TipoChavePixEnum tipoChavePix;
	private String confirmaSenhaTemporaria;
	private String enderecoCompleto;
	private String fileNameFoto;

	@PostConstruct
	public void init() {
		this.novoJuiz = new CadastroJuizDTO();
		this.novoJuiz.setEnderecoDTO(new CadastroEnderecoDTO());
		this.novoJuiz.getEnderecoDTO().setEstado("SP");
		this.novoJuiz.setDadosBancariosDTO(new CadastroDadoBancarioDTO());
	}

	public String criar() {

		if (!novoJuiz.getSenha().equals(confirmaSenhaTemporaria)) {
			FacesUtil.registrarErro("As senhas não coincidem.");
			return "";
		}

		if (!aceitouTermos) {
			FacesUtil.registrarErro("msg.erro.termosNaoAceitos");
			return "";
		}
		if (juizService.existe(idJuiz)) {
			FacesUtil.registrarErro("msg.erro.usuario.cadastrado");
			return "";

		}

		if (usuarioSistemaService.verificarCpfExistente(novoJuiz.getCpf())) {
			FacesUtil.registrarErro("msg.erro.cpf.cadastrado");
			return "";

		}

		else {
			juizService.salvar(novoJuiz);
			FacesUtil.registrarMensagem("msg.app.usuario.cadastrado");
			return "/login.xhtml?faces-redirect=true";

		}
	}

	public void editar() {
		if (juizService.existe(idJuiz)) {
			juizService.editar(idJuiz, novoJuiz);
			FacesUtil.registrarMensagem("msg.app.update.sucesso");
		} else {
			FacesUtil.registrarErro("msg.erro.editar.juiz");
		}
	}

	public void buscarJuizPorId() {
		if (idJuiz != null) {
			Optional<CadastroJuizDTO> optionalJuizDTO = juizService.buscarJuizPorId(idJuiz);
			if (optionalJuizDTO.isPresent()) {
				novoJuiz = optionalJuizDTO.get();
			} else {
				FacesUtil.registrarErro("msg.erro.buscar.juiz");
			}
		} else {
			FacesUtil.registrarErro("msg.erro.id.juiz.nao.informado");
		}
	}

	public List<ModalidadeEnum> getModalidadeOptions() {
		return Arrays.asList(ModalidadeEnum.values());
	}

	public List<TipoJuizEnum> getTipoJuizOptions() {
		return Arrays.asList(TipoJuizEnum.values());
	}

	public boolean possuiModalidadeCampo() {
		for (ModalidadeEnum m : novoJuiz.getModalidade()) {
			if (m == ModalidadeEnum.CAMPO) {
				return true;
			}
		}
		return false;
	}

	public List<TipoChavePixEnum> getTiposChavePix() {
		return Arrays.asList(TipoChavePixEnum.values());
	}

	public void confirmarTermos() {
		this.aceitouTermos = true;
	}

	public void cancelarConfirmacaoTermo() {
		this.aceitouTermos = false;
	}


	public String getLinkGoogleMaps() {
		CadastroEnderecoDTO endereco = novoJuiz.getEnderecoDTO();
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
		String cep = novoJuiz.getEnderecoDTO().getCep();

		IntegracaoUtil cepUtil = new IntegracaoUtil();
		Optional<Cep> cepEndereco = cepUtil.searchByCep(cep);

		if (cepEndereco.isPresent()) {
			String estadoSigla = cepEndereco.get().getEstado().getSigla();

			if (!"SP".equalsIgnoreCase(estadoSigla)) {
				FacesUtil.registrarErro("Somente CEPs do estado de São Paulo são permitidos.");
				return;
			}

			novoJuiz.getEnderecoDTO().setRua(cepEndereco.get().getLogradouro());
			novoJuiz.getEnderecoDTO().setBairro(cepEndereco.get().getBairro());
			novoJuiz.getEnderecoDTO().setCidade(cepEndereco.get().getCidade().getNome());
			novoJuiz.getEnderecoDTO().setEstado(estadoSigla);

			enderecoCompleto = "Rua: " + cepEndereco.get().getLogradouro() + ", Bairro: "
					+ cepEndereco.get().getBairro() + ", Cidade: " + cepEndereco.get().getCidade().getNome()
					+ ", Estado: " + estadoSigla;
		} else {
			FacesUtil.registrarErro("CEP não encontrado.");
		}
	}

	public void successListener(final ClipboardSuccessEvent successEvent) {
		final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Success",
				"Component id: " + successEvent.getComponent().getId() + " Action: " + successEvent.getAction()
						+ " Text: " + successEvent.getText());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void errorListener(final ClipboardErrorEvent errorEvent) {
		final FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
				"Component id: " + errorEvent.getComponent().getId() + " Action: " + errorEvent.getAction());
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void validarCpf() {
		if (usuarioSistemaService.verificarCpfExistente(novoJuiz.getCpf())) {
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
			
			this.fileNameFoto = novoJuiz.getCpf() +(extension!= null  ? "." + extension : "");
			File file = new File(uploadDir, this.fileNameFoto);

			try (FileOutputStream fos = new FileOutputStream(file)) {
				fos.write(event.getFile().getContent());
				fos.close();
			}
		
			novoJuiz.setFotoCaminho(this.fileNameFoto);

		} catch (IOException e) {
			FacesUtil.registrarErro("Erro ao enviar a foto: " + e.getMessage());
		}
	}

}
