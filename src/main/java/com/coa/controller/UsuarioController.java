package com.coa.controller;

import com.coa.model.Usuario;
import com.coa.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasRole('admin')")
    ResponseEntity<List<Usuario>> listarUsuarios(){
        List<Usuario> usuarios = service.listar();

        return new ResponseEntity<List<Usuario>>(usuarios, HttpStatus.OK);
    }

    @GetMapping("/perfil")
    ResponseEntity<Usuario> listarUsuarioPorId(){
        Usuario usuario = service.retornarUsuario();

        return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> eliminarUsuario(@PathVariable("id") Long id){
        Usuario usuario = service.listarPorId(id);

        service.eliminar(usuario.getIdUsuario());

        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

}
