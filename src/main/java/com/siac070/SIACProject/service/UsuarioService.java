package com.siac070.SIACProject.service;

import com.siac070.SIACProject.model.Usuario;
import com.siac070.SIACProject.model.CatUsuario;
import com.siac070.SIACProject.model.CatEstatusUsuario;
import com.siac070.SIACProject.repository.UsuarioRepository;
import com.siac070.SIACProject.repository.CatUsuarioRepository;
import com.siac070.SIACProject.repository.CatEstatusUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CatUsuarioRepository catUsuarioRepository;

    @Autowired
    private CatEstatusUsuarioRepository catEstatusUsuarioRepository;

    @Autowired
    private PasswordService passwordService; // Usar el nuevo PasswordService

    // Método para guardar un nuevo usuario
    public Usuario saveUsuario(Usuario usuario) {
        Optional<Usuario> existingUsuario = usuarioRepository.findByCorreo(usuario.getCorreo());
        if (existingUsuario.isPresent()) {
            throw new IllegalArgumentException("El correo ya está en uso."); // Lanzar una excepción
        }

        // Encriptar la contraseña antes de guardarla usando PasswordService
        if (usuario.getContrasena() != null) {
            usuario.setContrasena(passwordService.encodePassword(usuario.getContrasena()));
        } else {
            throw new IllegalArgumentException("La contraseña no puede ser nula");
        }

        // Asignar catUsuario y catEstatusUsuario por defecto
        usuario.setCatUsuario(getDefaultCatUsuario());
        usuario.setCatEstatusUsuario(getDefaultCatEstatusUsuario());
        usuario.setApellidoMaterno(usuario.getApellidoMaterno().strip());
        usuario.setApellidoPaterno(usuario.getApellidoPaterno().strip());
        usuario.setNombre(usuario.getNombre().strip());
        usuario.setCorreo(usuario.getCorreo().strip());

        return usuarioRepository.save(usuario);
    }

    // Método para verificar si la contraseña es correcta
    public boolean checkPassword(Usuario usuario, String rawPassword) {
        return passwordService.matches(rawPassword, usuario.getContrasena());
    }

    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    // Método para obtener todos los usuarios
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Método para obtener un usuario por id
    public Optional<Usuario> getUsuarioById(Long id) {
        return usuarioRepository.findById(id);
    }

    // Método para eliminar un usuario por id
    public void deleteUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }

    // Obtener el valor por defecto de CatUsuario (ID 1)
    public CatUsuario getDefaultCatUsuario() {
        return catUsuarioRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Categoría de Usuario por defecto no encontrada"));
    }

    // Obtener el valor por defecto de CatEstatusUsuario (ID 1)
    public CatEstatusUsuario getDefaultCatEstatusUsuario() {
        return catEstatusUsuarioRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Estatus de Usuario por defecto no encontrado"));
    }

    // Implementación del método de UserDetailsService para cargar usuarios por
    // nombre de usuario
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con el correo: " + correo));

        return new org.springframework.security.core.userdetails.User(usuario.getCorreo(), usuario.getContrasena(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
    }

    public String obtenerNombreDesdeBaseDeDatosPorCorreo(String correo) {
        Optional<Usuario> usuario = usuarioRepository.findByCorreo(correo);

        return usuario.map(Usuario::getNombre).orElse("Nombre no encontrado");
    }

    public void recuperarContrasenaUsuario(Usuario usuario) {

        try {
            String nuevaContrasena = usuario.getContrasena();
            usuario.setContrasena(passwordService.encodePassword(nuevaContrasena));
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new IllegalArgumentException("Ocurrio un error");
        }

    }

}
