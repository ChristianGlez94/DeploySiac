package com.siac070.SIACProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.siac070.SIACProject.model.CatalogoEstatusReporte;

public interface CatalogoEstatusReporteRepository extends JpaRepository<CatalogoEstatusReporte, Long> {
    @Query("SELECT c.id FROM CatalogoEstatusReporte c ORDER BY c.id ASC LIMIT 1")
    Long findFirstIdByOrderByIdAsc();
}