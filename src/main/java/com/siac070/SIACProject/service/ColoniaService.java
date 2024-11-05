package com.siac070.SIACProject.service;

import com.siac070.SIACProject.model.Colonia;
import com.siac070.SIACProject.repository.ColoniaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColoniaService {

    @Autowired
    private ColoniaRepository coloniaRepository;

    public List<Colonia> getAllColonias() {
        return coloniaRepository.findAll();
    }

    public Colonia getColoniaById(Integer id) {
        return coloniaRepository.findById(id).orElse(null);
    }

    public Colonia saveColonia(Colonia colonia) {
        colonia.setNombre(colonia.getNombre().strip());
        return coloniaRepository.save(colonia);
    }

    public void deleteColonia(Integer id) {
        coloniaRepository.deleteById(id);
    }

    public Colonia buscarGuardarColonia(String nombreColonia){
        String coloniaNombreSinEspacios = nombreColonia.trim();
        Colonia colonia = coloniaRepository.findByNombreIgnoreCase(coloniaNombreSinEspacios);
        if (colonia == null) {
            colonia = new Colonia();
            colonia.setNombre(coloniaNombreSinEspacios);
            colonia = coloniaRepository.save(colonia);
        }
        return colonia;
    }

}

