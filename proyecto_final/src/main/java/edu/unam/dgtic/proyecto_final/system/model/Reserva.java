package edu.unam.dgtic.proyecto_final.system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "reservas")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha no debe estar vacía")
    @FutureOrPresent(message = "La fecha de inicio no puede ser en el pasado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @NotNull(message = "La fecha no debe estar vacía")
    @Future(message = "La fecha de inicio no puede ser en el pasado ni hoy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_fin")
    private LocalDate fechaFin;

    @NotNull(message = "El asiento infantil no debe ser nulo")
    @Column(name = "asiento_infantil")
    private boolean asientoInfantil;

    @NotNull(message = "El asiento Elevador no debe ser nulo")
    @Column(name = "asiento_elevador")
    private boolean asientoElevador;

    @NotNull(message = "Conductores adicionales no debe ser nulo")
    @Column(name = "conductores_adicionales")
    private boolean conductoresAdicionales;

    @NotNull(message = "El pago total no debe ser nulo")
    @Column(name = "pago_total")
    private double pagoTotal;

    @NotNull(message = "La fecha de reserva no debe estar vacía")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_reserva", nullable = false)
    private LocalDate fechaReserva;

    @NotNull(message = "El estatus de cancelada no debe estar vacío")
    private boolean cancelada;

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;
}
