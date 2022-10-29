package com.coa.repo;

import com.coa.model.Tarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface ITareaRepo extends IGenericRepo<Tarea, Long> {

    List<Tarea> findAllByTableroId(Long id);

    @Transactional
    @Modifying
    @Query(value = "update tarea set nombre = ?, descripcion = ?, finalizado = ?, fecha_inicio = ?, fecha_fin = ? , updated_at = ? where id_tarea = ?", nativeQuery = true)
    int actualizarTablero(String nombre, String descripcion, Boolean finalizado, LocalDate fechaInicio, LocalDate fechaFin, LocalDate localDate, Long idTarea);

    @Query(value = "select * from tarea where id_tablero = ?", nativeQuery = true)
    Page<Tarea> paginacion(Long idTablero, Pageable pageable);
}
