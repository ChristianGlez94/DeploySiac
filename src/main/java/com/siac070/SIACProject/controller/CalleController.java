package com.siac070.SIACProject.controller;

import com.siac070.SIACProject.model.Calle;
import com.siac070.SIACProject.service.CalleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/calles")
public class CalleController {

    @Autowired
    private CalleService calleService;

    // Obtener todas las calles
    @GetMapping
    public List<Calle> getAllCalles() {
        return calleService.getAllCalles();
    }

    // Obtener una calle por ID
    @GetMapping("/{id}")
    public ResponseEntity<Calle> getCalleById(@PathVariable Integer id) {
        Calle calle = calleService.getCalleById(id);
        return calle != null ? ResponseEntity.ok(calle) : ResponseEntity.notFound().build();
    }

    // Crear una nueva calle
    @PostMapping
    public Calle createCalle(@RequestBody Calle calle) {
        return calleService.saveCalle(calle);
    }

    // Actualizar una calle existente
    @PutMapping("/{id}")
    public ResponseEntity<Calle> updateCalle(@PathVariable Integer id, @RequestBody Calle calleDetails) {
        Calle calle = calleService.getCalleById(id);
        if (calle != null) {
            calle.setNombre(calleDetails.getNombre());
            return ResponseEntity.ok(calleService.saveCalle(calle));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Eliminar una calle por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCalle(@PathVariable Integer id) {
        calleService.deleteCalle(id);
        return ResponseEntity.ok().build();
    }
}
