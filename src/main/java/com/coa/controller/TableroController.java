package com.coa.controller;

import com.coa.model.Tablero;
import com.coa.service.ITableroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/tablero")
public class TableroController {

    @Autowired
    private ITableroService service;

    @GetMapping
    ResponseEntity<List<Tablero>> listarTableros(){
        List<Tablero> tableros = service.listar();

        return new ResponseEntity<List<Tablero>>(tableros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Tablero> listarTableroPorId(@PathVariable("id") Long id){
        Tablero tablero = service.listarPorId(id);

        return new ResponseEntity<Tablero>(tablero, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Tablero> agregarTablero(@RequestBody @Validated Tablero tablero){
        Tablero u = service.registrar(tablero);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(u.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarTablero(@PathVariable("id") Long id){
        Tablero tablero = service.listarPorId(id);

        if (tablero == null){
            return null;
        }

        service.eliminar(tablero.getId());

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
