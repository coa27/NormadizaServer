package com.coa.service.impl;

import com.coa.model.Tablero;
import com.coa.repo.IGenericRepo;
import com.coa.repo.ITableroRepo;
import com.coa.service.ITableroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TableroServiceImpl extends CRUDImpl<Tablero, Long> implements ITableroService {

    @Autowired
    private ITableroRepo repo;

    @Override
    protected IGenericRepo<Tablero, Long> getRepo() {
        return repo;
    }
}
