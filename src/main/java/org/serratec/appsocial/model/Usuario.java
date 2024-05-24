package org.serratec.appsocial.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Usuario implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_usuario")
	private Long id;

	@Column
	private String nome;
	@Column
	private String sobrenome; // usiarioDTO (completo, sem senha - classe principal) e outro
								// UsuarioResumidoDTO (id, nome 'email', seguirdores e seguidos, para a lista)
	@Column
	private String email;
	@Column
	private String senha;
	@Column
	private LocalDate dataNascimento;

	@OneToMany(mappedBy = "autor")
	private List<Postagem> postagens;

	@OneToMany(mappedBy = "id.seguidor", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Relacionamento> seguidores = new ArrayList<>();

	// @OneToMany(mappedBy = "id.seguidor", fetch=FetchType.EAGER,
	// cascade=CascadeType.ALL)
	// private List<UsuarioDTO> seguidores = new ArrayList<>();

	@OneToMany(mappedBy = "id.seguido", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Relacionamento> seguindo = new ArrayList<>();

	// Getters e Setters

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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public List<Postagem> getPostagens() {
		return postagens;
	}

	public void setPostagens(List<Postagem> postagens) {
		this.postagens = postagens;
	}

	public List<Relacionamento> getSeguidores() {
		return seguidores;
	}

	public void setSeguidores(List<Relacionamento> seguidores) {
		this.seguidores = seguidores;
	}

	public List<Relacionamento> getSeguindo() {
		return seguindo;
	}

	public void setSeguindo(List<Relacionamento> seguindo) {
		this.seguindo = seguindo;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(id, other.id);
	}

	// Métodos da interface UserDetails

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Retorne as autoridades do usuário (roles/perfis)
		return null; // substituir por sua lógica
	}

	@JsonIgnore
	@Override
	public String getPassword() {
		return senha;
	}

	@JsonIgnore
	@Override
	public String getUsername() {
		return email;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true; // substituir por sua lógica, se necessário
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true; // substituir por sua lógica, se necessário
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true; // substituir por sua lógica, se necessário
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true; // substituir por sua lógica, se necessário
	}

	@Override
	public String toString() {
		return "* Código: " + id + "\n* Nome: " + nome + "\n* Email: " + email;
	}

	public Usuario orElseThrow(Object object) {
		// TODO Auto-generated method stub
		return null;
	};
	// comentario
}
