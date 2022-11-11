package com.coa.service.impl;

import com.coa.model.Usuario;
import com.coa.repo.IGenericRepo;
import com.coa.repo.IUsuarioRepo;
import com.coa.security.UserDetailsImpl;
import com.coa.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends CRUDImpl<Usuario, Long> implements IUsuarioService {

    @Autowired
    private IUsuarioRepo repo;

    @Override
    protected IGenericRepo<Usuario, Long> getRepo() {
        return repo;
    }


    @Override
    public Usuario retornarUsuario() {
        UserDetailsImpl usuario = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var usuariod =  SecurityContextHolder.getContext().getAuthentication().getAuthorities();


        Usuario u = new Usuario(
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getContrasenia(),
                usuario.getEmail(),
                usuario.getCreateAt(),
                usuario.getUpdatedAt(),
                usuario.getRoles()
        );

        return u;
    }
}
