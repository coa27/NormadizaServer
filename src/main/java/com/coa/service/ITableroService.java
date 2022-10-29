package com.coa.service;

import com.coa.dto.TableroDTO;
import com.coa.model.Tablero;
import com.coa.repo.RegistroTableroDTO;

import java.util.List;

public interface ITableroService extends ICRUD<Tablero, Long> {

    Tablero registrarTablero(RegistroTableroDTO tablero);

    TableroDTO listarDTOPorId(Long id);
    List<TableroDTO> listarDTO();

    List<TableroDTO> listarDTOPorIdUsuario(Long id);

    void actualizar(TableroDTO tablero);

}
