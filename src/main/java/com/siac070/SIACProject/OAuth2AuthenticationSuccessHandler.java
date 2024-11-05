package com.siac070.SIACProject;

import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.siac070.SIACProject.util.JwtUtil;  // Asegúrate de usar tu paquete correcto para JwtUtil

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtUtil jwtUtil;  // Inyección de JwtUtil para generar el token JWT

    public OAuth2AuthenticationSuccessHandler(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        if (authentication.getPrincipal() instanceof DefaultOidcUser) {
            DefaultOidcUser oidcUser = (DefaultOidcUser) authentication.getPrincipal();

            // Extrae los detalles del usuario autenticado
            String email = oidcUser.getAttribute("email");
            String name = oidcUser.getAttribute("name");
            String picture = oidcUser.getAttribute("picture");

            // Genera el token JWT usando el email como clave
            String jwtToken = jwtUtil.generateToken(email);

            // Prepara la respuesta con el JWT y los detalles del usuario
            Map<String, Object> userDetails = Map.of(
                "message", "Login exitoso con Google",
                "name", name,
                "email", email,
                "picture", picture,
                "token", jwtToken  // Adjunta el token JWT en la respuesta
            );

            // Escribir la respuesta JSON al cliente
            response.setContentType("application/json");
            new ObjectMapper().writeValue(response.getOutputStream(), userDetails);
        } else {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuario no autenticado mediante OIDC");
        }
    }
}
