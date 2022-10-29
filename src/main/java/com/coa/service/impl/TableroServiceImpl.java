package com.coa.service.impl;

import com.coa.dto.TableroDTO;
import com.coa.model.Tablero;
import com.coa.model.Usuario;
import com.coa.repo.IGenericRepo;
import com.coa.repo.ITableroRepo;
import com.coa.repo.RegistroTableroDTO;
import com.coa.security.UserDetailsImpl;
import com.coa.service.ITableroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TableroServiceImpl extends CRUDImpl<Tablero, Long> implements ITableroService {

    @Autowired
    private ITableroRepo repo;

    @Override
    protected IGenericRepo<Tablero, Long> getRepo() {
        return repo;
    }

    @Override
    public Tablero registrarTablero(RegistroTableroDTO registroTableroDTO) {
        Tablero tablero = new Tablero(registroTableroDTO.getNombre());
        UserDetailsImpl usuarioUD = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Usuario usuario = new Usuario(
                usuarioUD.getIdUsuario(),
                usuarioUD.getNombre(),
                usuarioUD.getContrasenia(),
                usuarioUD.getEmail(),
                usuarioUD.getCreateAt(),
                usuarioUD.getUpdatedAt(),
                usuarioUD.getRoles()
        );

        tablero.setUsuario(usuario);


        return repo.save(tablero);
    }

    @Override
    public TableroDTO listarDTOPorId(Long id) {
        Tablero tablero = repo.findById(id).orElseThrow();
        TableroDTO tableroDTO = new TableroDTO(tablero.getId(), tablero.getNombre(), tablero.getCreateAt(), tablero.getUpdatedAt(), tablero.getUsuario().getIdUsuario());

        return tableroDTO;
    }

    @Override
    public List<TableroDTO> listarDTO() {
        List<Tablero> tablero = repo.findAll();

        List<TableroDTO> tableroDTO = tablero.stream().map(t -> {
            TableroDTO tableroDTO1 = new TableroDTO(t.getId(), t.getNombre(), t.getCreateAt(), t.getUpdatedAt(), t.getUsuario().getIdUsuario());
            return tableroDTO1;
        } ).collect(Collectors.toList());

        return tableroDTO;
    }

    @Override
    public List<TableroDTO> listarDTOPorIdUsuario(Long id) {
        List<Tablero> tablero = repo.findAllByUsuarioIdUsuario(id);

        List<TableroDTO> tableroDTO = tablero.stream().map( t -> {
            TableroDTO tableroDTO1 = new TableroDTO(t.getId(), t.getNombre(), t.getCreateAt(), t.getUpdatedAt(), t.getUsuario().getIdUsuario());
            return tableroDTO1;
        } ).collect(Collectors.toList());

        return tableroDTO;
    }

    @Override
    public void actualizar(TableroDTO tablero) {
        repo.actualizarTablero(tablero.getNombre(), LocalDate.now(), tablero.getIdTablero());
    }

}
