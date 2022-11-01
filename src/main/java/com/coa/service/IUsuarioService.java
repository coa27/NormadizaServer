package com.coa.service;

import com.coa.model.Usuario;

public interface IUsuarioService extends ICRUD<Usuario, Long> {

    Usuario retornarUsuario();

}
