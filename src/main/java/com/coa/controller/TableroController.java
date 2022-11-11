package com.coa.controller;

import com.coa.dto.TableroDTO;
import com.coa.model.Tablero;
import com.coa.dto.RegistroTableroDTO;
import com.coa.service.ITableroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @GetMapping("/tableros")
    ResponseEntity<List<TableroDTO>> listarTablerosAdmin(){
        List<TableroDTO> tableros = service.listarDTO();

        return new ResponseEntity<List<TableroDTO>>(tableros, HttpStatus.OK);
    }

    @GetMapping
    ResponseEntity<Page<Tablero>> listarTableros(Pageable pageable){
        Page<Tablero> tableros = service.listarDTOUsuario(pageable);

        return new ResponseEntity<Page<Tablero>>(tableros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<TableroDTO> listarTableroPorId(@PathVariable("id") Long id){
        TableroDTO tablero = service.listarDTOPorId(id);

        return new ResponseEntity<TableroDTO>(tablero, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Tablero> agregarTablero(@RequestBody @Validated RegistroTableroDTO registroTableroDTO){
        Tablero u = service.registrarTablero(registroTableroDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(u.getId()).toUri();

        return ResponseEntity.created(location).build();
    }

    @PutMapping
    ResponseEntity<Tablero> actualizarTablero(@RequestBody TableroDTO tablero){
        //validar si existe
        service.listarPorId(tablero.getIdTablero());

        service.actualizar(tablero);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarTablero(@PathVariable("id") Long id){
        Tablero tablero = service.listarPorId(id);

        service.eliminarTablero(tablero.getId());

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/usuario/{id}")
    ResponseEntity<List<TableroDTO>> listarTableroPorIdUsuario(@PathVariable("id") Long id){
        List<TableroDTO> tablero = service.listarDTOPorIdUsuario(id);

        return new ResponseEntity<List<TableroDTO>>(tablero, HttpStatus.OK);
    }

}
