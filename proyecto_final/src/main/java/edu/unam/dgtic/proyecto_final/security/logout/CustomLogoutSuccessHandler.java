package edu.unam.dgtic.proyecto_final.security.logout;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class CustomLogoutSuccessHandler implements LogoutSuccessHandler {
    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("Logout Handler");
        Cookie[] cookies = request.getCookies();
        boolean tokenFound = false;

        if (cookies != null) {
            log.info("Total cookies encontradas: {}", cookies.length);

            for (Cookie cookie : cookies) {
                log.info("Cookie encontrada: {} = {}", cookie.getName(), cookie.getValue());

                if (cookie.getName().equals("token")) {
                    tokenFound = true;
                    log.info("Cookie token encontrada, estableciendo maxAge=0");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
            }
        } else {
            log.info("No se encontraron cookies en la solicitud");
        }

        if (tokenFound) {
            log.info("Logout exitoso");
        } else {
            log.info("No se encontró la cookie token");
        }

        response.sendRedirect(request.getContextPath());
    }
}
