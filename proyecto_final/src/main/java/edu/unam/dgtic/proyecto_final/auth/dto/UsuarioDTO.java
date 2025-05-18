package edu.unam.dgtic.proyecto_final.auth.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsuarioDTO {
    private Long id;
    private String rfc;
    private String email;
    private String nombreCompleto;
    private String numeroTelefono;
    private LocalDate fechaNacimiento;
    private String contrasena;
    private String direccion;
    private String pais;
    private String ciudad;
    private Boolean esAdministrador;
}
