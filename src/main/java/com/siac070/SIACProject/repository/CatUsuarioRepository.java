package com.siac070.SIACProject.repository;

import com.siac070.SIACProject.model.CatUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatUsuarioRepository extends JpaRepository<CatUsuario, Long> {}
