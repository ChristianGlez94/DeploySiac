package com.siac070.SIACProject.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.siac070.SIACProject.model.HistorialReportes;
 
public interface HistorialReportesRepository extends JpaRepository<HistorialReportes, Long> {

    List<HistorialReportes> findByIdReportes(Long idReporte);
}