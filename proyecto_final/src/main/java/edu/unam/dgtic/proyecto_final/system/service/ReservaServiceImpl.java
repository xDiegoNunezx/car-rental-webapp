package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.Cliente;
import edu.unam.dgtic.proyecto_final.system.model.Reserva;
import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import edu.unam.dgtic.proyecto_final.system.repository.ClienteRepository;
import edu.unam.dgtic.proyecto_final.system.repository.ReservaRepository;
import edu.unam.dgtic.proyecto_final.system.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ReservaServiceImpl implements ReservaService {
    @Autowired
    private ReservaRepository reservaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public List<Reserva> obtenerReservasDeCliente(Long clienteId) {
        return reservaRepository.findByCliente_Id(clienteId);
    }

    @Override
    public List<Reserva> obtenerReservasActivasDeCliente(Long clienteId) {
        return reservaRepository.findReservasActivasByClienteId(clienteId);
    }

    @Override
    @Transactional
    public Reserva crearReserva(Long clienteId, Long vehiculoId, LocalDate inicio, LocalDate fin,
                                boolean asientoInfantil, boolean asientoElevador, boolean conductoresAdicionales) {
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Vehiculo vehiculo = vehiculoRepository.findById(vehiculoId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        // Validar disponibilidad
        boolean estaDisponible = reservaRepository
                .findReservasPorVehiculoYFechas(vehiculoId, inicio, fin).isEmpty();

        if (!estaDisponible) {
            throw new RuntimeException("El vehículo no está disponible en esas fechas.");
        }

        long dias = ChronoUnit.DAYS.between(inicio, fin);
        double total = dias * vehiculo.getPrecioDia();

        Reserva reserva = Reserva.builder()
                .fechaInicio(inicio)
                .fechaFin(fin)
                .fechaReserva(LocalDate.now())
                .vehiculo(vehiculo)
                .cliente(cliente)
                .asientoInfantil(asientoInfantil)
                .asientoElevador(asientoElevador)
                .conductoresAdicionales(conductoresAdicionales)
                .pagoTotal(total)
                .cancelada(false)
                .build();

        return reservaRepository.save(reserva);
    }

    @Override
    @Transactional
    public void cancelarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setCancelada(true);
        reservaRepository.save(reserva);
    }

    @Override
    public List<Reserva> obtenerReservasPorVehiculo(Long vehiculoId) {
        return reservaRepository.findReservasByVehiculo(vehiculoId);
    }

    @Override
    public List<Reserva> obtenerTodasReservas() {
        return reservaRepository.findAll();
    }

    @Override
    public Reserva obtenerReservaPorId(Long reservaId) {
        return reservaRepository.findById(reservaId).get();
    }

    public boolean verificarDisponibilidad(Vehiculo vehiculo) {
        return !reservaRepository.existsReservaByVehiculo(vehiculo);
    }
}
