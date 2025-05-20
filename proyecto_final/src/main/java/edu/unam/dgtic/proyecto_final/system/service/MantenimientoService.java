package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.Mantenimiento;

import java.util.List;

public interface MantenimientoService {
    Mantenimiento guardar(Mantenimiento mantenimiento);
    List<Mantenimiento> obtenerPorVehiculo(Long vehiculoId);
    void eliminar(Long mantenimientoId);
}
