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
public class ReservaDate {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
}
