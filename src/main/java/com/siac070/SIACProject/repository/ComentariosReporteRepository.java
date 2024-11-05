package com.siac070.SIACProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.siac070.SIACProject.model.ComentariosReporte;
import java.util.List;

public interface ComentariosReporteRepository extends JpaRepository<ComentariosReporte, Long> {
        List<ComentariosReporte> findByIdUsuarios(Long idUsuarios);
 
        @Query("SELECT c FROM ComentariosReporte c WHERE c.idReportes = :idReporte" +
                        " AND (:idCatEstatusComentarios IS NULL OR c.idCatEstatusComentarios = :idCatEstatusComentarios)")
        List<ComentariosReporte> findByIdReporteAndOptionalIdCatEstatus(
                        @Param("idReporte") Long idReporte,
                        @Param("idCatEstatusComentarios") Long idCatEstatusComentarios);
}
