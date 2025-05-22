package edu.unam.dgtic.proyecto_final.system.repository;

import edu.unam.dgtic.proyecto_final.system.model.MarcaVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaVehiculoRepository extends JpaRepository<MarcaVehiculo, Long> {
    MarcaVehiculo findByNombre(String nombre);
}
