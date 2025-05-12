package edu.unam.dgtic.proyecto_final.system.model;

import edu.unam.dgtic.proyecto_final.system.validation.NoEspacioVacio;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usuarioId")
    private Long usuarioId;
    @NoEspacioVacio(message = "El RFC no debe ser vacío")
    private String rfc;
    @Email
    @NoEspacioVacio(message = "El email no debe ser vacío")
    private String email;
    @NoEspacioVacio(message = "El nombre no debe ser vacío")
    private String nombreCompleto;
    @NoEspacioVacio(message = "El número de teléfono no debe estar vacío")
    private String numeroTelefono;
    @NotNull(message = "La fecha no debe estar vacía")
    @PastOrPresent(message = "La fecha de nacimiento no puede ser en el futuro")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;
    @NoEspacioVacio(message = "La contraseña no debe estar vacía")
    private String contrasena;
    private boolean esAdministrador;
    @NoEspacioVacio(message = "La dirección no debe estar vacía")
    private String direccion;
    @NoEspacioVacio(message = "El país no debe estar vacío")
    private String pais;
    @NoEspacioVacio(message = "La ciudad no debe estar vacía")
    private String ciudad;
}
