package com.siac070.SIACProject.service;

import com.siac070.SIACProject.model.Calle;
import com.siac070.SIACProject.model.Colonia;
import com.siac070.SIACProject.model.Localidad;
import com.siac070.SIACProject.repository.LocalidadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocalidadService {

    @Autowired
    private LocalidadRepository localidadRepository;

    // Obtener todas las localidades
    public List<Localidad> getAllLocalidades() {
        return localidadRepository.findAll();
    }

    // Obtener localidad por ID
    public Localidad getLocalidadById(Integer id) {
        return localidadRepository.findById(id).orElse(null);
    }

    // Crear o guardar una localidad
    public Localidad saveLocalidad(Localidad localidad) {
        return localidadRepository.save(localidad);
    }

    // Eliminar una localidad por ID
    public void deleteLocalidad(Integer id) {
        localidadRepository.deleteById(id);
    }
    
    public Localidad buscarOCrearLocalidad(Calle calle, Colonia colonia){
        Localidad localidadExistente = localidadRepository.findByCalleAndColonia(calle, colonia);
        Localidad localidad;

        if(localidadExistente != null){
            localidad = localidadExistente;
        }else{
            localidad = new Localidad();
            localidad.setCalle(calle);
            localidad.setColonia(colonia);
            localidad = localidadRepository.save(localidad);
        }
        return localidad;
    }
 
    public List<Calle> getCallesByColoniaId(Integer idColonia) {
        return localidadRepository.findCallesByColoniaId(idColonia);
    }
}
