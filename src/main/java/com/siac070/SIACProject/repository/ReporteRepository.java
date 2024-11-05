package com.siac070.SIACProject.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.siac070.SIACProject.model.Reporte;

import org.springframework.stereotype.Repository;

@Repository
public interface ReporteRepository extends JpaRepository<Reporte, Long> {
    @Query("SELECT r FROM Reporte r WHERE FUNCTION('DATE', r.fecha_creacion) >= :fechaInicio")
    List<Reporte> findReportesMesActual(@Param("fechaInicio") LocalDate fechaInicio);

    List<Reporte> findReporteByClave(String clave);
}
