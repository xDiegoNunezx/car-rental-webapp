package edu.unam.dgtic.proyecto_final.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/")
public class NavigationController {
    @GetMapping("/")
    public String home() {
        return "navegacion/home";
    }

    @GetMapping("index")
    public String index() {
        return "redirect:/";
    }

    @GetMapping("about-us")
    public String aboutUs() {
        return "navegacion/about-us";
    }

    @GetMapping("contact-us")
    public String contactUs() {
        return "navegacion/contact-us";
    }

    @GetMapping("faq")
    public String faq() {
        return "navegacion/faq";
    }

    @GetMapping("admin")
    public String admin() {
        return "navegacion/admin";
    }

    @GetMapping("admin/maintenance")
    public String mantenimiento() {
        return "navegacion/maintenance";
    }

    @GetMapping("admin/reservations")
    public String reservas() {
        return "navegacion/reservations";
    }

    @GetMapping("admin/metrics")
    public String estadisticas() {
        return "navegacion/metrics";
    }

    @GetMapping("login")
    public String login() {
        return "login";
    }

    @PostMapping("login_success_handler")
    public String loginSuccessHandler() {
        System.out.println("Logging user login success...");
        return "navegacion/home";
    }

    @PostMapping("login_failure_handler")
    public String loginFailureHandler() {
        System.out.println("Login failure handler....");
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }
}
