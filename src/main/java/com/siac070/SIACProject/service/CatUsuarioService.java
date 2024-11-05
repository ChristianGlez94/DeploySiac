package com.siac070.SIACProject.service;

import com.siac070.SIACProject.model.CatUsuario;
import com.siac070.SIACProject.repository.CatUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CatUsuarioService {

    @Autowired
    private CatUsuarioRepository catUsuarioRepository;

    // Obtener todos los CatUsuarios
    public List<CatUsuario> getAllCatUsuarios() {
        return catUsuarioRepository.findAll();
    }

    // Obtener CatUsuario por id
    public Optional<CatUsuario> getCatUsuarioById(Long id) {
        return catUsuarioRepository.findById(id);
    }

    // Guardar un CatUsuario
    public CatUsuario saveCatUsuario(CatUsuario catUsuario) {
        catUsuario.setNombre(catUsuario.getNombre().strip());
        return catUsuarioRepository.save(catUsuario);
    }

    // Eliminar un CatUsuario
    public void deleteCatUsuario(Long id) {
        catUsuarioRepository.deleteById(id);
    }
}
