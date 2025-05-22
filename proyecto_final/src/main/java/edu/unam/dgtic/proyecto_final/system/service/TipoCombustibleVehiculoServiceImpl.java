package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.TipoCombustibleVehiculo;
import edu.unam.dgtic.proyecto_final.system.repository.TipoCombustibleVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoCombustibleVehiculoServiceImpl implements TipoCombustibleVehiculoService {
    @Autowired
    TipoCombustibleVehiculoRepository tipoCombustibleVehiculoRepository;

    @Override
    public List<TipoCombustibleVehiculo> obtenerTodos() {
        return tipoCombustibleVehiculoRepository.findAll();
    }

    @Override
    public TipoCombustibleVehiculo obtenerPorDescripcion(String descripcion) {
        return tipoCombustibleVehiculoRepository.findByDescripcion(descripcion);
    }

    @Override
    public TipoCombustibleVehiculo findById(Long tipoCombustibleId) {
        return tipoCombustibleVehiculoRepository.findById(tipoCombustibleId).get();
    }
}
