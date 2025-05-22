package edu.unam.dgtic.proyecto_final.system.repository;

import edu.unam.dgtic.proyecto_final.system.model.TransmisionVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransmisionVehiculoRepository extends JpaRepository<TransmisionVehiculo, Long> {
    TransmisionVehiculo findByDescripcion(String descripcion);
}
