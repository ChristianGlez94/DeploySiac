package com.siac070.SIACProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.siac070.SIACProject.model.HistorialReportes;
import com.siac070.SIACProject.repository.CatalogoEstatusReporteRepository;
import com.siac070.SIACProject.repository.HistorialReportesRepository;

@Service
public class HistorialReportesService {
    @Autowired
    private HistorialReportesRepository historialReportesRepository;

    @Autowired
    private CatalogoEstatusReporteRepository catalogoEstatusReporteRepository;

    public boolean crearHistorialReportes(HistorialReportes historialReportes) {
        try {
            historialReportesRepository.save(historialReportes);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public List<HistorialReportes> obtenerHistorialReportes() {
        return historialReportesRepository.findAll();
    }

    public List<HistorialReportes> obtenerHistorialReporte(Long idReporte) {
        return historialReportesRepository.findByIdReportes(idReporte);
    }

    public void guardarHistorialReporte(Long idReporte) {
        HistorialReportes historialReportes = new HistorialReportes();
        historialReportes.setIdReportes(idReporte);
        Long idCatalogoEstatusReporte = catalogoEstatusReporteRepository.findFirstIdByOrderByIdAsc();
        historialReportes.setIdCatEstatusReporte(idCatalogoEstatusReporte);
        historialReportesRepository.save(historialReportes);
    }
}