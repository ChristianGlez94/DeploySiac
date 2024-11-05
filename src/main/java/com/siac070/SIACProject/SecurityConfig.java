package com.siac070.SIACProject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import com.siac070.SIACProject.filter.JwtRequestFilter;
import com.siac070.SIACProject.service.CustomOAuth2UserService;
import jakarta.servlet.http.HttpServletResponse;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private JwtRequestFilter jwtRequestFilter;  // Filtro para JWT

    @Autowired
    private OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;  // Manejador de éxito para OAuth2

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf().disable()  // Desactiva CSRF ya que solo trabajas en backend
                .authorizeHttpRequests(request -> {
                    // Rutas públicas que no requieren autenticación
                    request.requestMatchers("/", "/swagger-ui/**", "/v2/api-docs", "/swagger-resources/**", "/webjars/**").permitAll();
                    // Permitir POST a /api/usuarios/register, /login y /api/usuarios/login
                    request.requestMatchers("/api/usuarios/register", "/login", "/api/usuarios/login", "/catalogo_reportes", "/api/reportes","api/reportes/crear_reporte").permitAll();
                    // Excluir rutas de OAuth2 del requerimiento de JWT
                    request.requestMatchers("/oauth2/**").permitAll();
                    // Cualquier otra solicitud requiere autenticación
                    //request.anyRequest().authenticated();
                    request.anyRequest().permitAll();
                })
                .oauth2Login(oauth2 -> oauth2
                        .userInfoEndpoint(userInfo -> userInfo
                                .userService(oAuth2UserService())  // Carga detalles del usuario de Google
                        )
                        .successHandler(oAuth2AuthenticationSuccessHandler)  // Manejador de éxito para devolver el JWT
                )
                // Filtro JWT para autenticación estándar
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    // Configura el servicio para cargar usuarios mediante OAuth2 (Google)
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oAuth2UserService() {
        return new CustomOAuth2UserService();
    }

    // Bean para el PasswordEncoder (en este caso BCrypt)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Para manejar fallos en la autenticación, si lo deseas puedes configurarlo
    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return (request, response, exception) -> {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("{\"error\": \"" + exception.getMessage() + "\"}");
        };
    }

    // Para permitir la inyección de AuthenticationManager
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
