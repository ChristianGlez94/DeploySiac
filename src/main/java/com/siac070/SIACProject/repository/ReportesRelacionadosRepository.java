package com.siac070.SIACProject.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.siac070.SIACProject.model.Reporte;
import com.siac070.SIACProject.model.ReportesRelacionados;

import jakarta.transaction.Transactional;

public interface ReportesRelacionadosRepository extends JpaRepository<ReportesRelacionados, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE ReportesRelacionados r SET r.idUsuariosTrabajador = :idTrabajador WHERE r.reporte.id = :idReportes")
    int updateTrabajadorByIdReportes(@Param("idReportes") Long idReportes, @Param("idTrabajador") Long idTrabajador);

    @Query("SELECT r FROM ReportesRelacionados rr JOIN rr.reporte r WHERE rr.idUsuariosReporte = :idUsuariosReporte")
    List<Reporte> findReportesByIdUsuarioReporte(Long idUsuariosReporte);

    @Query("SELECT r FROM ReportesRelacionados rr JOIN rr.reporte r WHERE rr.idUsuariosReporte = :idUsuariosReporte")
    Page<Reporte> findReportesByIdUsuarioReporte(Long idUsuariosReporte, Pageable pageable);

    @Query("SELECT r FROM ReportesRelacionados rr JOIN rr.reporte r WHERE rr.idUsuariosTrabajador = :idUsuariosTrabajador")
    List<Reporte> findReportesByIdUsuarioTrabajador(Long idUsuariosTrabajador);
}
