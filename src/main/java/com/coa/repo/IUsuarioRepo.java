package com.coa.repo;

import com.coa.model.Usuario;

import java.util.Optional;


public interface IUsuarioRepo extends IGenericRepo<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);
    Optional<Usuario> findByNombre(String nombre);

}
