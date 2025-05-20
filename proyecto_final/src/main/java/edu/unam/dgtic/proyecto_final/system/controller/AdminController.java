package edu.unam.dgtic.proyecto_final.system.controller;

import edu.unam.dgtic.proyecto_final.system.model.*;
import edu.unam.dgtic.proyecto_final.system.model.dto.VehiculoDto;
import edu.unam.dgtic.proyecto_final.system.repository.ReservaRepository;
import edu.unam.dgtic.proyecto_final.system.repository.TiposMantenimientoRepository;
import edu.unam.dgtic.proyecto_final.system.service.MantenimientoService;
import edu.unam.dgtic.proyecto_final.system.service.ReservaService;
import edu.unam.dgtic.proyecto_final.system.service.VehiculoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    VehiculoService vehiculoService;

    @Autowired
    MantenimientoService mantenimientoService;

    @Autowired
    ReservaService reservaService;

    @Autowired
    ReservaRepository reservaRepository;

    @Autowired
    TiposMantenimientoRepository tiposMantenimientoRepository;

    @GetMapping("")
    public String admin() {
        return "navegacion/admin";
    }

    @GetMapping("/reservations")
    public String reservas(Model model) {
        List<Reserva> reservas = reservaService.obtenerTodasReservas();
        model.addAttribute("reservas", reservas);
        return "navegacion/reservations";
    }

    @GetMapping("/metrics")
    public String estadisticas(Model model) {
        // 1. Vehículos más rentados (top 5)
        List<Object[]> resultados = reservaRepository.findTopVehiculosMasRentados();

        List<String> vehiculosLabels = new ArrayList<>();
        List<Long> vehiculosData = new ArrayList<>();

        for (Object[] resultado : resultados) {
            Vehiculo vehiculo = (Vehiculo) resultado[0];
            Long count = (Long) resultado[1];
            vehiculosLabels.add(vehiculo.getModelo());
            vehiculosData.add(count);
        }

        Map<String, Object> vehiculosMasRentados = new HashMap<>();
        vehiculosMasRentados.put("labels", vehiculosLabels);
        vehiculosMasRentados.put("data", vehiculosData);

        model.addAttribute("vehiculosMasRentados", vehiculosMasRentados);

        // 2. Ingresos mensuales (últimos 12 meses)
        List<Object[]> ingresos = reservaRepository.findIngresosMensuales(LocalDate.now().minusMonths(12));

        List<String> mesesLabels = new ArrayList<>();
        List<Double> ingresosData = new ArrayList<>();

        for (Object[] ingreso : ingresos) {
            mesesLabels.add((String) ingreso[0]);
            ingresosData.add((Double) ingreso[1]);
        }

        Map<String, Object> ingresosMensuales = new HashMap<>();
        ingresosMensuales.put("labels", mesesLabels);
        ingresosMensuales.put("data", ingresosData);

        model.addAttribute("ingresosMensuales", ingresosMensuales);

        // 3. Porcentaje de servicios adicionales
        long totalReservas = reservaRepository.count();
        long conAsientoInfantil = reservaRepository.countByAsientoInfantil(true);
        long conAsientoElevador = reservaRepository.countByAsientoElevador(true);
        long conConductoresAdicionales = reservaRepository.countByConductoresAdicionales(true);

        model.addAttribute("porcentajeAsientoInfantil",
                totalReservas > 0 ? Math.round((conAsientoInfantil * 100.0 / totalReservas)) : 0);
        model.addAttribute("porcentajeAsientoElevador",
                totalReservas > 0 ? Math.round((conAsientoElevador * 100.0 / totalReservas)) : 0);
        model.addAttribute("porcentajeConductoresAdicionales",
                totalReservas > 0 ? Math.round((conConductoresAdicionales * 100.0 / totalReservas)) : 0);

        // 4. Tasa de cancelación
        long reservasCanceladas = reservaRepository.countByCancelada(true);
        model.addAttribute("tasaCancelacion",
                totalReservas > 0 ? Math.round((reservasCanceladas * 100.0 / totalReservas)) : 0);

        return "navegacion/metrics";
    }

    @GetMapping("/maintenance")
    public String mantenimiento(Model model, @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size) {
        List<VehiculoDto> todosVehiculos = vehiculoService.findAll();
        List<VehiculoDto> listaMantenimiento = vehiculoService.findAllByDisponibilidad(2L);

        log.info("Vehiculos en mantenimiento: " + listaMantenimiento.size());

        model.addAttribute("vehiculos", todosVehiculos);
        model.addAttribute("vehiculosEnMantenimiento", listaMantenimiento);

        return "navegacion/maintenance";
    }

    @GetMapping("/detalle/{id}")
    public String mostrarDetalleVehiculo(@PathVariable Long id, Model model) {
        VehiculoDto vehiculo = vehiculoService.obtenerPorId(id).get();
        List<Mantenimiento> mantenimientos = mantenimientoService.obtenerPorVehiculo(id);
        List<TipoMantenimiento> tiposMantenimiento = tiposMantenimientoRepository.findAll();
        List<Reserva> reservas = reservaService.obtenerReservasPorVehiculo(id);

        model.addAttribute("vehiculo", vehiculo);
        model.addAttribute("mantenimientos", mantenimientos);
        model.addAttribute("reservas", reservas);
        model.addAttribute("tiposMantenimiento", tiposMantenimiento);
        model.addAttribute("today", LocalDate.now());

        return "navegacion/car-detail";
    }

    @PostMapping("/vehiculo-baja/{id}")
    public String bajaVehiculo(@PathVariable Long id) {
        vehiculoService.eliminar(id);
        return "redirect:/admin/maintenance";
    }

    @PostMapping("/nuevo-mantenimiento/{id}")
    public String nuevoMantenimiento(@PathVariable Long id,
                                     @RequestParam Long tipoMantenimiento,
                                     @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin,
                                     @RequestParam double costo,
                                     RedirectAttributes redirectAttributes) {

        Vehiculo vehiculo = vehiculoService.obtenerVehiculoEntidad(id).get();
        vehiculo.setDisponibilidad(new DisponibilidadVehiculo(2L,"En mantenimiento"));
        vehiculoService.guardar(vehiculo);
        log.info("Vehiculo actualizado a mantenimiento");

        TipoMantenimiento tipo = tiposMantenimientoRepository.findById(tipoMantenimiento).get();

        Mantenimiento mantenimiento = Mantenimiento.builder()
                .vehiculo(vehiculo)
                .tipoMantenimiento(tipo)
                .fechaInicio(fechaInicio)
                .fechaFin(fechaFin)
                .costo(costo)
                .build();

        mantenimientoService.guardar(mantenimiento);

        redirectAttributes.addFlashAttribute("mensajeExito","Mantenimiento registrado exitosamente");

        return "redirect:/admin/detalle/" + id;
    }

    @PostMapping("/editar-mantenimiento/{vehiculoId}")
    public String procesarEdicion(@PathVariable Long vehiculoId,
                                  @ModelAttribute Mantenimiento mantenimiento,
                                  RedirectAttributes redirectAttributes) {

        Vehiculo vehiculo = vehiculoService.obtenerVehiculoEntidad(vehiculoId).get();
        vehiculo.setDisponibilidad(new DisponibilidadVehiculo(1L,"Disponible"));
        vehiculoService.guardar(vehiculo);
        log.info("Vehiculo actualizado disponible");

        mantenimiento.setVehiculo(vehiculoService.obtenerVehiculoEntidad(vehiculoId).get());
        mantenimientoService.guardar(mantenimiento);
        redirectAttributes.addFlashAttribute("mensajeExito",
                "Mantenimiento actualizado exitosamente");

        return "redirect:/admin/detalle/" + vehiculoId;
    }

    @DeleteMapping("/eliminar-mantenimiento/{id}")
    @ResponseBody
    public void eliminarMantenimiento(@PathVariable Long id) {
        mantenimientoService.eliminar(id);
    }
}
