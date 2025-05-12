package edu.unam.dgtic.proyecto_final.system.service;

import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import edu.unam.dgtic.proyecto_final.system.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class VehiculoServiceImpl implements VehiculoService {
    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Override
    public Page<Vehiculo> obtenerDisponibles(Pageable pageable) {
        List<Vehiculo> vehiculos = vehiculoRepository.findVehiculosDisponiblesEntreFechas(LocalDate.now(), LocalDate.now().plusDays(1L));
        int start = Math.min((int)pageable.getOffset(), vehiculos.size());
        int end = Math.min((start + pageable.getPageSize()), vehiculos.size());
        Page<Vehiculo> page=new PageImpl<>(vehiculos.subList(start,end),pageable,vehiculos.size());
        return page;
    }

    @Override
    public Page<Vehiculo> buscarDisponiblesEntreFechas(LocalDate inicio, LocalDate fin, Pageable pageable) {
        if (inicio.isAfter(fin)) {
            throw new IllegalArgumentException("La fecha de inicio debe ser anterior a la fecha final");
        }
        List<Vehiculo> vehiculos = vehiculoRepository.findVehiculosDisponiblesEntreFechas(inicio, fin);
        int start = Math.min((int)pageable.getOffset(), vehiculos.size());
        int end = Math.min((start + pageable.getPageSize()), vehiculos.size());
        Page<Vehiculo> page=new PageImpl<>(vehiculos.subList(start,end),pageable,vehiculos.size());
        return page;
    }

    @Override
    public Optional<Vehiculo> obtenerPorId(Long id) {
        return vehiculoRepository.findById(id);
    }

    @Override
    public List<Vehiculo> buscarPorFiltros(String marca, String tipoCombustible, String carroceria) {
        // Implementación de búsqueda por múltiples filtros
        if (marca != null && tipoCombustible != null && carroceria != null) {
            return vehiculoRepository.findByMarcaAndTipoCombustibleAndCarroceria(marca, tipoCombustible, carroceria);
        } else if (marca != null && tipoCombustible != null) {
            return vehiculoRepository.findByMarcaAndTipoCombustible(marca, tipoCombustible);
        } else if (marca != null) {
            return vehiculoRepository.findByMarca(marca);
        } else {
            return vehiculoRepository.findAll();
        }
    }

    @Override
    public Page<Vehiculo> findPage(Pageable pageable) {
        return vehiculoRepository.findAll(pageable);
    }
}
