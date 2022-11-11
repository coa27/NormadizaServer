package com.coa.service.impl;

import com.coa.dto.LoginRequestDTO;
import com.coa.dto.LoginResponseDTO;
import com.coa.dto.RegistroDTO;
import com.coa.exception.ErrorRegistroException;
import com.coa.model.Rol;
import com.coa.model.Usuario;
import com.coa.repo.IRolRepo;
import com.coa.repo.IUsuarioRepo;
import com.coa.security.UserDetailsImpl;
import com.coa.service.IAuthService;
import com.coa.service.IUsuarioService;
import com.coa.utils.TokenUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthServiceImpl implements IAuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenUtils tokenUtils;

    @Autowired
    private IUsuarioRepo usuarioRepo;

    @Autowired
    private IRolRepo rolRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private Usuario parseoUserDetailsServiceToUsuario(UserDetailsImpl userDetails){
        return new Usuario(
                userDetails.getIdUsuario(),
                userDetails.getNombre(),
                userDetails.getContrasenia(),
                userDetails.getEmail(),
                userDetails.getCreateAt(),
                userDetails.getUpdatedAt(),
                userDetails.getRoles()
        );
    }


    @Override
    public LoginResponseDTO acceder(LoginRequestDTO loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequestDTO.getEmailOrUsuario(), loginRequestDTO.getPassword())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenUtils.generarToken(authentication);
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        LoginResponseDTO responseDTO = new LoginResponseDTO(parseoUserDetailsServiceToUsuario(principal), token);

        return responseDTO;
    }

    @Override
    public Usuario registro(RegistroDTO registroDTO) {
        if (usuarioRepo.findByEmail(registroDTO.getEmail()).isPresent()){
            throw new ErrorRegistroException("Este email ya existe");
        }
        if (usuarioRepo.findByNombre(registroDTO.getNombre()).isPresent()){
            throw new ErrorRegistroException("Este usuario ya existe");
        }

        Usuario usuario = new Usuario();
        BeanUtils.copyProperties(registroDTO, usuario);
        usuario.setContrasenia(passwordEncoder.encode(registroDTO.getContrasenia()));
        rolUsuario(usuario);
        return usuarioRepo.save(usuario);
    }

    public AuthServiceImpl(AuthenticationManager authenticationManager, TokenUtils tokenUtils) {
        this.authenticationManager = authenticationManager;
        this.tokenUtils = tokenUtils;
    }

    private void rolUsuario(Usuario usuario){
        Set<Rol> rol = new HashSet<>();
        rol.add(rolRepo.findByRoleNombre("USER").orElseThrow(() -> new ErrorRegistroException("ERROR CON EL ROL")));
        usuario.setRoles(new HashSet<>(rol));
    }
}
