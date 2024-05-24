package org.serratec.appsocial.dto;

import java.time.LocalDate;

import org.serratec.appsocial.model.Usuario;

public class UsuarioDTO {

	private Long id;
	private String nome;
	private String sobrenome;
	private String email;
	private LocalDate dataNascimento;

	public UsuarioDTO() {
	}

	public UsuarioDTO(Long id, String nome, String sobrenome, String email, LocalDate dataNascimento) {
		this.id = id;
		this.nome = nome;
		this.sobrenome = sobrenome;
		this.email = email;
		this.dataNascimento = dataNascimento;
	}

	public UsuarioDTO(Usuario usuario) {
		this.id = usuario.getId();
		this.nome = usuario.getNome();
		this.sobrenome = usuario.getSobrenome();
		this.email = usuario.getEmail();
		this.dataNascimento = usuario.getDataNascimento();
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

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	//comentario

	public Usuario toEntity() {
		// TODO Auto-generated method stub
		return null;
	}
	
	  // Método para converter de Usuario para UsuarioDTO
    public static UsuarioDTO fromUsuario(Usuario usuario) {
        return new UsuarioDTO(usuario.getId(), usuario.getNome(), usuario.getSobrenome(), usuario.getEmail(), usuario.getDataNascimento());
    }

    // Método para converter de UsuarioDTO para Usuario
    public Usuario toUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setNome(this.nome);
        usuario.setSobrenome(this.sobrenome);
        usuario.setEmail(this.email);
        usuario.setDataNascimento(this.dataNascimento);
        return usuario;
    }
}
