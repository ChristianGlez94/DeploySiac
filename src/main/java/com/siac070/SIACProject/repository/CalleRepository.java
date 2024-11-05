package com.siac070.SIACProject.repository;


import com.siac070.SIACProject.model.Calle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalleRepository extends JpaRepository<Calle, Integer> {
    Calle findByNombreIgnoreCase(String nombre);
}
