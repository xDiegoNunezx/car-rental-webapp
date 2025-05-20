package edu.unam.dgtic.proyecto_final.system.repository;

import edu.unam.dgtic.proyecto_final.system.model.Mantenimiento;
import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MantenimientoRepository extends JpaRepository<Mantenimiento, Long> {
    // Consulta para obtener todos los mantenimientos de un vehículo específico
    List<Mantenimiento> findByVehiculo(Vehiculo vehiculo);

    // Consulta alternativa usando el ID del vehículo
    List<Mantenimiento> findByVehiculoId(Long vehiculoId);

    // Consultas adicionales que podrían ser útiles

    // Encontrar mantenimientos por rango de fechas
    List<Mantenimiento> findByFechaInicioBetween(LocalDate fechaInicio, LocalDate fechaFin);

    // Encontrar mantenimientos por tipo de mantenimiento
    List<Mantenimiento> findByTipoMantenimientoId(Long tipoMantenimientoId);

    // Consulta personalizada para encontrar mantenimientos de un vehículo en un rango de fechas
    @Query("SELECT m FROM Mantenimiento m WHERE m.vehiculo.id = :vehiculoId AND m.fechaInicio BETWEEN :fechaInicio AND :fechaFin")
    List<Mantenimiento> buscarMantenimientosPorVehiculoYFechas(
            @Param("vehiculoId") Long vehiculoId,
            @Param("fechaInicio") LocalDate fechaInicio,
            @Param("fechaFin") LocalDate fechaFin);

    // Consulta para obtener mantenimientos con costo mayor a cierto valor
    List<Mantenimiento> findByVehiculoIdAndCostoGreaterThan(Long vehiculoId, double costo);
}