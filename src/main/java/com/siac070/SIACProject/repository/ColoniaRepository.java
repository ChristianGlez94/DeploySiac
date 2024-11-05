package com.siac070.SIACProject.repository;


import com.siac070.SIACProject.model.Colonia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ColoniaRepository extends JpaRepository<Colonia, Integer> {
    Colonia findByNombreIgnoreCase(String nombre);

}
