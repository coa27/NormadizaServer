package com.coa.service.impl;

import com.coa.dto.TareaDTO;
import com.coa.model.Tarea;
import com.coa.repo.IGenericRepo;
import com.coa.repo.ITareaRepo;
import com.coa.service.ITareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl extends CRUDImpl<Tarea, Long> implements ITareaService {

    @Autowired
    private ITareaRepo repo;

    @Override
    protected IGenericRepo<Tarea, Long> getRepo() {
        return repo;
    }

    @Override
    public List<TareaDTO> listarPorDTO() {
        List<Tarea> tareas = repo.findAll();

        List<TareaDTO> tareaDTOS = tareas.stream().map(t -> {
            TareaDTO tableroDTO1 = new TareaDTO(t.getId(), t.getNombre(), t.getDescripcion(), t.getFinalizado(), t.getFechaInicio(), t.getFechaFin(), LocalDate.now(), LocalDate.now(), t.getTablero().getId());
            return tableroDTO1;
        } ).collect(Collectors.toList());

        return tareaDTOS;

    }

    @Override
    public TareaDTO listarPorDTOId(Long id) {
        Tarea tarea = repo.findById(id).orElseThrow();
        TareaDTO tareaDTO = new TareaDTO(tarea.getId(), tarea.getNombre(), tarea.getDescripcion(), tarea.getFinalizado(), tarea.getFechaInicio(), tarea.getFechaFin(), LocalDate.now(), LocalDate.now(), tarea.getTablero().getId());

        return tareaDTO;

    }

    @Override
    public List<TareaDTO> listarDTOPorTablero(Long id) {
        List<Tarea> tareas = repo.findAllByTableroId(id);

        List<TareaDTO> tareaDTOS = tareas.stream().map(t -> {
            TareaDTO tableroDTO1 = new TareaDTO(t.getId(), t.getNombre(), t.getDescripcion(), t.getFinalizado(), t.getFechaInicio(), t.getFechaFin(), LocalDate.now(), LocalDate.now(), t.getTablero().getId());
            return tableroDTO1;
        } ).collect(Collectors.toList());

        return tareaDTOS;
    }

    @Override
    public void actualizar(TareaDTO tareaDTO) {
        repo.actualizarTablero(tareaDTO.getNombre(), tareaDTO.getDescripcion(), tareaDTO.getFinalizado(), tareaDTO.getFechaInicio(), tareaDTO.getFechaFin(), LocalDate.now(), tareaDTO.getId());
    }

    @Override
    public Page<Tarea> paginacion(Long idTablero, Pageable pageable) {
        return repo.paginacion(idTablero, pageable);
    }
}
