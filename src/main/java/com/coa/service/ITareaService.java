package com.coa.service;

import com.coa.dto.TareaDTO;
import com.coa.model.Tarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITareaService extends ICRUD<Tarea, Long> {

    List<TareaDTO> listarPorDTO();

    TareaDTO listarPorDTOId(Long id);

    List<TareaDTO> listarDTOPorTablero(Long id);

    void actualizar(TareaDTO tareaDTO);

    Page<Tarea> paginacion(Long idTablero, Pageable pageable);


}
