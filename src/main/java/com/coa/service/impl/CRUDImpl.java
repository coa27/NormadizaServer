package com.coa.service.impl;

import com.coa.repo.IGenericRepo;
import com.coa.service.ICRUD;

import java.util.List;

public abstract class CRUDImpl<T, ID> implements ICRUD<T, ID> {

    protected abstract IGenericRepo<T, ID> getRepo();

    @Override
    public T registrar(T t) {
        return null;
    }

    @Override
    public T modificar(T t) {
        return null;
    }

    @Override
    public List<T> listar() {
        return getRepo().findAll();
    }

    @Override
    public T listarPorId(ID id) {
        return getRepo().findById(id).orElse(null);
    }

    @Override
    public void eliminar(ID id) {

    }
}
