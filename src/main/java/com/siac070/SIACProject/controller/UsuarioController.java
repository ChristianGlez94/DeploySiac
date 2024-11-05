package com.siac070.SIACProject.controller;

import com.siac070.SIACProject.dto.LoginResponse;
import com.siac070.SIACProject.dto.UsuarioDTO;
import com.siac070.SIACProject.model.TrabajadorDependencia;
import com.siac070.SIACProject.model.Usuario;
import com.siac070.SIACProject.service.TrabajadorDependenciaService;
import com.siac070.SIACProject.service.UsuarioService;
import com.siac070.SIACProject.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TrabajadorDependenciaService trabajadorDependenciaService;

    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> registerUsuario(@RequestBody Usuario usuario) {
        Map<String, String> response = new HashMap<>();

        try {
            // Verificar si el correo ya está en uso
            if (usuarioService.findByCorreo(usuario.getCorreo()).isPresent()) {
                response.put("message", "El correo ya está en uso.");
                System.out.println("Correo ya registrado: " + usuario.getCorreo());
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

            // Guardar el usuario (encriptación de la contraseña ya está en el servicio)
            usuarioService.saveUsuario(usuario);

            response.put("message", "Usuario registrado exitosamente");
            System.out.println("Usuario registrado exitosamente: " + usuario.getCorreo());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("Error general al registrar usuario: " + e.getMessage());
            response.put("message", "Error al registrar usuario");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Map<String, String> loginData) {
        String correo = loginData.get("correo");
        String contrasena = loginData.get("contrasena");

        Optional<Usuario> usuarioOptional = usuarioService.findByCorreo(correo);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            // Verificar la contraseña usando passwordEncoder.matches()
            boolean isPasswordValid = passwordEncoder.matches(contrasena, usuario.getContrasena());
            if (isPasswordValid) {
                // Obtener detalles del usuario
                UserDetails userDetails = usuarioService.loadUserByUsername(correo);

                // Generar JWT
                String token = jwtUtil.generateToken(userDetails);

                // Buscar la dependencia del usuario
                Optional<TrabajadorDependencia> trabajadorDependenciaOpt = trabajadorDependenciaService.findByUsuarioId(usuario.getId());

                Long dependenciaId = null;
                String dependenciaNombre = null;
                if (trabajadorDependenciaOpt.isPresent()) {
                    TrabajadorDependencia trabajadorDependencia = trabajadorDependenciaOpt.get();
                    dependenciaId = Long.valueOf(trabajadorDependencia.getDependencia().getId());
                    dependenciaNombre = trabajadorDependencia.getDependencia().getNombre();
                }

                // Crear el objeto de respuesta incluyendo la dependencia
                LoginResponse response = new LoginResponse(
                        usuario.getId(),
                        usuario.getNombre(),
                        usuario.getApellidoPaterno(),
                        usuario.getApellidoMaterno(),
                        usuario.getCorreo(),
                        usuario.getTelefono(),
                        usuario.getCatUsuario(),
                        usuario.getCatEstatusUsuario(),
                        token,
                        "Login exitoso",
                        dependenciaId,
                        dependenciaNombre
                );

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
            }
        } else {
            return new ResponseEntity<>(null, HttpStatus.UNAUTHORIZED);
        }
    }


    @GetMapping("/oauth2/details")
    public Map<String, Object> getUserDetails(@AuthenticationPrincipal OAuth2User oAuth2User) {
        // Extraer los detalles del usuario autenticado mediante OAuth2 (Google)
        Map<String, Object> userDetails = Map.of(
                "message", "Login exitoso con Google",
                "name", oAuth2User.getAttribute("name"),
                "email", oAuth2User.getAttribute("email"),
                "picture", oAuth2User.getAttribute("picture"));

        return userDetails;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsuarioDTO>> getAllUsuarios() {
        try {
            // Obtén todos los usuarios
            List<Usuario> usuarios = usuarioService.getAllUsuarios();

            // Convierte los usuarios en una lista de UsuarioDTO
            List<UsuarioDTO> usuariosDTO = usuarios.stream()
                    .map(usuario -> new UsuarioDTO(
                            usuario.getId(),
                            usuario.getNombre(),
                            usuario.getApellidoPaterno(),
                            usuario.getApellidoMaterno(),
                            usuario.getCorreo(),
                            usuario.getTelefono(),
                            usuario.getCatUsuario(),
                            usuario.getCatEstatusUsuario()))
                    .collect(Collectors.toList());

            // Devuelve la lista de UsuarioDTO
            return new ResponseEntity<>(usuariosDTO, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/actualizarContrasena")
    public ResponseEntity<Object> actualizarContrasena(@RequestBody Usuario usuarioDetalles) {
        Map<String, Object> response = new HashMap<>();
        try {
            String correo = usuarioDetalles.getCorreo();
            Optional<Usuario> usuarioOptional = usuarioService.findByCorreo(correo);

            if (usuarioOptional.isPresent()) {
                Usuario usuario = usuarioOptional.get();

                String contrasena = usuarioDetalles.getContrasena();
                if (contrasena == null || contrasena.isEmpty()) {
                    response.put("message", "La contraseña no puede estar vacía.");
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }

                usuario.setContrasena(contrasena);
                usuarioService.recuperarContrasenaUsuario(usuario);
                response.put("message", "Contraseña actualizada con éxito.");
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                response.put("message", "Usuario no encontrado.");
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.put("message", "Ocurrio un error");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @PostMapping("/usuarioCorreo")
    public ResponseEntity<Object> buscarUsuarioCorreo(@RequestBody Map<String, String> usuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            String correo = usuario.get("correo").strip();
            Optional<Usuario> usuarioOptional = usuarioService.findByCorreo(correo);
 
            if (usuarioOptional.isPresent()) {
                response.put("message", "Usuario encontrado.");
                response.put("usuario", usuarioOptional.get().getCorreo());
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Usuario no encontrado.");
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.put("message", "Ocurrio un error");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/usuarioId")
    public ResponseEntity<Object> buscarUsuarioId(@RequestBody Map<String, Long> usuario) {
        Map<String, Object> response = new HashMap<>();
        try {
            Long id = usuario.get("id");
            Optional<Usuario> usuarioOptional = usuarioService.getUsuarioById(id);
 
            if (usuarioOptional.isPresent()) {
                response.put("message", "Usuario encontrado.");
                response.put("usuario", usuarioOptional.get().getCorreo());
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Usuario no encontrado.");
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            response.put("message", "Ocurrio un error");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
