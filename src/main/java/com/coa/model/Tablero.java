package com.coa.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "tablero")
public class Tablero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_tablero")
	private Long id;
	
	@NotBlank
	private String nombre;

	@Column(name = "create_at")
	private LocalDate createAt = LocalDate.now();

	@Column(name = "updated_at")
	private LocalDate updatedAt = LocalDate.now();
	
	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;
	
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "tablero")
	@JsonIgnore
	private List<Tarea> tarea;

	public Tablero() {
	}

	public Tablero(String nombre) {
		this.nombre = nombre;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public LocalDate getCreateAt() {
		return createAt;
	}

	public void setCreateAt(LocalDate createAt) {
		this.createAt = createAt;
	}

	public LocalDate getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}

	@JsonIgnore
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public List<Tarea> getTarea() {
		return tarea;
	}

	public void setTarea(List<Tarea> tarea) {
		this.tarea = tarea;
	}

}
