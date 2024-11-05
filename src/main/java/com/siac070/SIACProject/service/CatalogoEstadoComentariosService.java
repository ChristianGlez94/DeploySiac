package com.siac070.SIACProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siac070.SIACProject.model.CatalogoEstadoComentarios;
import com.siac070.SIACProject.repository.CatalogoEstadoComentariosRepository;

@Service
public class CatalogoEstadoComentariosService {

    @Autowired
    private CatalogoEstadoComentariosRepository catalogoEstadoComentariosRepositorio;

    public boolean crearCatalogoEstadoComentarios(CatalogoEstadoComentarios catalogoEstadoComentarios) {
        try {
            catalogoEstadoComentarios.setEstado(catalogoEstadoComentarios.getEstado().strip());
            catalogoEstadoComentariosRepositorio.save(catalogoEstadoComentarios);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
 
    public boolean actualizarCatalogoEstadoComentarios(Long id,
            CatalogoEstadoComentarios catalogoEstadoComentariosDetalles) {
        try {

            Optional<CatalogoEstadoComentarios> catalogoEstadoComentariosExistente = catalogoEstadoComentariosRepositorio.findById(id);
            if (catalogoEstadoComentariosExistente.isPresent()) {
                CatalogoEstadoComentarios catalogoEstadoComentarios = catalogoEstadoComentariosExistente.get();
                catalogoEstadoComentarios.setEstado(catalogoEstadoComentariosDetalles.getEstado().strip());
                catalogoEstadoComentariosRepositorio.save(catalogoEstadoComentarios);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    //Medoto para eliminar
    public boolean eliminarCatalogoEstadoComentarios(Long id) {
        try {
            if (catalogoEstadoComentariosRepositorio.existsById(id)) {
                catalogoEstadoComentariosRepositorio.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List<CatalogoEstadoComentarios> obtenerCatalogoEstadoComentarios() {
        return catalogoEstadoComentariosRepositorio.findAll();
    }
}
