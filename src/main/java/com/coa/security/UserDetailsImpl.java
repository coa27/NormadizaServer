package com.coa.security;

import com.coa.model.Rol;
import com.coa.model.Tablero;
import com.coa.model.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

    private Long idUsuario;
    private String nombre;
    private String email;
    private LocalDate createAt = LocalDate.now();
    private LocalDate updatedAt = LocalDate.now();
    @JsonIgnore
    private String contrasenia;
    @JsonIgnore
    private Set<Rol> roles = new HashSet<>();

    public UserDetailsImpl(Long idUsuario, String nombre, String email, LocalDate createAt, LocalDate updatedAt, String contrasenia, Set<Rol> roles) {
        this.idUsuario = idUsuario;
        this.nombre = nombre;
        this.email = email;
        this.createAt = createAt;
        this.updatedAt = updatedAt;
        this.contrasenia = contrasenia;
        this.roles = roles;
    }

    public static UserDetailsImpl parseo(Usuario usuario){
        return new UserDetailsImpl(
        usuario.getIdUsuario(),
        usuario.getNombre(),
        usuario.getEmail(),
        usuario.getCreateAt(),
        usuario.getUpdatedAt(),
        usuario.getContrasenia(),
        usuario.getRoles()
        );
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Set<Rol> getRoles() {
        return roles;
    }

    public void setRoles(Set<Rol> roles) {
        this.roles = roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(rol ->
                new SimpleGrantedAuthority(rol.getRoleNombre())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return getContrasenia();
    }

    @Override
    public String getUsername() {
        return getNombre();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
