package com.siac070.SIACProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.siac070.SIACProject.model.ObservacionesReporte;

public interface ObservacionesReporteRepository extends JpaRepository<ObservacionesReporte, Long> {
    List<ObservacionesReporte> findByIdReportes(Long idReporte);
}
