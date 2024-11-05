package com.siac070.SIACProject.repository;

import com.siac070.SIACProject.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByCorreo(String correo);

    @Query("SELECT u.id FROM Usuario u WHERE u.correo = :correo")
    Long findIdByCorreo(@Param("correo") String correo);
}
