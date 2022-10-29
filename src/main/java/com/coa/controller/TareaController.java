package com.coa.controller;

import com.coa.dto.TareaDTO;
import com.coa.model.Tablero;
import com.coa.model.Tarea;
import com.coa.service.ITareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tarea")
public class TareaController {

    @Autowired
    private ITareaService service;

    @GetMapping
    ResponseEntity<List<TareaDTO>> listarTareas(){
        List<TareaDTO> tareas = service.listarPorDTO();

        return new ResponseEntity<List<TareaDTO>>(tareas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<TareaDTO> listarTareaPorId(@PathVariable("id") Long id){
        TareaDTO tarea = service.listarPorDTOId(id);

        return new ResponseEntity<TareaDTO>(tarea, HttpStatus.OK);
    }

    @GetMapping("/tablero/{id}")
    ResponseEntity<List<TareaDTO>> listarTareaPorTablero(@PathVariable("id") Long id){
        List<TareaDTO> tarea = service.listarDTOPorTablero(id);

        return new ResponseEntity<List<TareaDTO>>(tarea, HttpStatus.OK);
    }

    @GetMapping("/paginacion")
    ResponseEntity<Page<Tarea>> listarPaginacion(@RequestParam(value = "tablero") String id, Pageable pageable){
        Page<Tarea> tareas =service.paginacion(Long.parseLong(id), pageable);

        return new ResponseEntity<>(tareas, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Tarea> agregarTarea(@RequestBody @Validated Tarea tarea){
        Tarea u = service.registrar(tarea);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(u.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping
    ResponseEntity<Tablero> actualizarTarea(@RequestBody TareaDTO tareaDTO){
        service.listarPorId(tareaDTO.getId());
        service.actualizar(tareaDTO);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarTarea(@PathVariable("id") Long id){
        Tarea tarea = service.listarPorId(id);

        service.eliminar(tarea.getId());

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
