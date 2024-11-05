package com.siac070.SIACProject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siac070.SIACProject.model.CatalogoReporte;
import com.siac070.SIACProject.repository.CatalogoReportesRepository;

@Service
public class CatalogoReportesService {
    @Autowired
    private CatalogoReportesRepository catalogoReportesRepositorio;

    public boolean crearCatalogoReportes(CatalogoReporte catalogoReportes) {
        try {
            catalogoReportes.setNombre(catalogoReportes.getNombre().strip());
            catalogoReportes.setAbreviacion(catalogoReportes.getAbreviacion().strip());
            catalogoReportesRepositorio.save(catalogoReportes);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean actualizarCatalogoReportes(Long id, CatalogoReporte catalogoReportesDetalles) {
        try {
            if (catalogoReportesRepositorio.existsById(id)) {
                CatalogoReporte catalogoReportes = catalogoReportesRepositorio.findById(id).get();
                catalogoReportes.setNombre(catalogoReportesDetalles.getNombre().strip());
                catalogoReportes.setAbreviacion(catalogoReportesDetalles.getAbreviacion().strip());
                catalogoReportesRepositorio.save(catalogoReportes);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    // Metodo para eliminar un catalogo
    public boolean eliminarCatalogoReporte(Long id) {
        try {
            if (catalogoReportesRepositorio.existsById(id)) {
                catalogoReportesRepositorio.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List<CatalogoReporte> obtenerCatalogoReportes() {
        return catalogoReportesRepositorio.findAll();
    }

    public CatalogoReporte obtenerCatalogoReportePorId(Integer id) {
        try {
            Long idLong = Long.valueOf(id);
            return catalogoReportesRepositorio.findById(idLong).get();
        } catch (Exception e) {
            return null;
        }
    }

    public CatalogoReporte obtenerCatalogoReportePorId(Long id) {
        try {
            return catalogoReportesRepositorio.findById(id).get();
        } catch (Exception e) {
            return null;
        }
    }
 
    public boolean validarAbreviacionUnica(String abreviacion) {
        return !catalogoReportesRepositorio.existsByAbreviacion(abreviacion);
    }

    public boolean validarAbreviacionActualizacion(String abreviacion, Long id) {
        return !catalogoReportesRepositorio.existsByAbreviacionAndIdNot(abreviacion, id);
    }

}
