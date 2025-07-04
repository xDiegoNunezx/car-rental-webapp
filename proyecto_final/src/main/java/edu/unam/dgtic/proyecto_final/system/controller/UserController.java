package edu.unam.dgtic.proyecto_final.system.controller;

import edu.unam.dgtic.proyecto_final.security.model.UserDetailsImpl;
import edu.unam.dgtic.proyecto_final.system.model.Cliente;
import edu.unam.dgtic.proyecto_final.system.model.Reserva;
import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import edu.unam.dgtic.proyecto_final.system.model.dto.VehiculoDto;
import edu.unam.dgtic.proyecto_final.system.repository.VehiculoRepository;
import edu.unam.dgtic.proyecto_final.system.service.ClienteService;
import edu.unam.dgtic.proyecto_final.system.service.ReservaService;
import edu.unam.dgtic.proyecto_final.system.service.VehiculoService;
import edu.unam.dgtic.proyecto_final.system.util.PdfGenerator;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    ClienteService clienteService;
    @Autowired
    ReservaService reservaService;
    @Autowired
    VehiculoService vehiculoService;

    @GetMapping("/profile")
    public String profile(@RequestParam(name = "page", defaultValue = "0") int page,
                          Model modelo,
                          SessionStatus status,
                          Authentication authentication
    ) {
        Optional<Cliente> clienteOpt = clienteService.obtenerPorEmail(authentication.getName());

        if (!clienteOpt.isPresent()) {
            modelo.addAttribute("error", "No se encontró información del cliente");
            return "/public";
        }

        Cliente cliente = clienteOpt.get();
        Long usuarioId = cliente.getId();

        List<Reserva> reservas = reservaService.obtenerReservasDeCliente(usuarioId);
        modelo.addAttribute("usuario", cliente);
        modelo.addAttribute("reservas", reservas);

        return "navegacion/profile";
    }

    @GetMapping("/perfil/{usuarioId}")
    public String verPerfil(@PathVariable Long usuarioId, Model model) {
        Optional<Cliente> cliente = clienteService.obtenerPorId(usuarioId);
        cliente.ifPresent(c -> model.addAttribute("cliente", c));
        return "perfil_usuario"; // templates/perfil_usuario.html
    }

    @PostMapping("/reserva/cancelar/{reservaId}")
    public String cancelarReserva(@PathVariable Long reservaId, RedirectAttributes redirectAttributes) {
        reservaService.cancelarReserva(reservaId);
        redirectAttributes.addFlashAttribute("mensaje", "Reserva cancelada con éxito");
        return "redirect:/user/profile";
    }

    @GetMapping("/reserva/book-now")
    public String mostrarFormularioReserva(@RequestParam("idVehiculo") Long idVehiculo,
                                           @RequestParam("fechaInicio") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaInicio,
                                           @RequestParam("fechaFin") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fechaFin,
                                           Model model) {

        Vehiculo vehiculo = vehiculoService.obtenerVehiculoEntidad(idVehiculo).get();
        Reserva reserva = new Reserva();
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setVehiculo(vehiculo);

        model.addAttribute("reserva", reserva);
        model.addAttribute("automovil", vehiculo.toDto());
        return "navegacion/book-now";
    }

    @PostMapping("/reserva/recibir-reserva")
    public String recibirReserva(
            Reserva reserva,
            BindingResult bindingResult,
            Model model,
            Authentication authentication
    ) {
        Vehiculo vehiculo = vehiculoService.obtenerVehiculoEntidad(reserva.getVehiculo().getId()).get();

        model.addAttribute("automovil", vehiculo.toDto());
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "navegacion/book-now";
        }

        // Guardar reserva
        Optional<Cliente> clienteOpt = clienteService.obtenerPorEmail(authentication.getName());
        if (!clienteOpt.isPresent()) {
            model.addAttribute("error", "No se encontró información del cliente");
            return "/user/profile";
        }

        Cliente cliente = clienteOpt.get();
        Long usuarioId = cliente.getId();

        String cadena = "Reserva Registrada: " + reserva;
        try {
            Reserva nuevaReserva = reservaService.crearReserva(
                    usuarioId,
                    reserva.getVehiculo().getId(),
                    reserva.getFechaInicio(),
                    reserva.getFechaFin(),
                    reserva.isAsientoInfantil(),
                    reserva.isAsientoElevador(),
                    reserva.isConductoresAdicionales()
            );
            model.addAttribute("reserva", nuevaReserva);
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            bindingResult.rejectValue("id","id","Registro duplicado");
            return "navegacion/book-now";
        }

        return "redirect:/user/profile";
    }

    @GetMapping("/reserva/pdf/{reservaId}")
    public void generarPdfReserva(@PathVariable Long reservaId, HttpServletResponse response) throws IOException {
        Reserva reserva = reservaService.obtenerReservaPorId(reservaId);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=reserva_" + reservaId + ".pdf");

        PdfGenerator pdfGenerator = new PdfGenerator();
        ByteArrayOutputStream baos = pdfGenerator.generarPdfReserva(reserva);
        response.getOutputStream().write(baos.toByteArray());
        response.getOutputStream().flush();
    }
}
