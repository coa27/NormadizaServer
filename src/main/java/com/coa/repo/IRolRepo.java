package com.coa.repo;

import com.coa.model.Rol;

import java.util.Optional;

public interface IRolRepo extends IGenericRepo<Rol, Long> {

    Optional<Rol> findByRoleNombre(String nombre);
}
