package edu.unam.dgtic.proyecto_final.system.repository;

import edu.unam.dgtic.proyecto_final.system.model.DisponibilidadVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisponibilidadVehiculoRepository extends JpaRepository<DisponibilidadVehiculo, Long> {
    DisponibilidadVehiculo findByDescripcion(String descripcion);
}
