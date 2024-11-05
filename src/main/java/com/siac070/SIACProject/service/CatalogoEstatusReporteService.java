package com.siac070.SIACProject.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siac070.SIACProject.model.CatalogoEstatusReporte;
import com.siac070.SIACProject.repository.CatalogoEstatusReporteRepository;

@Service
public class CatalogoEstatusReporteService {

    @Autowired
    private CatalogoEstatusReporteRepository catalogoEstatusReporteRepositorio;

    public boolean crearCatalogoEstatusReporte(CatalogoEstatusReporte catalogoEstatusReporte) {
        try {
            catalogoEstatusReporte.setNombre(catalogoEstatusReporte.getNombre().strip());
            catalogoEstatusReporteRepositorio.save(catalogoEstatusReporte);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean actualizarCatalogoEstatusReporte(Long id, CatalogoEstatusReporte catalogoEstatusReporteDetalles) {
        try {

            Optional<CatalogoEstatusReporte> catalogoEstatusReporteExistente = catalogoEstatusReporteRepositorio.findById(id);
            if (catalogoEstatusReporteExistente.isPresent()) {
                CatalogoEstatusReporte catalogoEstatusReporte = catalogoEstatusReporteExistente.get();
                catalogoEstatusReporte.setNombre(catalogoEstatusReporteDetalles.getNombre().strip());
                catalogoEstatusReporteRepositorio.save(catalogoEstatusReporte);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean eliminarCatalogoEstatusReporte(Long id) {
        try {
            if (catalogoEstatusReporteRepositorio.existsById(id)) {
                catalogoEstatusReporteRepositorio.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List<CatalogoEstatusReporte> obtenerCatalogoEstatusReporte() {
        return catalogoEstatusReporteRepositorio.findAll();
    }
}
