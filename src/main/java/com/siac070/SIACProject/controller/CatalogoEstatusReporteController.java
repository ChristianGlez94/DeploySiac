package com.siac070.SIACProject.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

 import com.siac070.SIACProject.model.CatalogoEstatusReporte;
import com.siac070.SIACProject.service.CatalogoEstatusReporteService;

@RestController
@CrossOrigin
public class CatalogoEstatusReporteController {

    @Autowired
    private CatalogoEstatusReporteService catalogoEstatusReporteService;

    @PostMapping("/crear_catalogo_estatus_reporte")
    public ResponseEntity<Object> crearCatalogoEstatusReporte(@RequestBody CatalogoEstatusReporte catalogoEstatusReporte) {
        boolean exito = catalogoEstatusReporteService.crearCatalogoEstatusReporte(catalogoEstatusReporte);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Catálogo creado correctamente");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("message", "Error al crear el catálogo");
            response.put("success", false);
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    //actualizar catalogo
    @PostMapping("/actualizar_catalogo_estatus_reporte/{id}")
    public ResponseEntity<Object> actualizarCatalogoEstatusReporte(@RequestBody CatalogoEstatusReporte catalogoEstatusReporte, @PathVariable Long id) {
        boolean exito = catalogoEstatusReporteService.actualizarCatalogoEstatusReporte(id, catalogoEstatusReporte);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Catálogo actualizado correctamente");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error al actualizar el catálogo");
            response.put("success", false);
        }
       
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    ///eliminar catalogo
    @DeleteMapping("/eliminar_catalogo_estatus_reporte/{id}")
    public ResponseEntity<Object> eliminarCatalogoEstatusReporte(@PathVariable Long id) {
        boolean exito = catalogoEstatusReporteService.eliminarCatalogoEstatusReporte(id);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Catálogo eliminado correctamente");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error al eliminar el catálogo");
            response.put("success", false);
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
 
    @GetMapping("/obtener_catalogo_estatus_reporte")
    public ResponseEntity<Object> obtenerCatalogoEstatusReporte() {
        return new ResponseEntity<>(catalogoEstatusReporteService.obtenerCatalogoEstatusReporte(), HttpStatus.OK);
    }
}
