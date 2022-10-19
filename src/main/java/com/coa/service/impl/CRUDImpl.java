package com.coa.service.impl;

import com.coa.exception.NoEncontradoException;
import com.coa.repo.IGenericRepo;
import com.coa.service.ICRUD;

import java.util.List;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T registrar(T t) {
        return getRepo().save(t);
    }

    @Override
    public T modificar(T t) {
        return getRepo().save(t);
    }

    @Override
    public List<T> listar() {
        return getRepo().findAll();
    }

    @Override
    public T listarPorId(ID id) {
        return getRepo().findById(id).orElseThrow();
    }

    @Override
    public void eliminar(ID id) {
        getRepo().deleteById(id);
    }
}
