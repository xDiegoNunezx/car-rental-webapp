package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.MarcaVehiculo;

import java.util.List;
import java.util.Optional;

public interface MarcaVehiculoService {
    List<MarcaVehiculo> obtenerTodos();
    MarcaVehiculo obtenerPorDescripcion(String descripcion);
    MarcaVehiculo findById(Long marcaId);
}
