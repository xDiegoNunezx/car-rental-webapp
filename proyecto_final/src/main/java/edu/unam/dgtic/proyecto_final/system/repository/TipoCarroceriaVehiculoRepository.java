package edu.unam.dgtic.proyecto_final.system.repository;

import edu.unam.dgtic.proyecto_final.system.model.TipoCarroceriaVehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TipoCarroceriaVehiculoRepository extends JpaRepository<TipoCarroceriaVehiculo, Long> {
    TipoCarroceriaVehiculo findByDescripcion(String nombre);
}
