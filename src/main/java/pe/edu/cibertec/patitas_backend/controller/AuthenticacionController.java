package pe.edu.cibertec.patitas_backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.edu.cibertec.patitas_backend.dto.LoginRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LoginResponseDTO;
import pe.edu.cibertec.patitas_backend.dto.LogoutRequestDTO;
import pe.edu.cibertec.patitas_backend.dto.LogoutResponseDTO;
import pe.edu.cibertec.patitas_backend.service.AuthenticacionService;
import pe.edu.cibertec.patitas_backend.service.LogOutService;

import java.time.Duration;
import java.util.Arrays;

@RestController
@RequestMapping("/autenticacion")
public class AuthenticacionController {

    @Autowired
    AuthenticacionService authenticacionService;

    @Autowired
    LogOutService logOutService;

    @PostMapping("/login")
    public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO){

        try {
            Thread.sleep(Duration.ofSeconds(5));

            String[] datosUsuario = authenticacionService.validarUsuario(loginRequestDTO);
            System.out.println("Resultado: " + Arrays.toString(datosUsuario));

            if(datosUsuario == null){
                return new LoginResponseDTO("01", "Error:No existe el usuario", "", "");
            }

            return new LoginResponseDTO("00", "", datosUsuario[0], datosUsuario[1]);

        } catch (Exception e) {
            return new LoginResponseDTO("99", "Error, intente  nuevmente", "", "");
        }
    }

    @PostMapping("/logout")
    public LogoutResponseDTO logout(@RequestBody LogoutRequestDTO logoutRequestDTO){
        System.out.println(logoutRequestDTO);
        try {
            Thread.sleep(Duration.ofSeconds(5));

            if (logoutRequestDTO == null) {
                return new LogoutResponseDTO("99", "Error: al guardar" + logoutRequestDTO);
            }

            logOutService.registrarCerrarSesion(logoutRequestDTO.nombreUsuario());

            return new LogoutResponseDTO("00", "Sesión finalizada");
        } catch (Exception e) {
            return new LogoutResponseDTO("99", "Error: Ocurrio un problema");
        }
    }
}
