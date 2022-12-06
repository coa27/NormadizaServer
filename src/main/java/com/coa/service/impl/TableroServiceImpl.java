package com.coa.service.impl;

import com.coa.dto.TableroDTO;
import com.coa.exception.NoEncontradoException;
import com.coa.model.Tablero;
import com.coa.model.Usuario;
import com.coa.repo.IGenericRepo;
import com.coa.repo.ITableroRepo;
import com.coa.dto.RegistroTableroDTO;
import com.coa.repo.ITareaRepo;
import com.coa.security.UserDetailsImpl;
import com.coa.service.ITableroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.persistence.Table;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableroServiceImpl extends CRUDImpl<Tablero, Long> implements ITableroService {

    @Autowired
    private ITableroRepo repo;

    @Autowired
    private ITareaRepo tareaRepo;

    @Override
    protected IGenericRepo<Tablero, Long> getRepo() {
        return repo;
    }

    /***
     *
     * @param registroTableroDTO
     * @return
     */
    @Override
    public TableroDTO registrarTablero(RegistroTableroDTO registroTableroDTO) {
        Tablero tablero = new Tablero(registroTableroDTO.getNombre());
        UserDetailsImpl usuarioUD = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario usuario = crearUsuario(usuarioUD);

        tablero.setUsuario(usuario);

        repo.save(tablero);

        return new TableroDTO(tablero.getId(), tablero.getNombre(), tablero.getCreateAt(), tablero.getUpdatedAt(), tablero.getUsuario().getIdUsuario(), 0, 0);
    }

    @Override
    public TableroDTO listarDTOPorId(Long id) {
        UserDetailsImpl usuarioUD = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        esSuTablero(usuarioUD.getIdUsuario(), id);

        Tablero tablero = repo.findById(id).orElseThrow();
        Integer tareasTotales = tareaRepo.findAllByTableroId(id).size();
        Integer tareasCompletas = tareaRepo.encontrarTareasFinalizadas(id);

        TableroDTO tableroDTO = new TableroDTO(tablero.getId(), tablero.getNombre(), tablero.getCreateAt(), tablero.getUpdatedAt(), tablero.getUsuario().getIdUsuario(), tareasTotales, tareasCompletas);

        return tableroDTO;
    }

    @Override
    public List<TableroDTO> listarDTO() {
        List<Tablero> tablero = repo.findAll();

        List<TableroDTO> tableroDTO = tablero.stream().map(t -> {
            Integer tareasTotales = tareaRepo.findAllByTableroId(t.getId()).size();
            Integer tareasCompletas = tareaRepo.encontrarTareasFinalizadas(t.getId());

            TableroDTO tableroDTO1 = new TableroDTO(t.getId(), t.getNombre(), t.getCreateAt(), t.getUpdatedAt(), t.getUsuario().getIdUsuario(), tareasTotales, tareasCompletas);
            return tableroDTO1;
        } ).collect(Collectors.toList());

        return tableroDTO;
    }

    @Override
    public Page<TableroDTO> listarDTOUsuario(Pageable pageable) {
        UserDetailsImpl usuario = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        List<Tablero> lista = repo.findAllByUsuarioIdUsuario(usuario.getIdUsuario(), Sort.by("id").ascending() );

        Page<TableroDTO> paginacion = paginacionTableroDTO(lista, pageable);

        return paginacion;
    }

    @Override
    public List<TableroDTO> listarDTOPorIdUsuario(Long id) {
        List<Tablero> tablero = repo.findAllByUsuarioIdUsuario(id, Sort.by("id").ascending());

        List<TableroDTO> tableroDTO = tablero.stream().map( t -> {
            Integer tareasTotales = tareaRepo.findAllByTableroId(t.getId()).size();
            Integer tareasCompletas = tareaRepo.encontrarTareasFinalizadas(t.getId());

            TableroDTO tableroDTO1 = new TableroDTO(t.getId(), t.getNombre(), t.getCreateAt(), t.getUpdatedAt(), t.getUsuario().getIdUsuario(), tareasTotales, tareasCompletas);
            return tableroDTO1;
        } ).collect(Collectors.toList());

        return tableroDTO;
    }

    @Override
    public TableroDTO actualizar(TableroDTO tablero) {
        UserDetailsImpl usuario = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        esSuTablero(usuario.getIdUsuario(), tablero.getIdTablero());

        Tablero t = repo.findById(tablero.getIdTablero()).orElseThrow(() -> new NoEncontradoException("Tablero no encontrado"));

        t.setNombre(tablero.getNombre());
        t.setUpdatedAt(LocalDate.now());

        repo.save(t);

//        repo.actualizarTablero(tablero.getNombre(), LocalDate.now(), tablero.getIdTablero());

        TableroDTO tableroDTO = convercionTableroToDTO(t);

        return tableroDTO;
    }

    @Override
    public void eliminarTablero(Long id) {
        UserDetailsImpl usuario = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //valido si es su tablero
        esSuTablero(usuario.getIdUsuario(), id);

        repo.deleteById(id);
    }

    private Boolean esSuTablero(Long idUsuario, Long idTablero){
        List<Long> idsTableros = repo.obtenerIdsTableroPorUsuario(idUsuario);

        if(idsTableros.contains(idTablero)){
            return true;
        }
        throw new AuthorizationServiceException("No tiene acceso a este recurso");

    }

    /***
     *
     * Hace un pageable que me devuelve un objeto con la informacion de los tableros y la cantidad de tareas que hay en estas, es decir un
     * TableroDTO, pero no encontre forma de hacer (quitando los procedimientos almacenados) un pageable directamente del repositorio
     * con la informacion del tablero, del total de tareas y tareas completas que hay en el mismo tablero.
     *
     * @param lista
     * @param pageable
     * @return
     */
    private Page<TableroDTO> paginacionTableroDTO(List<Tablero> lista, Pageable pageable){

        List<TableroDTO> tableroDTOS = convercionTableroToDTO(lista);

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), tableroDTOS.size());
        Page<TableroDTO> page = new PageImpl<>(tableroDTOS.subList(start, end), pageable, tableroDTOS.size());

        return page;
    }

    private List<TableroDTO> convercionTableroToDTO(List<Tablero> list){
        return list.stream().map( tablero -> {
            Integer cantidadTotalTareas = tareaRepo.countByTableroId(tablero.getId());
            Integer cantidadFinalizada = tareaRepo.countByTableroIdAndFinalizado(tablero.getId(), true);

            return new TableroDTO(tablero.getId(), tablero.getNombre(), tablero.getCreateAt(), tablero.getUpdatedAt(), tablero.getUsuario().getIdUsuario(), cantidadTotalTareas, cantidadFinalizada);
        }).collect(Collectors.toList());
    }

    private TableroDTO convercionTableroToDTO(Tablero tablero){
        Integer cantidadTotalTareas = tareaRepo.countByTableroId(tablero.getId());
        Integer cantidadFinalizada = tareaRepo.countByTableroIdAndFinalizado(tablero.getId(), true);
        return new TableroDTO(tablero.getId(), tablero.getNombre(), tablero.getCreateAt(), tablero.getUpdatedAt(), tablero.getUsuario().getIdUsuario(), cantidadTotalTareas, cantidadFinalizada);
    }

    private Usuario crearUsuario(UserDetailsImpl usuarioUD){
        return new Usuario(
                usuarioUD.getIdUsuario(),
                usuarioUD.getNombre(),
                usuarioUD.getContrasenia(),
                usuarioUD.getEmail(),
                usuarioUD.getCreateAt(),
                usuarioUD.getUpdatedAt(),
                usuarioUD.getRoles()
        );
    }
}
