package edu.unam.dgtic.proyecto_final.system.controller;

import edu.unam.dgtic.proyecto_final.system.model.Reserva;
import edu.unam.dgtic.proyecto_final.system.model.Usuario;
import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import edu.unam.dgtic.proyecto_final.system.model.dto.VehiculoDto;
import edu.unam.dgtic.proyecto_final.system.service.ReservaService;
import edu.unam.dgtic.proyecto_final.system.service.UsuarioService;
import edu.unam.dgtic.proyecto_final.system.service.VehiculoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ReservaService reservaService;
    @Autowired
    VehiculoService vehiculoService;

    @GetMapping("/profile")
    public String profile(@RequestParam(name = "page", defaultValue = "0") int page,
                          Model modelo,
                          SessionStatus status) {
        List<Reserva> reservas = reservaService.obtenerReservasDeUsuario(1L);
        modelo.addAttribute("reservas", reservas);
        return "navegacion/profile";
    }


    @GetMapping("/perfil/{usuarioId}")
    public String verPerfil(@PathVariable Long usuarioId, Model model) {
        Optional<Usuario> usuario = usuarioService.obtenerPorId(usuarioId);
        usuario.ifPresent(u -> model.addAttribute("usuario", u));
        return "perfil_usuario"; // templates/perfil_usuario.html
    }

    @PostMapping("/reserva/cancelar/{reservaId}")
    public String cancelarReserva(@PathVariable Long reservaId, RedirectAttributes redirectAttributes) {
        reservaService.cancelarReserva(reservaId);
        redirectAttributes.addFlashAttribute("mensaje", "Reserva cancelada con éxito");
        return "redirect:/user/profile"; // o redirige a la lista de reservas
    }

    @GetMapping("/reserva/book-now")
    public String mostrarFormularioReserva(@RequestParam("idVehiculo") Long idVehiculo,
                                           @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
                                           @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin,
                                           Model model) {

        Vehiculo vehiculo = vehiculoService.obtenerPorId(idVehiculo).get();
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setVehiculo(vehiculo);

        model.addAttribute("reserva", reserva);
        model.addAttribute("automovil", toDto(vehiculo));
        return "navegacion/book-now";
    }

    @PostMapping("/reserva/recibir-reserva")
    public String recibirReserva(
            Reserva reserva,
            BindingResult bindingResult,
            Model model
    ) {
        Vehiculo vehiculo = vehiculoService.obtenerPorId(reserva.getVehiculo().getVehiculo_id()).get();
        model.addAttribute("automovil", toDto(vehiculo));
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "navegacion/book-now";
        }

        // Guardar reserva
        String cadena = "Reserva Registrada: " + reserva;
        try {
            Reserva nuevaReserva = reservaService.crearReserva(
                    1L,
                    reserva.getVehiculo().getVehiculo_id(),
                    reserva.getFechaInicio(),
                    reserva.getFechaFin(),
                    reserva.isAsientoInfantil(),
                    reserva.isAsientoElevador(),
                    reserva.isConductoresAdicionales()
            );
            model.addAttribute("reserva", nuevaReserva);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            bindingResult.rejectValue("reservaId","reservaId","Registro duplicado");
            return "navegacion/book-now";
        }

        return "redirect:/user/profile";
    }

    public VehiculoDto toDto(Vehiculo vehiculo) {
        VehiculoDto dto = new VehiculoDto();

        dto.setVehiculoId(vehiculo.getVehiculo_id());
        dto.setNumeroPlaca(vehiculo.getNumeroPlaca());
        dto.setModelo(vehiculo.getModelo());
        dto.setFechaFabricacion(vehiculo.getFechaFabricacion());
        dto.setKilometraje(vehiculo.getKilometraje());
        dto.setCapacidadPersonas(vehiculo.getCapacidadPersonas());
        dto.setPrecioDia(vehiculo.getPrecioDia());
        dto.setMarca(vehiculo.getMarca());
        dto.setTipoCombustible(vehiculo.getTipoCombustible());
        dto.setTransmision(vehiculo.getTransmision());
        dto.setCarroceria(vehiculo.getCarroceria());
        dto.setDisponible(vehiculo.getDisponible());

        // Procesar la imagen en base64 si está disponible
        if (vehiculo.getImagen() != null && vehiculo.getImagen().length > 0) {
            String tipoImagen = "image/jpeg"; // Valor por defecto
            try (InputStream is = new ByteArrayInputStream(vehiculo.getImagen())) {
                String detected = URLConnection.guessContentTypeFromStream(is);
                if (detected != null) {
                    tipoImagen = detected;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            dto.setImagenBase64("data:" + tipoImagen + ";base64," + Base64.getEncoder().encodeToString(vehiculo.getImagen()));
        }

        return dto;
    }
}
