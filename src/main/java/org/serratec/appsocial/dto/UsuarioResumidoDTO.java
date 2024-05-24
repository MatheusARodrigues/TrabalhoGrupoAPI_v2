package org.serratec.appsocial.dto;

import org.serratec.appsocial.model.Usuario;

public class UsuarioResumidoDTO {

	private Long id;
	private String nome;

	public UsuarioResumidoDTO() {
	}

	public UsuarioResumidoDTO(Long id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public UsuarioResumidoDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

}
