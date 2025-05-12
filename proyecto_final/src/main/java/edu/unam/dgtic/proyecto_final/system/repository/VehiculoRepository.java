package edu.unam.dgtic.proyecto_final.system.repository;

import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {
    // Ver todos los vehículos disponibles actualmente
    Page<Vehiculo> findByDisponibleTrue(Pageable pageable);

    // Buscar vehículos disponibles por rango de fechas (sin reserva en ese periodo)
    @Query("SELECT v FROM Vehiculo v WHERE v.disponible = true AND " +
            "v.vehiculo_id NOT IN (" +
            "SELECT r.vehiculo.vehiculo_id FROM Reserva r WHERE " +
            "(:fechaInicio < r.fechaFin AND :fechaFin > r.fechaInicio) AND r.cancelada = false)")
    List<Vehiculo> findVehiculosDisponiblesEntreFechas(LocalDate fechaInicio, LocalDate fechaFin);

    List<Vehiculo> findByMarca(String marca);
    List<Vehiculo> findByTipoCombustible(String tipoCombustible);
    List<Vehiculo> findByCarroceria(String carroceria);
    Optional<Vehiculo> findByNumeroPlaca(String numeroPlaca);

    List<Vehiculo> findByMarcaAndTipoCombustibleAndCarroceria(String marca, String tipoCombustible, String carroceria);
    List<Vehiculo> findByMarcaAndTipoCombustible(String marca, String tipoCombustible);

}
