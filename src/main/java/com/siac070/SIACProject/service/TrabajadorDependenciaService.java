package com.siac070.SIACProject.service;

import com.siac070.SIACProject.dto.UsuarioDTO;
import com.siac070.SIACProject.model.TrabajadorDependencia;
import com.siac070.SIACProject.repository.TrabajadorDependenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrabajadorDependenciaService {

    @Autowired
    private TrabajadorDependenciaRepository trabajadorDependenciaRepository;

    public List<TrabajadorDependencia> getAllTrabajadores() {
        return trabajadorDependenciaRepository.findAll();
    }

    public TrabajadorDependencia getTrabajadorById(Integer id) {
        return trabajadorDependenciaRepository.findById(id).orElse(null);
    }

    public boolean buscarUsuarioDependencia(TrabajadorDependencia trabajadorDependencia) {
        Optional<TrabajadorDependencia> existingTrabajador = trabajadorDependenciaRepository
                .findByUsuarioAndDependencia(trabajadorDependencia.getUsuario(),
                        trabajadorDependencia.getDependencia());
        if (existingTrabajador.isPresent()) {
            return false;
        } else {
            return true;
        }
    }

    public TrabajadorDependencia saveTrabajador(TrabajadorDependencia trabajadorDependencia) {
        return trabajadorDependenciaRepository.save(trabajadorDependencia);
    }

    public void deleteTrabajador(Integer id) {
        trabajadorDependenciaRepository.deleteById(id);
    }
 
    public List<UsuarioDTO> getUsuariosByDependencia(Integer id) {
        return trabajadorDependenciaRepository.getTrabajadoresByDependencia(id);
    }

    // Buscar la dependencia de un usuario por su ID
    public Optional<TrabajadorDependencia> findByUsuarioId(Long usuarioId) {
        return trabajadorDependenciaRepository.findByUsuarioId(usuarioId);
    }

}
