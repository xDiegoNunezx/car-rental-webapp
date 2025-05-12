package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VehiculoService {
    Page<Vehiculo> obtenerDisponibles(Pageable pageable);
    Page<Vehiculo> buscarDisponiblesEntreFechas(LocalDate inicio, LocalDate fin, Pageable pageable);
    Optional<Vehiculo> obtenerPorId(Long id);
    List<Vehiculo> buscarPorFiltros(String marca, String tipoCombustible, String carroceria);
    Page<Vehiculo> findPage(Pageable pageable);
}
