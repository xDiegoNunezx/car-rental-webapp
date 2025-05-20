package edu.unam.dgtic.proyecto_final.system.repository;

import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.stereotype.Repository;


import java.time.LocalDate;
import java.util.List;

@Repository
public interface VehiculoRepository extends JpaRepository<Vehiculo,Long> {
    // Buscar vehículos disponibles por rango de fechas (sin reserva en ese periodo)
    @Query("SELECT v FROM Vehiculo v WHERE v.disponibilidad.id = 1  AND " +
            "v.id NOT IN (" +
            "SELECT r.vehiculo.id FROM Reserva r WHERE " +
            "(:fechaInicio < r.fechaFin AND :fechaFin > r.fechaInicio) AND r.cancelada = false)")
    List<Vehiculo> findVehiculosDisponiblesEntreFechas(LocalDate fechaInicio, LocalDate fechaFin);

    List<Vehiculo> findByMarcaNombre(String marca);
    List<Vehiculo> findByMarca_NombreAndTipoCombustible_DescripcionAndTipoCarroceria_Descripcion(String marca, String tipoCombustible, String carroceria);
    List<Vehiculo> findByMarca_NombreAndTipoCombustible_Descripcion(String marca, String tipoCombustible);
    List<Vehiculo> findByDisponibilidad_Id(Long id);
}
