package com.coa.controller;

import com.coa.model.Usuario;
import com.coa.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private IUsuarioService service;

    @GetMapping
    ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = service.listar();

        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    ResponseEntity<Usuario> listarUsuarioPorId(@PathVariable("id") Long id){
        Usuario usuario = service.listarPorId(id);

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @PostMapping
    ResponseEntity<Usuario> agregarUsuario(@RequestBody @Validated Usuario usuario){
        Usuario u = service.registrar(usuario);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(u.getIdUsuario()).toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarUsuario(@PathVariable("id") Long id){
        Usuario usuario = service.listarPorId(id);

        service.eliminar(usuario.getIdUsuario());

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
