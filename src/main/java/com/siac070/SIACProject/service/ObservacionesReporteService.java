package com.siac070.SIACProject.service;
 
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.siac070.SIACProject.model.ObservacionesReporte;
import com.siac070.SIACProject.repository.ObservacionesReporteRepository;


@Service
public class ObservacionesReporteService {
    @Autowired
    private ObservacionesReporteRepository observacionesRepositorio;
    public boolean crearObservacionesReporte(ObservacionesReporte observaciones) {
        try {
            observaciones.setDescripcion(observaciones.getDescripcion().strip());
            observacionesRepositorio.save(observaciones);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<ObservacionesReporte> obtenerObservacionesReporte(Long idReporte) {
        return observacionesRepositorio.findByIdReportes(idReporte);
    }
 
}
