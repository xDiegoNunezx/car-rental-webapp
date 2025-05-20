package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.Reserva;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface ReservaService {
    List<Reserva> obtenerReservasDeCliente(Long clienteId);

    List<Reserva> obtenerReservasActivasDeCliente(Long clienteId);

    Reserva crearReserva(Long clienteId, Long vehiculoId, LocalDate inicio, LocalDate fin,
                 boolean asientoInfantil, boolean asientoElevador, boolean conductoresAdicionales);

    void cancelarReserva(Long reservaId);

    List<Reserva> obtenerReservasPorVehiculo(Long vehiculoId);

    List<Reserva> obtenerTodasReservas();
}
