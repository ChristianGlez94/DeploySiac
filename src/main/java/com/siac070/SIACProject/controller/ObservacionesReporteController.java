package com.siac070.SIACProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siac070.SIACProject.model.ObservacionesReporte;
import com.siac070.SIACProject.service.ObservacionesReporteService;

@RestController
@CrossOrigin
public class ObservacionesReporteController {
    @Autowired
    private ObservacionesReporteService observacionesReporteService;

    @PostMapping("/crear_observaciones_reporte")
    public ResponseEntity<Object> crearObservacionesReporte(@RequestBody ObservacionesReporte observacionesReporte) {
        boolean exito = observacionesReporteService.crearObservacionesReporte(observacionesReporte);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Observación de reporte creada con éxito");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("message", "Error al crear la observación de reporte");
            response.put("success", false);
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/observaciones_reporte/{idReporte}")
    public ResponseEntity<List<ObservacionesReporte>> getObservaciones(@PathVariable Long idReporte) {
        List<ObservacionesReporte> observaciones = observacionesReporteService.obtenerObservacionesReporte(idReporte);
        if (observaciones != null) {
            return ResponseEntity.ok(observaciones);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleException(HttpMessageNotReadableException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Error al actualizar el reporte");
        response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
  
}
