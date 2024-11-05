package com.siac070.SIACProject.controller;


import com.siac070.SIACProject.model.Calle;
import com.siac070.SIACProject.model.Localidad;
import com.siac070.SIACProject.service.LocalidadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/localidades")
public class LocalidadController {

    @Autowired
    private LocalidadService localidadService;

    // Obtener todas las localidades
    @GetMapping
    public List<Localidad> getAllLocalidades() {
        return localidadService.getAllLocalidades();
    }

    // Obtener una localidad por ID
    @GetMapping("/{id}")
    public ResponseEntity<Localidad> getLocalidadById(@PathVariable Integer id) {
        Localidad localidad = localidadService.getLocalidadById(id);
        return localidad != null ? ResponseEntity.ok(localidad) : ResponseEntity.notFound().build();
    }

    // Crear una nueva localidad
    @PostMapping
    public Localidad createLocalidad(@RequestBody Localidad localidad) {
        return localidadService.saveLocalidad(localidad);
    }

    // Actualizar una localidad existente
    @PutMapping("/{id}")
    public ResponseEntity<Localidad> updateLocalidad(@PathVariable Integer id, @RequestBody Localidad localidadDetails) {
        Localidad localidad = localidadService.getLocalidadById(id);
        if (localidad != null) {
            localidad.setColonia(localidadDetails.getColonia());
            localidad.setCalle(localidadDetails.getCalle());
            return ResponseEntity.ok(localidadService.saveLocalidad(localidad));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una localidad por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocalidad(@PathVariable Integer id) {
        localidadService.deleteLocalidad(id);
        return ResponseEntity.ok().build();
    }
  
    @GetMapping("/obtener_calles_colonia/{idColonia}")
    public ResponseEntity<List<Calle>> getCallesByColonia(@PathVariable Integer idColonia) {
        List<Calle> calles = localidadService.getCallesByColoniaId(idColonia);
        if (calles.isEmpty()) {
            return ResponseEntity.noContent().build();   
        }
        return ResponseEntity.ok(calles); 
    }
}
