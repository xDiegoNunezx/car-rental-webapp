package edu.unam.dgtic.proyecto_final.system.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculoDto {
    private Long vehiculoId;
    private String numeroPlaca;
    private String modelo;
    private LocalDate fechaFabricacion;
    private Integer kilometraje;
    private Short capacidadPersonas;
    private Float precioDia;
    private String marca;
    private String tipoCombustible;
    private String transmision;
    private String carroceria;
    private Boolean disponible;
    private String imagenBase64; // <-- para Thymeleaf
}
