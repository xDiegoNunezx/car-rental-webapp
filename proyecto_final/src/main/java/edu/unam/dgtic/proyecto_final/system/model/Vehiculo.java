package edu.unam.dgtic.proyecto_final.system.model;

import edu.unam.dgtic.proyecto_final.system.validation.NoEspacioVacio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vehiculo")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long vehiculo_id;
    @NoEspacioVacio(message = "El numero de placa no debe ser vacío")
    @Column(name = "numero_placa")
    private String numeroPlaca;
    @NoEspacioVacio(message = "El numero de placa no debe ser vacío")
    private String modelo;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_fabricacion")
    private LocalDate fechaFabricacion;
    @NotNull(message = "El kilometraje no debe ser vacío")
    private Integer kilometraje;
    @NotNull(message = "La capacidad de personas no debe ser vacía")
    @Column(name = "capacidad_personas")
    private Short capacidadPersonas;
    @NotNull(message = "El precio por día no debe ser vacío")
    @Column(name = "precio_dia")
    private Float precioDia;
    @NotNull(message = "La imagen no debe ser vacía")
    private byte[] imagen;
    @NoEspacioVacio(message = "La marca no debe ser vacía")
    private String marca;
    @NoEspacioVacio(message = "El tipo de combustible no debe ser vacío")
    @Column(name = "tipo_combustible")
    private String tipoCombustible;
    @NoEspacioVacio(message = "La transmisión no debe ser vacía")
    private String transmision;
    @NoEspacioVacio(message = "La carrocería no debe ser vacía")
    private String carroceria;
    @NotNull(message = "El estado disponible no debe ser vacío")
    private Boolean disponible;
}

