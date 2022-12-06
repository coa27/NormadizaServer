package com.coa.repo;

import com.coa.dto.TareaDTO;
import com.coa.model.Tarea;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

public interface ITareaRepo extends IGenericRepo<Tarea, Long> {

    List<Tarea> findAllByTableroId(Long id);

    Integer countByTableroId(Long id);

    Integer countByTableroIdAndFinalizado(Long id, Boolean finalizado);

    @Query(value = "select new com.coa.dto.TareaDTO(t.id, t.nombre, t.descripcion, t.finalizado, t.fechaInicio, t.fechaFin, t.createAt, t.updatedAt, t.tablero.id) from Tarea t where t.tablero.id = :idTablero order by t.id")
    Page<TareaDTO> paginacionTareaDTO(@Param("idTablero") Long idTablero, Pageable pageable);

    @Query(value = "select count(*) from tarea where id_tablero = ? and finalizado = true", nativeQuery = true)
    Integer encontrarTareasFinalizadas(Long id);

    @Transactional
    @Modifying
    @Query(value = "update tarea set nombre = ?, descripcion = ?, finalizado = ?, fecha_inicio = ?, fecha_fin = ? , updated_at = ? where id_tarea = ?", nativeQuery = true)
    int actualizarTablero(String nombre, String descripcion, Boolean finalizado, LocalDate fechaInicio, LocalDate fechaFin, LocalDate localDate, Long idTarea);

    @Query(value = "select * from tarea where id_tablero = ?", nativeQuery = true)
    Page<Tarea> paginacion(Long idTablero, Pageable pageable);

//    @Query(value = "select t.id_tarea, t.create_at, t.descripcion, t.fecha_fin, t.fecha_inicio, t.finalizado, t.nombre, t.updated_at, t.id_tablero from tarea as t INNER JOIN tablero ON tablero.id_tablero=t.id_tablero where tablero.id_usuario = ?", nativeQuery = true)
//    Page<Tarea> tareaPorUsuario(Long idUsuario, );
}
