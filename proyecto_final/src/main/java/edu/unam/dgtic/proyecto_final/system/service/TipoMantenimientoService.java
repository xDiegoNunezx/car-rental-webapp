package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.TipoMantenimiento;

import java.util.List;

public interface TipoMantenimientoService {
    List<TipoMantenimiento> obtenerTodos();
    TipoMantenimiento obtenerPorDescripcion(String descripcion);
}
