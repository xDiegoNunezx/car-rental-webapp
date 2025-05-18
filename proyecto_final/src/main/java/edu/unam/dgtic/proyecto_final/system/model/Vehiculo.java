package edu.unam.dgtic.proyecto_final.system.model;

import edu.unam.dgtic.proyecto_final.system.model.dto.VehiculoDto;
import edu.unam.dgtic.proyecto_final.system.validation.NoEspacioVacio;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "vehiculos")
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NoEspacioVacio(message = "El número de placa no debe ser vacío")
    @Column(name = "numero_placa", unique = true, nullable = false, length = 20)
    private String numeroPlaca;

    @ManyToOne
    @JoinColumn(name = "marca_id", nullable = false)
    private MarcaVehiculo marca;

    @NoEspacioVacio(message = "El modelo no debe ser vacío")
    @Column(nullable = false, length = 50)
    private String modelo;

    @NotNull(message = "La fecha de fabricación no debe ser vacía")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "fecha_fabricacion", nullable = false)
    private LocalDate fechaFabricacion;

    @ManyToOne
    @JoinColumn(name = "tipo_combustible_id", nullable = false)
    private TipoCombustibleVehiculo tipoCombustible;

    @NotNull(message = "El kilometraje no debe ser vacío")
    @Column(nullable = false)
    private Integer kilometraje;

    @ManyToOne
    @JoinColumn(name = "transmision_id", nullable = false)
    private TransmisionVehiculo transmision;

    @NotNull(message = "La capacidad de personas no debe ser vacía")
    @Column(name = "capacidad_personas", nullable = false)
    private Short capacidadPersonas;

    @ManyToOne
    @JoinColumn(name = "disponibilidad_id", nullable = false)
    private DisponibilidadVehiculo disponibilidad;

    @NotNull(message = "El precio por día no debe ser vacío")
    @Column(name = "precio_dia", nullable = false)
    private double precioDia;

    @Lob
    private byte[] imagen;

    @ManyToOne
    @JoinColumn(name = "tipo_carroceria_id", nullable = false)
    private TipoCarroceriaVehiculo tipoCarroceria;

    public VehiculoDto toDto() {
        VehiculoDto dto = VehiculoDto.builder()
                .id(this.id)
                .numeroPlaca(this.numeroPlaca)
                .marca(this.marca != null ? this.marca.getNombre() : "")
                .modelo(this.modelo)
                .fechaFabricacion(this.fechaFabricacion)
                .tipoCombustible(this.tipoCombustible != null ? this.tipoCombustible.getDescripcion() : "")
                .kilometraje(this.kilometraje)
                .transmision(this.transmision != null ? this.transmision.getDescripcion() : "")
                .capacidadPersonas(this.capacidadPersonas)
                .disponibilidad(this.disponibilidad != null ? this.disponibilidad.getDescripcion() : "")
                .precioDia(this.precioDia)
                .tipoCarroceria(this.tipoCarroceria != null ? this.tipoCarroceria.getDescripcion() : "")
                .build();

        if (this.imagen != null && this.imagen.length > 0) {
            String tipoImagen = "image/jpeg"; // valor por defecto
            try (InputStream is = new ByteArrayInputStream(this.imagen)) {
                String detected = URLConnection.guessContentTypeFromStream(is);
                if (detected != null) {
                    tipoImagen = detected;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            dto.setImagenBase64("data:" + tipoImagen + ";base64," + Base64.getEncoder().encodeToString(this.imagen));
        }

        return dto;
    }
}