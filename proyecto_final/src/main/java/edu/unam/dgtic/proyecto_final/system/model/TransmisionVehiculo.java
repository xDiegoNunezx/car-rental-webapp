package edu.unam.dgtic.proyecto_final.system.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "transmisiones_vehiculo")
public class TransmisionVehiculo {
    @Id
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String descripcion;
}