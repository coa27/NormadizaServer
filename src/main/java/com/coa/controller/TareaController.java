package com.coa.controller;

import com.coa.model.Tarea;
import com.coa.service.ITareaService;
import org.springframework.beans.factory.annotation.Autowired;
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
    ResponseEntity<List<Tarea>> listarTareas(){
        List<Tarea> tareas = service.listar();

        return new ResponseEntity<List<Tarea>>(tareas, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Tarea> listarTareaPorId(@PathVariable("id") Long id){
        Tarea tarea = service.listarPorId(id);

        return new ResponseEntity<Tarea>(tarea, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Tarea> agregarTarea(@RequestBody @Validated Tarea tarea){
        Tarea u = service.registrar(tarea);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(u.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarTarea(@PathVariable("id") Long id){
        Tarea tarea = service.listarPorId(id);

        if (tarea == null){
            return null;
        }

        service.eliminar(tarea.getId());

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
