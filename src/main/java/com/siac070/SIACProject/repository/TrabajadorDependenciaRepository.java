package com.siac070.SIACProject.repository;

import com.siac070.SIACProject.dto.UsuarioDTO;
import com.siac070.SIACProject.model.CatDependencia;
import com.siac070.SIACProject.model.TrabajadorDependencia;
import com.siac070.SIACProject.model.Usuario;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TrabajadorDependenciaRepository extends JpaRepository<TrabajadorDependencia, Integer> {
    Optional<TrabajadorDependencia> findByUsuarioAndDependencia(Usuario usuario, CatDependencia dependencia);

    @Query("SELECT new com.siac070.SIACProject.dto.UsuarioDTO(" +
            "td.usuario.id, td.usuario.nombre, td.usuario.apellidoPaterno, td.usuario.apellidoMaterno, " +
            "td.usuario.correo, td.usuario.telefono, td.usuario.catUsuario, td.usuario.catEstatusUsuario) " +
            "FROM TrabajadorDependencia td WHERE td.dependencia.id = :idDependencia")
    List<UsuarioDTO> getTrabajadoresByDependencia(Integer idDependencia);

    // Método para encontrar la dependencia de un usuario por su ID
    @Query("SELECT td FROM TrabajadorDependencia td WHERE td.usuario.id = ?1")
    Optional<TrabajadorDependencia> findByUsuarioId(Long usuarioId);

}
