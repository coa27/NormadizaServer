package com.coa.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "usuario")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_usuario")
	private Long idUsuario;

	@NotBlank
	@Length(min = 1, message = "El usuario debe ser superior a 1 caracter")
	@Column(length = 15)
	private String nombre;
	
	@NotBlank
	@Length(min = 3, message = "La contrasenia debe ser superior a 3 caracteres")
	@JsonIgnore
	private String contrasenia;

	@NotBlank
	@Email
	private String email;

	@Column(name = "create_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate createAt = LocalDate.now();


	@Column(name = "updated_at")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
	private LocalDate updatedAt = LocalDate.now();

	@OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Tablero> tablero;

	@ManyToMany(fetch = FetchType.EAGER)
	@JsonIgnore
	@JoinTable(name = "usuarios_roles",
			joinColumns = @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario"),
			inverseJoinColumns = @JoinColumn(name = "id_rol", referencedColumnName = "id_rol"))
	private Set<Rol> roles = new HashSet<>();

	public Set<Rol> getRoles() {
		return roles;
	}

	public void setRoles(Set<Rol> roles) {
		this.roles = roles;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
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

	public List<Tablero> getTablero() {
		return tablero;
	}

	public void setTablero(List<Tablero> tablero) {
		this.tablero = tablero;
	}

	public Usuario() {
	}

	public Usuario(Long idUsuario, String nombre, String contrasenia, String email, LocalDate createAt, LocalDate updatedAt, Set<Rol> roles) {
		this.idUsuario = idUsuario;
		this.nombre = nombre;
		this.contrasenia = contrasenia;
		this.email = email;
		this.createAt = createAt;
		this.updatedAt = updatedAt;
		this.roles = roles;
	}
}
