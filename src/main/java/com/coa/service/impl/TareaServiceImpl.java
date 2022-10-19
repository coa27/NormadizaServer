package com.coa.service.impl;

import com.coa.model.Tarea;
import com.coa.repo.IGenericRepo;
import com.coa.service.ITareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TareaServiceImpl extends CRUDImpl<Tarea, Long> implements ITareaService {

    @Autowired
    private IGenericRepo repo;

    @Override
    protected IGenericRepo<Tarea, Long> getRepo() {
        return repo;
    }
}
