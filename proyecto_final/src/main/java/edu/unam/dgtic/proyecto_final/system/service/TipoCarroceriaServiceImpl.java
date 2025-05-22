package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.TipoCarroceriaVehiculo;
import edu.unam.dgtic.proyecto_final.system.repository.TipoCarroceriaVehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TipoCarroceriaServiceImpl implements TipoCarroceriaService {
    @Autowired
    TipoCarroceriaVehiculoRepository tipoCarroceriaVehiculoRepository;

    @Override
    public List<TipoCarroceriaVehiculo> obtenerTodos() {
        return tipoCarroceriaVehiculoRepository.findAll();
    }

    @Override
    public TipoCarroceriaVehiculo obtenerPorDescripcion(String descripcion) {
        return tipoCarroceriaVehiculoRepository.findByDescripcion(descripcion);
    }

    @Override
    public TipoCarroceriaVehiculo findById(Long tipoCarroceriaId) {
        return tipoCarroceriaVehiculoRepository.findById(tipoCarroceriaId).get();
    }
}
