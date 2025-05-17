package edu.unam.dgtic.proyecto_final.system.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping("")
    public String admin() {
        return "navegacion/admin";
    }

    @GetMapping("/reservations")
    public String reservas() {
        return "navegacion/reservations";
    }

    @GetMapping("/metrics")
    public String estadisticas() {
        return "navegacion/metrics";
    }

    @GetMapping("/maintenance")
    public String mantenimiento() {
        return "navegacion/maintenance";
    }
}
