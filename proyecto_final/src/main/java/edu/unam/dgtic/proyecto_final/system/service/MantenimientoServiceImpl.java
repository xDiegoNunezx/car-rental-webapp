package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.Mantenimiento;
import edu.unam.dgtic.proyecto_final.system.repository.MantenimientoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MantenimientoServiceImpl implements MantenimientoService {
    @Autowired
    private MantenimientoRepository mantenimientoRepository;

    @Override
    public Mantenimiento guardar(Mantenimiento mantenimiento) {
        return mantenimientoRepository.save(mantenimiento);
    }

    @Override
    public List<Mantenimiento> obtenerPorVehiculo(Long vehiculoId) {
        return mantenimientoRepository.findByVehiculoId(vehiculoId);
    }

    @Override
    public void eliminar(Long mantenimientoId) {
        mantenimientoRepository.deleteById(mantenimientoId);
    }
}
