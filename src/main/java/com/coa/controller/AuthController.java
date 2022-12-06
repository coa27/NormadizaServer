package com.coa.controller;

import com.coa.dto.LoginRequestDTO;
import com.coa.dto.LoginResponseDTO;
import com.coa.dto.RegistroDTO;
import com.coa.model.Usuario;
import com.coa.service.IAuthService;
import com.coa.service.IUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private IAuthService service;

    @Autowired
    private IUsuarioService usuarioService;

    @PostMapping("/acceder")
    public ResponseEntity<LoginResponseDTO> acceder(@RequestBody @Validated LoginRequestDTO loginRequestDTO){
        LoginResponseDTO loginResponseDTO = service.acceder(loginRequestDTO);

        return new ResponseEntity<>(loginResponseDTO, HttpStatus.OK);
    }

    @PostMapping("/registro")
    ResponseEntity<Usuario> registrar(@RequestBody @Validated RegistroDTO registroDTO){
        Usuario usuario = service.registro(registroDTO);

        return new ResponseEntity<>(usuario, HttpStatus.CREATED);
    }

    @GetMapping("/validar")
    ResponseEntity<Void> validarToken(@RequestHeader("x-token") @NotNull String token){
        service.validacion(token);

        return new ResponseEntity<>(HttpStatus.OK);
    }



}
