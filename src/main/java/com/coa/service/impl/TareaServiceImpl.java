package com.coa.service.impl;

import com.coa.dto.TareaDTO;
import com.coa.exception.NoEncontradoException;
import com.coa.model.Tarea;
import com.coa.repo.IGenericRepo;
import com.coa.repo.ITableroRepo;
import com.coa.repo.ITareaRepo;
import com.coa.security.UserDetailsImpl;
import com.coa.service.ITareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TareaServiceImpl extends CRUDImpl<Tarea, Long> implements ITareaService {

    @Autowired
    private ITareaRepo repo;

    @Autowired
    private ITableroRepo tableroRepo;

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
    public Page<Tarea> listarDTOPorTablero(Long id, Pageable pageable) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //valido si se esta accediendo a su tablero
        esSuTablero(userDetails.getIdUsuario(), id);

        Page<Tarea> page = repo.paginacion(id, pageable);

        return page;
    }

    private Boolean esSuTablero(Long idUsuario, Long idTablero){
        List<Long> idsTableros = tableroRepo.obtenerIdsTableroPorUsuario(idUsuario);

        if(idsTableros.contains(idTablero)){
            return true;
        }
        throw new AuthorizationServiceException("No tiene acceso a este recurso");

    }

    @Override
    public void actualizar(TareaDTO tareaDTO) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //valido si se esta accediendo a su tablero
        esSuTablero(userDetails.getIdUsuario(), tareaDTO.getIdTablero());

        repo.actualizarTablero(tareaDTO.getNombre(), tareaDTO.getDescripcion(), tareaDTO.getFinalizado(), tareaDTO.getFechaInicio(), tareaDTO.getFechaFin(), LocalDate.now(), tareaDTO.getId());
    }

    @Override
    public void eliminarTarea(Long idTarea) {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tarea tarea = repo.findById(idTarea).orElseThrow(() -> new NoEncontradoException("Tarea no encontrada"));

        //valido si se esta accediendo a su tablero
        esSuTablero(userDetails.getIdUsuario(), tarea.getTablero().getId());

        repo.deleteById(tarea.getId());

    }
}
