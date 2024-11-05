package com.siac070.SIACProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siac070.SIACProject.model.ComentariosReporte;
import com.siac070.SIACProject.repository.ComentariosReporteRepository;

@Service
public class ComentariosReporteService {

    @Autowired
    private ComentariosReporteRepository comentariosReporteRepositorio;

    public boolean crearComentariosReporte(ComentariosReporte comentariosReporte) {
        try {
            comentariosReporte.setComentario(comentariosReporte.getComentario().strip());
            comentariosReporteRepositorio.save(comentariosReporte);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarComentario(Long id) {
        try {
            if (comentariosReporteRepositorio.existsById(id)) {
                comentariosReporteRepositorio.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
     
    public List<ComentariosReporte> obtenerComentariosPorUsuario(Long idUsuarios) {
        return comentariosReporteRepositorio.findByIdUsuarios(idUsuarios);
    }

    public List<ComentariosReporte> obtenerTodosComentariosReportes() {
        return comentariosReporteRepositorio.findAll();
    }
  
    public List<ComentariosReporte> obtenerComentariosPorReporteYEstado(Long idReporte, Long idCatEstatusComentarios) {
        return comentariosReporteRepositorio.findByIdReporteAndOptionalIdCatEstatus(idReporte, idCatEstatusComentarios);
    }
}
