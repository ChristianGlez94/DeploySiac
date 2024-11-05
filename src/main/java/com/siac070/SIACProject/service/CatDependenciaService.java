package com.siac070.SIACProject.service;


import com.siac070.SIACProject.model.CatDependencia;
import com.siac070.SIACProject.repository.CatDependenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CatDependenciaService {

    @Autowired
    private CatDependenciaRepository catDependenciaRepository;

    // Obtener todas las dependencias
    public List<CatDependencia> getAllCatDependencias() {
        return catDependenciaRepository.findAll();
    }

    // Obtener una CatDependencia por ID
    public CatDependencia getCatDependenciaById(Integer id) {
        return catDependenciaRepository.findById(id).orElse(null);
    }

    // Guardar una nueva CatDependencia
    public CatDependencia saveCatDependencia(CatDependencia catDependencia) {
        catDependencia.setNombre(catDependencia.getNombre().strip());
        catDependencia.setContacto(catDependencia.getContacto().strip());
        return catDependenciaRepository.save(catDependencia);
    }

    // Eliminar una CatDependencia por ID
    public void deleteCatDependencia(Integer id) {
        catDependenciaRepository.deleteById(id);
    }
}
