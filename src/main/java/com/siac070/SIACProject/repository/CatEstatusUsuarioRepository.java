package com.siac070.SIACProject.repository;

import com.siac070.SIACProject.model.CatEstatusUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CatEstatusUsuarioRepository extends JpaRepository<CatEstatusUsuario, Long> {}
