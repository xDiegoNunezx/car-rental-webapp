package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.TipoCombustibleVehiculo;

import java.util.List;

public interface TipoCombustibleVehiculoService {
    List<TipoCombustibleVehiculo> obtenerTodos();
    TipoCombustibleVehiculo obtenerPorDescripcion(String descripcion);
    TipoCombustibleVehiculo findById(Long tipoCombustibleId);
}
