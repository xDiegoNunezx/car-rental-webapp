package edu.unam.dgtic.proyecto_final.system.repository;

import edu.unam.dgtic.proyecto_final.system.model.Reserva;
import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {
    // Ver reservas activas de un usuario
    @Query("SELECT r FROM Reserva r WHERE r.cliente.id = :usuarioId AND r.cancelada = false")
    default List<Reserva> findReservasActivasByClienteId(Long usuarioId) {
        return null;
    }

    // Ver historial de reservas (activas o canceladas)
    List<Reserva> findByCliente_Id(Long usuarioId);

    @Query("SELECT r FROM Reserva r WHERE r.cliente.id = :usuarioId AND r.cancelada = false")
    List<Reserva> findReservasActivasPorUsuario(@Param("usuarioId") Long usuarioId);

    // Buscar reservas por vehículo y fechas (para validar disponibilidad)
    @Query("SELECT r FROM Reserva r WHERE r.vehiculo.id = :vehiculoId AND " +
            "(:fechaInicio < r.fechaFin AND :fechaFin > r.fechaInicio) AND r.cancelada = false")
    List<Reserva> findReservasPorVehiculoYFechas(Long vehiculoId, LocalDate fechaInicio, LocalDate fechaFin);

    @Query("SELECT r FROM Reserva r WHERE r.vehiculo.id = :vehiculoId " +
            "AND r.cancelada = false " +
            "AND r.fechaFin >= CURRENT_DATE")
    List<Reserva> findReservasFuturasPorVehiculo(@Param("vehiculoId") Long vehiculoId);

    Boolean existsReservaByVehiculo(Vehiculo vehiculo);
}
