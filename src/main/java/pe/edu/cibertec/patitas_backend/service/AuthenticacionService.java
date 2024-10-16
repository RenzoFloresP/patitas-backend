package pe.edu.cibertec.patitas_backend.service;

import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;

import java.io.IOException;

public interface AuthenticacionService {
    String[] validarUsuario(LoginRequestDTO loginRequestDTO) throws IOException;
}
