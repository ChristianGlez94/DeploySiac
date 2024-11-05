package com.siac070.SIACProject.service;

import com.siac070.SIACProject.model.CatEstatusUsuario;
import com.siac070.SIACProject.repository.CatEstatusUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatEstatusUsuarioService {

    @Autowired
    private CatEstatusUsuarioRepository catEstatusUsuarioRepository;

    // Obtener todos los CatEstatusUsuarios
    public List<CatEstatusUsuario> getAllCatEstatusUsuarios() {
        return catEstatusUsuarioRepository.findAll();
    }

    // Obtener CatEstatusUsuario por id
    public Optional<CatEstatusUsuario> getCatEstatusUsuarioById(Long id) {
        return catEstatusUsuarioRepository.findById(id);
    }

    // Guardar un CatEstatusUsuario
    public CatEstatusUsuario saveCatEstatusUsuario(CatEstatusUsuario catEstatusUsuario) {
        catEstatusUsuario.setEstatus(catEstatusUsuario.getEstatus().strip());
        return catEstatusUsuarioRepository.save(catEstatusUsuario);
    }

    // Eliminar un CatEstatusUsuario
    public void deleteCatEstatusUsuario(Long id) {
        catEstatusUsuarioRepository.deleteById(id);
    }
}
