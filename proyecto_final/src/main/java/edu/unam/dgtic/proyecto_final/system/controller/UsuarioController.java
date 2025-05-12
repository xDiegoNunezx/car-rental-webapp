package edu.unam.dgtic.proyecto_final.system.controller;

import edu.unam.dgtic.proyecto_final.system.model.Reserva;
import edu.unam.dgtic.proyecto_final.system.model.Usuario;
import edu.unam.dgtic.proyecto_final.system.service.ReservaService;
import edu.unam.dgtic.proyecto_final.system.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/")
public class UsuarioController {
    @Autowired
    UsuarioService usuarioService;
    @Autowired
    ReservaService reservaService;

    @GetMapping("profile")
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

    /*
    @GetMapping("/loginn")
    public String loginForm() {
        return "login"; // templates/login.html
    }

    @PostMapping("/loginn")
    public String loginUsuario(@RequestParam String email,
                               @RequestParam String contrasena,
                               RedirectAttributes redirectAttributes,
                               Model model) {
        Optional<Usuario> usuario = usuarioService.obtenerPorEmail(email);

        if (usuario.isPresent() && usuario.get().getContrasena().equals(contrasena)) {
            // guardar en sesión si es necesario
            return "redirect:/vehiculos/disponibles";
        } else {
            model.addAttribute("error", "Credenciales incorrectas");
            return "login";
        }
    }*/
}
