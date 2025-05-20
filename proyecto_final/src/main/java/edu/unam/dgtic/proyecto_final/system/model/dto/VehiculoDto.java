package edu.unam.dgtic.proyecto_final.system.model.dto;

import edu.unam.dgtic.proyecto_final.system.model.DisponibilidadVehiculo;
import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VehiculoDto {
    private Long id;
    private String numeroPlaca;
    private String marca;
    private String modelo;
    private LocalDate fechaFabricacion;
    private String tipoCombustible;
    private Integer kilometraje;
    private String transmision;
    private Short capacidadPersonas;
    private String disponibilidad;
    private Double precioDia;
    private String imagenBase64;
    private String tipoCarroceria;
}
