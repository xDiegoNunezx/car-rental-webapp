package edu.unam.dgtic.proyecto_final.security;

import edu.unam.dgtic.proyecto_final.security.jwt.JWTAuthenticationFilter;
import edu.unam.dgtic.proyecto_final.security.jwt.JWTTokenProvider;
import edu.unam.dgtic.proyecto_final.security.logout.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.security.SecureRandom;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private UserDetailsService uds;
    @Autowired
    private JWTTokenProvider tokenProvider;
    @Autowired
    private CustomLogoutSuccessHandler customLogoutSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorize) -> authorize
                    .requestMatchers("/css/**", "/favicon.ico", "/public/**", "/auth/**", "/index", "/bootstrap/**", "/iconos/**", "/tema/**", "/image/**").permitAll()
                    .requestMatchers("/user/**").hasAuthority("USER")
                    .requestMatchers("/admin/**").hasAuthority("ADMIN")
                    .requestMatchers("/api/**").permitAll()
                    .anyRequest().authenticated()
            )
            .formLogin(login -> login
                    .loginPage("/auth/login")
                    .defaultSuccessUrl("/public")
                    .successForwardUrl("/auth/login_success_handler")
                    .failureForwardUrl("/auth/login_failure_handler")
                    .permitAll())
            .logout(logout -> logout
                    .logoutUrl("/doLogout")
                    .logoutSuccessUrl("/public")
                    .deleteCookies("JSESSIONID") //NEW Cookies to clear
                    .logoutSuccessHandler(customLogoutSuccessHandler)
                    .clearAuthentication(true)
                    .invalidateHttpSession(true))
            .addFilterAfter(new JWTAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
            .csrf(AbstractHttpConfigurer::disable)
            .cors(Customizer.withDefaults())
            .sessionManagement(session ->
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11, new SecureRandom());
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(uds);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        //your AuthenticationProvider must return UserDetails object
        return new ProviderManager(authenticationProvider());
    }
}

