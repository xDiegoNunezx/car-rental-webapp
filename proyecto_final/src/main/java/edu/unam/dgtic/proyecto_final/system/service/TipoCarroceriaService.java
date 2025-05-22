package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.TipoCarroceriaVehiculo;

import java.util.List;

public interface TipoCarroceriaService {
    List<TipoCarroceriaVehiculo> obtenerTodos();
    TipoCarroceriaVehiculo obtenerPorDescripcion(String descripcion);
    TipoCarroceriaVehiculo findById(Long tipoCarroceriaId);
}
