package edu.unam.dgtic.proyecto_final.auth.model;

import edu.unam.dgtic.proyecto_final.system.validation.NoEspacioVacio;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NoEspacioVacio(message = "El RFC no debe ser vacío")
    @Column(length = 13, unique = true, nullable = false)
    private String rfc;

    @Email
    @NoEspacioVacio(message = "El email no debe ser vacío")
    @Column(length = 100, unique = true, nullable = false)
    private String email;

    @NoEspacioVacio(message = "El nombre no debe ser vacío")
    @Column(name = "nombre_completo", length = 100, nullable = false)
    private String nombreCompleto;

    @NoEspacioVacio(message = "El número de teléfono no debe estar vacío")
    @Column(name = "numero_telefono", length = 15, nullable = false)
    private String numeroTelefono;

    @NotNull(message = "La fecha no debe estar vacía")
    @PastOrPresent(message = "La fecha de nacimiento no puede ser en el futuro")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_nacimiento", nullable = false)
    private LocalDate fechaNacimiento;

    @NoEspacioVacio(message = "La contraseña no debe estar vacía")
    @Column(length = 100, nullable = false)
    private String contrasena;

    @NoEspacioVacio(message = "La dirección no debe estar vacía")
    @Column(length = 200, nullable = false)
    private String direccion;

    @NoEspacioVacio(message = "El país no debe estar vacío")
    @Column(length = 50, nullable = false)
    private String pais;

    @NoEspacioVacio(message = "La ciudad no debe estar vacía")
    @Column(length = 50, nullable = false)
    private String ciudad;

    @Column(name = "es_administrador", nullable = false)
    private boolean esAdministrador;
}
