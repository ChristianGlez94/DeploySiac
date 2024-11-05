package com.siac070.SIACProject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.siac070.SIACProject.model.CatalogoReporte;

public interface CatalogoReportesRepository extends JpaRepository<CatalogoReporte, Long> {
    boolean existsByAbreviacion(String abreviacion);

    boolean existsByAbreviacionAndIdNot(String abreviacion, Long id);

}