package edu.unam.dgtic.proyecto_final.system.model;

import edu.unam.dgtic.proyecto_final.auth.model.Usuario;
import jakarta.persistence.*;
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
    @Column(name = "numero_licencia", nullable = false, unique = true, length = 20)
    private String numeroLicencia;

    @Column(name = "fecha_emision_licencia", nullable = false)
    private LocalDate fechaEmisionLicencia;

    @Column(name = "fecha_expiracion_licencia", nullable = false)
    private LocalDate fechaExpiracionLicencia;

    @PrePersist
    public void prePersist() {
        setEsAdministrador(false); // Los clientes nunca son admin
    }
}
