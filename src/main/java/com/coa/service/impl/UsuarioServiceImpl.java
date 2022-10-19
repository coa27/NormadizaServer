package com.coa.service.impl;

import com.coa.model.Usuario;
import com.coa.repo.IGenericRepo;
import com.coa.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl extends CRUDImpl<Usuario, Long> implements IUsuarioService {

    @Autowired
    private IGenericRepo repo;

    @Override
    protected IGenericRepo<Usuario, Long> getRepo() {
        return repo;
    }


}
