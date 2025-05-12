package edu.unam.dgtic.proyecto_final.system.controller;

import edu.unam.dgtic.proyecto_final.system.converter.MayusculasConverter;
import edu.unam.dgtic.proyecto_final.system.model.Reserva;
import edu.unam.dgtic.proyecto_final.system.model.Usuario;
import edu.unam.dgtic.proyecto_final.system.service.ReservaService;
import edu.unam.dgtic.proyecto_final.system.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Locale;

@Controller
@RequestMapping(value="/")
public class FormController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ReservaService reservaService;

    @Autowired
    MessageSource mensaje;

    @InitBinder("usuario")
    public void convertidor(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, "nombreCompleto", new MayusculasConverter());
    }

    @GetMapping("registro-usuario")
    public String registroUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "navegacion/registro-usuario";
    }

    @PostMapping("recibir-registro-usuario")
    public String recibirRegistroUsuario(
            @Valid Usuario usuario,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            for (ObjectError error : bindingResult.getAllErrors()) {
                System.out.println(error.getDefaultMessage());
            }
            return "navegacion/registro-usuario";
        }
        try {
            usuarioService.guardar(usuario);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "navegacion/registro-usuario";
        }

        String cadena = "Usuario Registrado: " + usuario;
        model.addAttribute("usuario", new Usuario());
        model.addAttribute("info", cadena);
        return "navegacion/registro-usuario";
    }
}
