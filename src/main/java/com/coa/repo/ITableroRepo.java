package com.coa.repo;

import com.coa.model.Tablero;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface ITableroRepo extends IGenericRepo<Tablero, Long> {

    List<Tablero> findAllByUsuarioIdUsuario(Long id);

    @Query(value = "select id_tablero from tablero where id_usuario = ?", nativeQuery = true)
    List<Long> obtenerIdsTableroPorUsuario(Long usuario);

    @Transactional
    @Modifying
    @Query(value = "update tablero set nombre = ?, updated_at = ? where id_tablero = ?", nativeQuery = true)
    int actualizarTablero(String nombre, LocalDate localDate, Long idTablero);

    @Query(value = "select * from tablero where id_usuario = ?", nativeQuery = true)
    Page<Tablero> obtenerPaginacion(Long idUsuario, Pageable page);

}
