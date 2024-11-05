package com.siac070.SIACProject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.siac070.SIACProject.model.ReportesRelacionados;
import com.siac070.SIACProject.repository.ReportesRelacionadosRepository;

import com.siac070.SIACProject.model.Reporte;
import com.siac070.SIACProject.repository.ReporteRepository;

import java.util.List;

@Service
public class ReportesRelacionadosService {

    @Autowired
    private ReportesRelacionadosRepository reportesRelacionadosR;

    @Autowired
    private ReporteRepository reporteRepository;

    public boolean crearReportesRelacionados(ReportesRelacionados reportesRelacionados) {
        try {
            reportesRelacionadosR.save(reportesRelacionados);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean asignarReporteTrabajador(Long idReporte, Long idTrabajador) {
        try {
            Optional<Reporte> reporteExistente = reporteRepository.findById(idReporte);
            if (reporteExistente.isPresent()) {
                int updatedCount = reportesRelacionadosR.updateTrabajadorByIdReportes(idReporte, idTrabajador);
                if (updatedCount > 0) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

   /*  public boolean asignarReporteTrabajador(Long idReporte, Long idUsuariosTrabajador) {
        try {
            int filasActualizadas = reportesRelacionadosRepository.updateTrabajadorByIdReportes(idReporte,
                    idUsuariosTrabajador);
            return filasActualizadas > 0;
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el error en la consola
            return false; // Retorna false si ocurre un error
        }
    } */

    @Autowired
    private ReportesRelacionadosRepository reportesRelacionadosRepository;

    // Obtener todos los reportes relacionados
    public List<ReportesRelacionados> getAllReportesRelacionados() {
        return reportesRelacionadosRepository.findAll();
    }

    // Guardar un reporte relacionado
    public ReportesRelacionados saveReportesRelacionados(ReportesRelacionados reportesRelacionados) {
        return reportesRelacionadosRepository.save(reportesRelacionados);
    }

    // Obtener un reporte relacionado por ID
    public ReportesRelacionados getReporteRelacionadoById(Long id) {
        return reportesRelacionadosRepository.findById(id).orElse(null);
    }

    // Eliminar un reporte relacionado por ID
    public void deleteReporteRelacionado(Long id) {
        reportesRelacionadosRepository.deleteById(id);
    }

    public void guardarReporteRelacionado(Long idUsuarioReporte, Reporte reporte, int notificacionCorreo) {
        ReportesRelacionados reportesRelacionados = new ReportesRelacionados();
        reportesRelacionados.setIdUsuariosReporte(idUsuarioReporte);
        reportesRelacionados.setReporte(reporte);
        reportesRelacionados.setNotificacionCorreo(notificacionCorreo);
        reportesRelacionadosRepository.save(reportesRelacionados);
    }
}
