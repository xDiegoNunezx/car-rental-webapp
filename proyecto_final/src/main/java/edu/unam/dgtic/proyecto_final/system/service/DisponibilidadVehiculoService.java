package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.DisponibilidadVehiculo;

import java.util.List;

public interface DisponibilidadVehiculoService {
    List<DisponibilidadVehiculo> obtenerTodos();
    DisponibilidadVehiculo obtenerPorDescripcion(String descripcion);
    DisponibilidadVehiculo findById(Long disponibilidadId);
}
