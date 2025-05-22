package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.TransmisionVehiculo;

import java.util.List;

public interface TransmisionVehiculoService {
    List<TransmisionVehiculo> obtenerTodos();
    TransmisionVehiculo obtenerPorDescripcion(String descripcion);
    TransmisionVehiculo findById(Long transmisionId);
}
