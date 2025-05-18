package edu.unam.dgtic.proyecto_final.auth.controller;

import edu.unam.dgtic.proyecto_final.auth.dto.UsuarioDTO;
import edu.unam.dgtic.proyecto_final.auth.exception.UsuarioNotFoundException;
import edu.unam.dgtic.proyecto_final.auth.service.UsuarioService;
import edu.unam.dgtic.proyecto_final.security.jwt.JWTTokenProvider;
import edu.unam.dgtic.proyecto_final.security.request.JwtRequest;
import edu.unam.dgtic.proyecto_final.security.request.LoginUserRequest;
import edu.unam.dgtic.proyecto_final.security.service.UserDetailsServiceImpl;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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
    public String loginFailureHandler() {
        System.out.println("Login failure handler....");
        return "login";
    }

    @GetMapping("/access-denied")
    public String accessDenied() {
        return "access-denied";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UsuarioDTO());
        return "signup_form";
    }

    @PostMapping("/process_register")
    public String processRegister(UsuarioDTO user) throws UsuarioNotFoundException {
        user.setEsAdministrador(false);
        usuarioService.save(user);
        return "register_success";
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

        } catch (UsernameNotFoundException | BadCredentialsException e) {
            session.setAttribute("msg","Bad Credentials");
            return "redirect:/auth/login";
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
