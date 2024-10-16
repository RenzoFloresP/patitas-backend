package pe.edu.cibertec.patitas_backend.service.impl;

import org.springframework.stereotype.Service;
import pe.edu.cibertec.patitas_backend.service.LogOutService;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class LogOutServiceImpl implements LogOutService {

    @Override
    public String registrarCerrarSesion(String nombreUsuario) {
        String usuariosFilePath = "src/main/resources/usuarios.txt";
        String salidaFilePath = "src/main/resources/logout.txt";
        String tipoDocumento = null;
        String numeroDocumento = null;

        try {
            List<String> lines = Files.readAllLines(Paths.get(usuariosFilePath));
            for (String line : lines) {
                String[] parts = line.split(";");
                if (parts[3].equals(nombreUsuario)) {
                    tipoDocumento = parts[1];
                    numeroDocumento = parts[2];
                    break;
                }
            }

            if (tipoDocumento == null || numeroDocumento == null) {
                return "Error: Usuario no encontrado en usuarios.txt";
            }

            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedDate = now.format(formatter);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(salidaFilePath, true))) {
                writer.write("Tipo Documento: " + tipoDocumento + ", Numero Documento: " + numeroDocumento + ", Fecha: " + formattedDate);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
                return "Error al registrar el cerrado de sesión en logout.txt";
            }

        } catch (IOException e) {
            e.printStackTrace();
            return "Error al leer el archivo usuarios.txt";
        }

        return "Cerrado de sesión registrado correctamente";
    }
}

