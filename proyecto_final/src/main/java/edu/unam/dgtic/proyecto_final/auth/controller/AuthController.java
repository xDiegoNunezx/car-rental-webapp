package edu.unam.dgtic.proyecto_final.auth.controller;

import edu.unam.dgtic.proyecto_final.auth.dto.UsuarioDTO;
import edu.unam.dgtic.proyecto_final.auth.exception.UsuarioNotFoundException;
import edu.unam.dgtic.proyecto_final.auth.model.Usuario;
import edu.unam.dgtic.proyecto_final.auth.service.UsuarioService;
import edu.unam.dgtic.proyecto_final.security.jwt.JWTTokenProvider;
import edu.unam.dgtic.proyecto_final.security.request.JwtRequest;
import edu.unam.dgtic.proyecto_final.security.request.LoginUserRequest;
import edu.unam.dgtic.proyecto_final.security.service.UserDetailsServiceImpl;
import edu.unam.dgtic.proyecto_final.system.model.Cliente;
import edu.unam.dgtic.proyecto_final.system.service.ClienteService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login_success_handler")
    public String loginSuccessHandler() {
        System.out.println("Logging user login success...");
        return "navegacion/home";
    }

    @PostMapping("/login_failure_handler")
    public String loginFailureHandler(Model model) {
        System.out.println("Login failure handler....");
        model.addAttribute("error", true);
        model.addAttribute("errorMsg", "Email o contraseña incorrectos");
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/registro-usuario")
    public String registroUsuario(Model model) {
        model.addAttribute("usuario", new Cliente());
        return "navegacion/registro-usuario";
    }

    @PostMapping("/recibir-registro-usuario")
    public String recibirRegistroUsuario(
            @Valid @ModelAttribute("usuario") Cliente cliente,
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
            cliente.setEsAdministrador(false);
            clienteService.guardar(cliente);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            bindingResult.addError(new ObjectError("usuario", "Error al registrar usuario: " + e.getMessage()));
            return "navegacion/registro-usuario";
        }

        String cadena = "Usuario Registrado: " + cliente;
        model.addAttribute("usuario", new Cliente());
        model.addAttribute("info", cadena);
        return "redirect:/auth/login?loginSuccessful=true";
    }

    @PostMapping("/token")
    public String createAuthenticationToken(Model model, HttpSession session,
                                            @ModelAttribute LoginUserRequest loginUserRequest, HttpServletResponse res) throws Exception {
        log.info("LoginUserRequest {}", loginUserRequest);
        try {
            UsuarioDTO user = usuarioService.findByEmail(loginUserRequest.getUsername());

            Authentication authentication = authenticate(loginUserRequest.getUsername(),
                    loginUserRequest.getPassword());
            log.info("authentication {}", authentication);

            String jwtToken = jwtTokenProvider.generateJwtToken(authentication, user);
            log.info("jwtToken {}", jwtToken);

            JwtRequest jwtRequest = new JwtRequest(jwtToken, user.getId(), user.getEmail(),
                    jwtTokenProvider.getExpiryDuration(), authentication.getAuthorities());
            log.info("jwtRequest {}", jwtRequest);

            Cookie cookie = new Cookie("token",jwtToken);
            cookie.setMaxAge((int) jwtTokenProvider.getExpiryDuration());
            cookie.setPath("/");
            res.addCookie(cookie);
            session.setAttribute("msg","Login OK!");

        } catch (UsuarioNotFoundException | BadCredentialsException e) {
            session.setAttribute("msg","Bad Credentials");
            return "redirect:/auth/login?error=true";
        }
        return "redirect:/public";
    }

    private Authentication authenticate(String username, String password) throws Exception {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}
