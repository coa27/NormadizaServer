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

    @GetMapping
    ResponseEntity<Page<TableroDTO>> listarTableros(Pageable pageable){
        Page<TableroDTO> tableros = service.listarDTOUsuario(pageable);

        return new ResponseEntity<Page<TableroDTO>>(tableros, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<TableroDTO> listarTablero(@PathVariable("id") Long id){
        TableroDTO tablero = service.listarDTOPorId(id);

        return new ResponseEntity<TableroDTO>(tablero, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<TableroDTO> agregarTablero(@RequestBody @Validated RegistroTableroDTO registroTableroDTO){
        TableroDTO u = service.registrarTablero(registroTableroDTO);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(u.getIdTablero()).toUri();

        return ResponseEntity.created(location).body(u);
    }

    @PutMapping
    ResponseEntity<TableroDTO> actualizarTablero(@RequestBody TableroDTO tablero){
        //validar si existe
        service.listarPorId(tablero.getIdTablero());

        TableroDTO t = service.actualizar(tablero);

        return new ResponseEntity<>(t, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarTablero(@PathVariable("id") Long id){
        Tablero tablero = service.listarPorId(id);

        service.eliminarTablero(tablero.getId());

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/admin/usuario/{id}")
    ResponseEntity<List<TableroDTO>> listarTableroPorIdUsuarioAdmin(@PathVariable("id") Long id){
        List<TableroDTO> tablero = service.listarDTOPorIdUsuario(id);

        return new ResponseEntity<List<TableroDTO>>(tablero, HttpStatus.OK);
    }

    @GetMapping("/admin/{id}")
    ResponseEntity<TableroDTO> listarTableroPorIdAdmin(@PathVariable("id") Long id){
        TableroDTO tablero = service.listarDTOPorId(id);

        return new ResponseEntity<TableroDTO>(tablero, HttpStatus.OK);
    }

    @GetMapping("/admin/tableros")
    ResponseEntity<List<TableroDTO>> listarTablerosAdmin(){
        List<TableroDTO> tableros = service.listarDTO();

        return new ResponseEntity<List<TableroDTO>>(tableros, HttpStatus.OK);
    }

}
