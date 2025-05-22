package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.DisponibilidadVehiculo;
import edu.unam.dgtic.proyecto_final.system.repository.DisponibilidadVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisponibilidadVehiculoServiceImpl implements DisponibilidadVehiculoService {
    @Autowired
    DisponibilidadVehiculoRepository disponibilidadVehiculoRepository;


    @Override
    public List<DisponibilidadVehiculo> obtenerTodos() {
        return disponibilidadVehiculoRepository.findAll();
    }

    @Override
    public DisponibilidadVehiculo obtenerPorDescripcion(String descripcion) {
        return disponibilidadVehiculoRepository.findByDescripcion(descripcion);
    }

    @Override
    public DisponibilidadVehiculo findById(Long disponibilidadId) {
        return disponibilidadVehiculoRepository.findById(disponibilidadId).get();
    }
}
