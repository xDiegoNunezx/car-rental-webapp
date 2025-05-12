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
@Table(name = "reserva")
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservaId;
    @NotNull(message = "La fecha no debe estar vacía")
    @FutureOrPresent(message = "La fecha de inicio no puede ser en el pasado")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaInicio;
    @NotNull(message = "La fecha no debe estar vacía")
    @Future(message = "La fecha de inicio no puede ser en el pasado ni hoy")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaFin;
    @NotNull(message = "El asiento infantil no debe ser nulo")
    private boolean asientoInfantil;
    @NotNull(message = "El asiento Elevador no debe ser nulo")
    private boolean asientoElevador;
    @NotNull(message = "Condcutores adicionales no debe ser nulo")
    private boolean conductoresAdicionales;
    @NotNull(message = "El pago total no debe ser nulo")
    private float pagoTotal;
    @NotNull(message = "La fecha de reserva no debe estar vacíaa")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaReserva;
    @NotNull(message = "El estatus de cancelada no debe estar vacío")
    private boolean cancelada;

    @ManyToOne
    @JoinColumn(name = "vehiculoId", nullable = false)
    private Vehiculo vehiculo;

    @ManyToOne
    @JoinColumn(name = "usuarioID", nullable = false)
    private Usuario usuario;
}
