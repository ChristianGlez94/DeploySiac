package com.siac070.SIACProject.service;


import com.siac070.SIACProject.model.Calle;
import com.siac070.SIACProject.repository.CalleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalleService {

    @Autowired
    private CalleRepository calleRepository;

    // Obtener todas las calles
    public List<Calle> getAllCalles() {
        return calleRepository.findAll();
    }

    // Obtener calle por ID
    public Calle getCalleById(Integer id) {
        return calleRepository.findById(id).orElse(null);
    }

    // Crear o guardar una calle
    public Calle saveCalle(Calle calle) {
        calle.setNombre(calle.getNombre().strip());
        return calleRepository.save(calle);
    }

    // Eliminar una calle por ID
    public void deleteCalle(Integer id) {
        calleRepository.deleteById(id);
    }

    public Calle buscarGuardarCalle(String nombreCalle){
        String calleNombreSinEspacios = nombreCalle.strip();
        Calle calle = calleRepository.findByNombreIgnoreCase(calleNombreSinEspacios);
        if (calle == null) {
            calle = new Calle();
            calle.setNombre(calleNombreSinEspacios);
            calle = calleRepository.save(calle);
        }
        return calle;
    }
}
