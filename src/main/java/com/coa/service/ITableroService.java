package com.coa.service;

import com.coa.dto.TableroDTO;
import com.coa.model.Tablero;
import com.coa.dto.RegistroTableroDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITableroService extends ICRUD<Tablero, Long> {

    Tablero registrarTablero(RegistroTableroDTO tablero);

    TableroDTO listarDTOPorId(Long id);

    List<TableroDTO> listarDTO();

    Page<Tablero> listarDTOUsuario(Pageable pageable);

    List<TableroDTO> listarDTOPorIdUsuario(Long id);

    void actualizar(TableroDTO tablero);

    void eliminarTablero(Long id);

}
