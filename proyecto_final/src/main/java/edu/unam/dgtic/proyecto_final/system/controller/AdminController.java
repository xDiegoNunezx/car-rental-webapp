package edu.unam.dgtic.proyecto_final.system.controller;

import edu.unam.dgtic.proyecto_final.system.model.*;
import edu.unam.dgtic.proyecto_final.system.model.TransmisionVehiculo;
import edu.unam.dgtic.proyecto_final.system.model.dto.VehiculoDto;
import edu.unam.dgtic.proyecto_final.system.repository.ReservaRepository;
import edu.unam.dgtic.proyecto_final.system.repository.TiposMantenimientoRepository;
import edu.unam.dgtic.proyecto_final.system.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

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
    TiposMantenimientoRepository tiposMantenimientoRepository;

    @Autowired
    MarcaVehiculoService marcaService;

    @Autowired
    TipoCombustibleVehiculoService combustibleService;

    @Autowired
    TransmisionVehiculoService transmisionService;

    @Autowired
    DisponibilidadVehiculoService disponibilidadService;

    @Autowired
    TipoCarroceriaService carroceriaService;

    @Autowired
    ReservaRepository reservaRepository;

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

    @GetMapping("/maintenance")
    public String mantenimiento(Model model, @RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "8") int size) {
        List<VehiculoDto> todosVehiculos = vehiculoService.findAll();
        List<VehiculoDto> listaMantenimiento = vehiculoService.findAllByDisponibilidad(2L);

        log.info("Vehiculos en mantenimiento: " + listaMantenimiento.size());

        model.addAttribute("vehiculos", todosVehiculos);
        model.addAttribute("vehiculosEnMantenimiento", listaMantenimiento);

        model.addAttribute("marcasVehiculo", marcaService.obtenerTodos());
        model.addAttribute("tiposCombustible", combustibleService.obtenerTodos());
        model.addAttribute("transmisiones", transmisionService.obtenerTodos());
        model.addAttribute("disponibilidades", disponibilidadService.obtenerTodos());
        model.addAttribute("tiposCarroceria", carroceriaService.obtenerTodos());
        model.addAttribute("today", LocalDate.now());

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
        model.addAttribute("marcasVehiculo", marcaService.obtenerTodos());
        model.addAttribute("tiposCombustible", combustibleService.obtenerTodos());
        model.addAttribute("transmisiones", transmisionService.obtenerTodos());
        model.addAttribute("disponibilidades", disponibilidadService.obtenerTodos());
        model.addAttribute("tiposCarroceria", carroceriaService.obtenerTodos());
        model.addAttribute("today", LocalDate.now());

        return "navegacion/car-detail";
    }

    @PostMapping("/guardar-vehiculo")
    public String guardarVehiculo(@ModelAttribute VehiculoDto vehiculoDto,
                                  @RequestParam(value = "marcaId", required = false) Long marcaId,
                                  @RequestParam(value = "tipoCombustibleId", required = false) Long tipoCombustibleId,
                                  @RequestParam(value = "transmisionId", required = false) Long transmisionId,
                                  @RequestParam(value = "disponibilidadId", required = false) Long disponibilidadId,
                                  @RequestParam(value = "tipoCarroceriaId", required = false) Long tipoCarroceriaId,
                                  @RequestParam MultipartFile imagen,
                                  RedirectAttributes redirectAttributes) {

        try {
            // Obtener el vehículo existente
            Vehiculo vehiculo = fromDto(vehiculoDto);

            // Actualizar los campos básicos
            vehiculo.setNumeroPlaca(vehiculoDto.getNumeroPlaca());
            vehiculo.setModelo(vehiculoDto.getModelo());
            vehiculo.setFechaFabricacion(vehiculoDto.getFechaFabricacion());
            vehiculo.setKilometraje(vehiculoDto.getKilometraje());
            vehiculo.setCapacidadPersonas(vehiculoDto.getCapacidadPersonas());
            vehiculo.setPrecioDia(vehiculoDto.getPrecioDia());

            // Procesar la imagen si se proporcionó
            if (imagen != null && !imagen.isEmpty()) {
                byte[] bytes = imagen.getBytes();
                vehiculo.setImagen(bytes);
            }

            // Actualizar relaciones utilizando los IDs de los parámetros
            if (marcaId != null) {
                vehiculo.setMarca(marcaService.findById(marcaId));
            }

            if (tipoCombustibleId != null) {
                vehiculo.setTipoCombustible(combustibleService.findById(tipoCombustibleId));
            }

            if (transmisionId != null) {
                vehiculo.setTransmision(transmisionService.findById(transmisionId));
            }

            if (disponibilidadId != null) {
                vehiculo.setDisponibilidad(disponibilidadService.findById(disponibilidadId));
            }

            if (tipoCarroceriaId != null) {
                vehiculo.setTipoCarroceria(carroceriaService.findById(tipoCarroceriaId));
            }

            vehiculoService.guardar(vehiculo);

            redirectAttributes.addFlashAttribute("mensajeExito",
                    "Vehículo registrado exitosamente");

            return "redirect:/admin/maintenance";
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("mensajeError",
                    "Error al procesar la imagen del vehículo");
            return "redirect:/admin/maintenance";
        }
    }

    @PostMapping("/actualizar-vehiculo/{id}")
    public String actualizarVehiculo(@PathVariable Long id,
                                     @ModelAttribute VehiculoDto vehiculoDto,
                                     @RequestParam(value = "marcaId", required = false) Long marcaId,
                                     @RequestParam(value = "tipoCombustibleId", required = false) Long tipoCombustibleId,
                                     @RequestParam(value = "transmisionId", required = false) Long transmisionId,
                                     @RequestParam(value = "disponibilidadId", required = false) Long disponibilidadId,
                                     @RequestParam(value = "tipoCarroceriaId", required = false) Long tipoCarroceriaId,
                                     @RequestParam(required = false) MultipartFile imagen,
                                     RedirectAttributes redirectAttributes) {

        try {
            // Obtener el vehículo existente
            Vehiculo vehiculoExistente = vehiculoService.obtenerVehiculoEntidad(id)
                    .orElseThrow(() -> new IllegalArgumentException("Vehículo no encontrado"));

            // Actualizar los campos básicos
            vehiculoExistente.setNumeroPlaca(vehiculoDto.getNumeroPlaca());
            vehiculoExistente.setModelo(vehiculoDto.getModelo());
            vehiculoExistente.setFechaFabricacion(vehiculoDto.getFechaFabricacion());
            vehiculoExistente.setKilometraje(vehiculoDto.getKilometraje());
            vehiculoExistente.setCapacidadPersonas(vehiculoDto.getCapacidadPersonas());
            vehiculoExistente.setPrecioDia(vehiculoDto.getPrecioDia());

            // Procesar la imagen si se proporcionó
            if (imagen != null && !imagen.isEmpty()) {
                byte[] bytes = imagen.getBytes();
                vehiculoExistente.setImagen(bytes);
            }

            // Actualizar relaciones utilizando los IDs de los parámetros
            if (marcaId != null) {
                vehiculoExistente.setMarca(marcaService.findById(marcaId));
            }

            if (tipoCombustibleId != null) {
                vehiculoExistente.setTipoCombustible(combustibleService.findById(tipoCombustibleId));
            }

            if (transmisionId != null) {
                vehiculoExistente.setTransmision(transmisionService.findById(transmisionId));
            }

            if (disponibilidadId != null) {
                vehiculoExistente.setDisponibilidad(disponibilidadService.findById(disponibilidadId));
            }

            if (tipoCarroceriaId != null) {
                vehiculoExistente.setTipoCarroceria(carroceriaService.findById(tipoCarroceriaId));
            }

            // Guardar el vehículo actualizado
            vehiculoService.guardar(vehiculoExistente);

            redirectAttributes.addFlashAttribute("mensajeExito", "Vehículo actualizado exitosamente");
            return "redirect:/admin/detalle/" + id;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al actualizar el vehículo: " + e.getMessage());
            return "redirect:/admin/detalle/" + id;
        }
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

    public Vehiculo fromDto(VehiculoDto dto) {

        // Obtener las entidades relacionadas a través de los servicios
        MarcaVehiculo marca = marcaService.obtenerPorDescripcion(dto.getMarca());
        TipoCombustibleVehiculo combustible = combustibleService.obtenerPorDescripcion(dto.getTipoCombustible());
        TransmisionVehiculo transmision = transmisionService.obtenerPorDescripcion(dto.getTransmision());
        DisponibilidadVehiculo disponibilidad = disponibilidadService.obtenerPorDescripcion(dto.getDisponibilidad());
        TipoCarroceriaVehiculo carroceria = carroceriaService.obtenerPorDescripcion(dto.getTipoCarroceria());

        // Convertir imagen Base64 a byte[]
        byte[] imagenBytes = null;
        if (dto.getImagenBase64() != null && !dto.getImagenBase64().isEmpty()) {
            String base64Image = dto.getImagenBase64().split(",")[1];
            imagenBytes = Base64.getDecoder().decode(base64Image);
        }

        // Construir y retornar la entidad Vehiculo
        return Vehiculo.builder()
                .id(dto.getId())
                .numeroPlaca(dto.getNumeroPlaca())
                .marca(marca)
                .modelo(dto.getModelo())
                .fechaFabricacion(dto.getFechaFabricacion())
                .tipoCombustible(combustible)
                .kilometraje(dto.getKilometraje())
                .transmision(transmision)
                .capacidadPersonas(dto.getCapacidadPersonas())
                .disponibilidad(disponibilidad)
                .precioDia(dto.getPrecioDia())
                .imagen(imagenBytes)
                .tipoCarroceria(carroceria)
                .build();
    }
}
