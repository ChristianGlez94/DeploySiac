package com.siac070.SIACProject.controller;

import com.siac070.SIACProject.model.ReportesRelacionados;
import com.siac070.SIACProject.service.ReportesRelacionadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/reportes_relacionados")
public class ReportesRelacionadosController {

    @Autowired
    private ReportesRelacionadosService reportesRelacionadosService;
 
    @PostMapping("/crear_reportes_relacionados")
    public ResponseEntity<Object> crearReportesRelacionados(@RequestBody ReportesRelacionados reportesRelacionados) {
        boolean exito = reportesRelacionadosService.crearReportesRelacionados(reportesRelacionados);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Reportes relacionados creados con éxito");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al crear los reportes relacionados");
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/asignar_reporte/{idReporte}")
    public ResponseEntity<Object> asignarReporteTrabajador(@PathVariable Long idReporte, @RequestBody ReportesRelacionados reportesRelacionados) {
        boolean exito = reportesRelacionadosService.asignarReporteTrabajador(idReporte, reportesRelacionados.getIdUsuariosTrabajador());
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Reporte asignado con éxito");
            response.put("success", true);
            return ResponseEntity.ok(response);
        } else {
            response.put("message", "Error al asignar el reporte");
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Obtener todos los reportes relacionados
    @GetMapping
    public List<ReportesRelacionados> getAllReportesRelacionados() {
        return reportesRelacionadosService.getAllReportesRelacionados();
    }

    // Crear un nuevo reporte relacionado
    @PostMapping
    public ReportesRelacionados createReporteRelacionado(@RequestBody ReportesRelacionados reportesRelacionados) {
        return reportesRelacionadosService.saveReportesRelacionados(reportesRelacionados);
    }

    // Obtener un reporte relacionado por ID
    @GetMapping("/{id}")
    public ResponseEntity<ReportesRelacionados> getReporteRelacionadoById(@PathVariable Long id) {
        ReportesRelacionados reportesRelacionados = reportesRelacionadosService.getReporteRelacionadoById(id);
        return reportesRelacionados != null ? ResponseEntity.ok(reportesRelacionados) : ResponseEntity.notFound().build();
    }

    // Eliminar un reporte relacionado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporteRelacionado(@PathVariable Long id) {
        reportesRelacionadosService.deleteReporteRelacionado(id);
        return ResponseEntity.ok().build();
    }
}