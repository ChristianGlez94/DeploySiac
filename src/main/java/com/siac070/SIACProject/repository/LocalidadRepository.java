package com.siac070.SIACProject.repository;

import com.siac070.SIACProject.model.Calle;
import com.siac070.SIACProject.model.Colonia;
import com.siac070.SIACProject.model.Localidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface LocalidadRepository extends JpaRepository<Localidad, Integer> {
    Localidad findByCalleAndColonia(Calle calle, Colonia colonia);

    @Query("SELECT l.calle FROM Localidad l WHERE l.colonia.id = :idColonia")
    List<Calle> findCallesByColoniaId(@Param("idColonia") Integer idColonia);
}
