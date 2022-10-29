package com.coa.service;

import com.coa.dto.LoginRequestDTO;
import com.coa.dto.LoginResponseDTO;
import com.coa.dto.RegistroDTO;
import com.coa.model.Usuario;

public interface IAuthService {

    LoginResponseDTO acceder(LoginRequestDTO loginRequestDTO);
    Usuario registro(RegistroDTO registroDTO);

}
