package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.TipoMantenimiento;
import edu.unam.dgtic.proyecto_final.system.repository.TipoMantenimientoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoMantenimientoServiceImpl implements TipoMantenimientoService {
    @Autowired
    TipoMantenimientoRepository tipoMantenimientoRepository;

    @Override
    public List<TipoMantenimiento> obtenerTodos() {
        return tipoMantenimientoRepository.findAll();
    }

    @Override
    public TipoMantenimiento obtenerPorDescripcion(String descripcion) {
        return null;
    }
}
