package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.MarcaVehiculo;
import edu.unam.dgtic.proyecto_final.system.repository.MarcaVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarcaVehiculoServiceImpl implements MarcaVehiculoService {
    @Autowired
    MarcaVehiculoRepository marcaVehiculoRepository;

    @Override
    public List<MarcaVehiculo> obtenerTodos() {
        return marcaVehiculoRepository.findAll();
    }

    @Override
    public MarcaVehiculo obtenerPorDescripcion(String descripcion) {
        return marcaVehiculoRepository.findByNombre(descripcion);
    }

    @Override
    public MarcaVehiculo findById(Long marcaId) {
        return marcaVehiculoRepository.findById(marcaId).get();
    }
}
