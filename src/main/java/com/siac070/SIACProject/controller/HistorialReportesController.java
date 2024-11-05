package com.siac070.SIACProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siac070.SIACProject.model.HistorialReportes;
import com.siac070.SIACProject.service.HistorialReportesService;

@RestController
@CrossOrigin
public class HistorialReportesController {
    @Autowired
    private HistorialReportesService historialReportesService;

    @PostMapping("/crear_historial_reportes")
    public ResponseEntity<Object> crearHistorialReportes(@RequestBody HistorialReportes historialReportes) {
        boolean exito = historialReportesService.crearHistorialReportes(historialReportes);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Historial de reportes creado con éxito");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("message", "Error al crear el historial de reportes");
        }
        response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/historial_reportes")
    public ResponseEntity<List<HistorialReportes>> obtenerHistorialReportes() {
        List<HistorialReportes> historialReportes = historialReportesService.obtenerHistorialReportes();
        if (historialReportes != null) {
            return ResponseEntity.ok(historialReportes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("historial_reporte/{idReporte}")
    public ResponseEntity<List<HistorialReportes>> obtenerHistorialReporte(@PathVariable Long idReporte) {
        List<HistorialReportes> historialReportes = historialReportesService.obtenerHistorialReporte(idReporte);
        if (historialReportes != null) {
            return ResponseEntity.ok(historialReportes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
