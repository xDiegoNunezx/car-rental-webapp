package edu.unam.dgtic.proyecto_final.system.controller;

import edu.unam.dgtic.proyecto_final.system.converter.MayusculasConverter;
import edu.unam.dgtic.proyecto_final.system.model.Usuario;
import edu.unam.dgtic.proyecto_final.system.model.Vehiculo;
import edu.unam.dgtic.proyecto_final.system.model.dto.ReservaDate;
import edu.unam.dgtic.proyecto_final.system.model.dto.VehiculoDto;
import edu.unam.dgtic.proyecto_final.system.service.ReservaService;
import edu.unam.dgtic.proyecto_final.system.service.UsuarioService;
import edu.unam.dgtic.proyecto_final.system.service.VehiculoService;
import edu.unam.dgtic.proyecto_final.system.util.RenderPagina;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/public")
@SessionAttributes("fecha-busqueda")
public class PublicController {
    @Autowired
    UsuarioService usuarioService;

    @Autowired
    ReservaService reservaService;

    @Autowired
    MessageSource mensaje;

    @Autowired
    VehiculoService vehiculoService;

    @InitBinder("usuario")
    public void convertidor(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, "nombreCompleto", new MayusculasConverter());
    }

    @GetMapping("")
    public String home() {
        return "navegacion/home";
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/public";
    }

    @GetMapping("/about-us")
    public String aboutUs() {
        return "navegacion/about-us";
    }

    @GetMapping("/contact-us")
    public String contactUs() {
        return "navegacion/contact-us";
    }

    @GetMapping("/faq")
    public String faq() {
        return "navegacion/faq";
    }

    @GetMapping("/registro-usuario")
    public String registroUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "navegacion/registro-usuario";
    }

    @PostMapping("/recibir-registro-usuario")
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

    @GetMapping("/cars")
    public String cars(@RequestParam(name = "page", defaultValue = "0") int page,
                       Model modelo,
                       SessionStatus status) {
        String texto = "Rent Cars";

        Pageable pageable = PageRequest.of(page, 4);
        Page<Vehiculo> vehiculoPage = vehiculoService.obtenerDisponibles(pageable);

        // Convertimos a DTO con imagen en Base64
        Page<VehiculoDto> dtoPage = toDto(pageable, vehiculoPage);

        RenderPagina<VehiculoDto> renderPagina = new RenderPagina<>("cars", dtoPage);
        modelo.addAttribute("vehiculos", dtoPage);
        modelo.addAttribute("page", renderPagina);
        modelo.addAttribute("contenido", texto);

        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);
        modelo.addAttribute("fechauno", hoy);
        modelo.addAttribute("fechados", manana);

        status.setComplete();
        return "navegacion/cars";
    }

    @PostMapping("/rent/recibir-fechas")
    public String buscarVehiculosPorFechas(@RequestParam(value = "fechauno") String fechaUno,
                                           @RequestParam(value = "fechados") String fechaDos,
                                           Model modelo) {
        ReservaDate reservaDate = ReservaDate.builder()
                .fechaInicio(LocalDate.parse(fechaUno))
                .fechaFin(LocalDate.parse(fechaDos))
                .build();
        modelo.addAttribute("fecha-busqueda",reservaDate);
        modelo.addAttribute("contenido","Lista de Autos");
        return "redirect:/public/rent/rent-search";
    }

    @GetMapping("/rent/rent-search")
    public String mostrarFormularioBusqueda(@RequestParam(name = "page", defaultValue = "0") int page,
                                            Model modelo, HttpSession session) {
        ReservaDate reservaDate=(ReservaDate) session.getAttribute("fecha-busqueda");

        Pageable pageable= PageRequest.of(page,3);
        Page<Vehiculo> vehiculoPage=vehiculoService.buscarDisponiblesEntreFechas(reservaDate.getFechaInicio(),
                reservaDate.getFechaFin(),pageable);

        Page<VehiculoDto> dtoPage = toDto(pageable, vehiculoPage);

        RenderPagina<Vehiculo> renderPagina=new RenderPagina<>("rent-search",vehiculoPage);

        modelo.addAttribute("contenido","Lista de Autos");
        modelo.addAttribute("fechauno",reservaDate.getFechaInicio());
        modelo.addAttribute("fechados",reservaDate.getFechaFin());
        modelo.addAttribute("vehiculos", dtoPage);
        modelo.addAttribute("page",renderPagina);

        return "navegacion/rent";
    }

    @GetMapping("/rent")
    public String mostrarFormularioBusqueda(@RequestParam(name = "page", defaultValue = "0") int page,
                                            Model modelo,
                                            SessionStatus status) {
        Pageable pageable = PageRequest.of(page, 3);
        Page<Vehiculo> vehiculoPage = vehiculoService.obtenerDisponibles(pageable);

        // Convertimos a DTO con imagen en Base64
        Page<VehiculoDto> dtoPage = toDto(pageable, vehiculoPage);

        RenderPagina<VehiculoDto> renderPagina = new RenderPagina<>("rent", dtoPage);

        modelo.addAttribute("contenido","Lista de Autos");
        modelo.addAttribute("vehiculos", dtoPage);
        modelo.addAttribute("page", renderPagina);

        LocalDate hoy = LocalDate.now();
        LocalDate manana = hoy.plusDays(1);
        modelo.addAttribute("fechauno", hoy);
        modelo.addAttribute("fechados", manana);

        status.setComplete();
        return "navegacion/rent"; // templates/buscar_vehiculos.html
    }

    private Page<VehiculoDto> toDto(Pageable pageable, Page<Vehiculo> vehiculoPage) {
        List<VehiculoDto> vehiculoDtos = vehiculoPage.getContent().stream().map(v -> {
            VehiculoDto dto = new VehiculoDto();
            dto.setVehiculoId(v.getVehiculo_id());
            dto.setNumeroPlaca(v.getNumeroPlaca());
            dto.setModelo(v.getModelo());
            dto.setFechaFabricacion(v.getFechaFabricacion());
            dto.setKilometraje(v.getKilometraje());
            dto.setCapacidadPersonas(v.getCapacidadPersonas());
            dto.setPrecioDia(v.getPrecioDia());
            dto.setMarca(v.getMarca());
            dto.setTipoCombustible(v.getTipoCombustible());
            dto.setTransmision(v.getTransmision());
            dto.setCarroceria(v.getCarroceria());
            dto.setDisponible(v.getDisponible());

            if (v.getImagen() != null && v.getImagen().length > 0) {
                String tipoImagen = "image/jpeg"; // valor por defecto
                try (InputStream is = new ByteArrayInputStream(v.getImagen())) {
                    String detected = URLConnection.guessContentTypeFromStream(is);
                    if (detected != null) {
                        tipoImagen = detected;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                dto.setImagenBase64("data:" + tipoImagen + ";base64," + Base64.getEncoder().encodeToString(v.getImagen()));
            }
            return dto;
        }).toList();

        return new PageImpl<>(vehiculoDtos, pageable, vehiculoPage.getTotalElements());
    }
}
