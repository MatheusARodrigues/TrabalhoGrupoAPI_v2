package org.serratec.appsocial.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Relacionamento implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private RelacionamentoPK id = new RelacionamentoPK ();

	private LocalDate dataCriacao;

	public RelacionamentoPK getId() {
		return id;
	}

	public void setId(RelacionamentoPK id) {
		this.id = id;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	@Embeddable
	public static class RelacionamentoPK implements Serializable {
		
		
		@ManyToOne
		// @MapsId("seguidorId")
		private Usuario seguidor;
		
		
		@ManyToOne
		// @MapsId("seguidoId")
		private Usuario seguido;
		
		//getters and setters

		public Usuario getSeguidor() {
			return seguidor;
		}

		public void setSeguidor(Usuario seguidor) {
			this.seguidor = seguidor;
		}

		public Usuario getSeguido() {
			return seguido;
		}

		public void setSeguido(Usuario seguido) {
			this.seguido = seguido;
		}

		@Override
		public int hashCode() {
			return Objects.hash(seguido, seguidor);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			RelacionamentoPK other = (RelacionamentoPK) obj;
			return Objects.equals(seguido, other.seguido) && Objects.equals(seguidor, other.seguidor);
		}

	}

}

