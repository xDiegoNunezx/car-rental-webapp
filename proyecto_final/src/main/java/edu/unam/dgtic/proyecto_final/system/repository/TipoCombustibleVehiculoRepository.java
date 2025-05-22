package edu.unam.dgtic.proyecto_final.system.repository;

import edu.unam.dgtic.proyecto_final.system.model.TipoCombustibleVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCombustibleVehiculoRepository extends JpaRepository<TipoCombustibleVehiculo, Long> {
    TipoCombustibleVehiculo findByDescripcion(String nombre);
}
