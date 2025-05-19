package edu.unam.dgtic.proyecto_final.system.model;

import edu.unam.dgtic.proyecto_final.auth.model.Usuario;
import edu.unam.dgtic.proyecto_final.system.validation.NoEspacioVacio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "clientes")
@PrimaryKeyJoinColumn(name = "id")
public class Cliente extends Usuario {
    @NoEspacioVacio(message = "El número de licencia no debe ser vacío")
    @Column(name = "numero_licencia", nullable = false, unique = true, length = 20)
    @Size(min = 10, message = "El número de licencia debe tener al menos 10 dígitos")
    private String numeroLicencia;

    @NotNull(message = "La fecha de emisión no debe estar vacía")
    @Column(name = "fecha_emision_licencia", nullable = false)
    private LocalDate fechaEmisionLicencia;

    @NotNull(message = "La fecha de expiración no debe estar vacía")
    @Column(name = "fecha_expiracion_licencia", nullable = false)
    private LocalDate fechaExpiracionLicencia;

    @PrePersist
    public void prePersist() {
        setEsAdministrador(false); // Los clientes nunca son admin
    }
}
