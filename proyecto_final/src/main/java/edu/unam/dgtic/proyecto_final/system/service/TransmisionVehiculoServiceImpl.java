package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.TransmisionVehiculo;
import edu.unam.dgtic.proyecto_final.system.repository.TransmisionVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransmisionVehiculoServiceImpl implements TransmisionVehiculoService {
    @Autowired
    TransmisionVehiculoRepository transmisionVehiculoRepository;

    @Override
    public List<TransmisionVehiculo> obtenerTodos() {
        return transmisionVehiculoRepository.findAll();
    }

    @Override
    public TransmisionVehiculo obtenerPorDescripcion(String descripcion) {
        return transmisionVehiculoRepository.findByDescripcion(descripcion);
    }

    @Override
    public TransmisionVehiculo findById(Long transmisionId) {
        return transmisionVehiculoRepository.findById(transmisionId).get();
    }
}
