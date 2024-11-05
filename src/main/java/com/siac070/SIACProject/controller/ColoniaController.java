package com.siac070.SIACProject.controller;

import com.siac070.SIACProject.model.Colonia;
import com.siac070.SIACProject.service.ColoniaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/colonias")
public class ColoniaController {

    @Autowired
    private ColoniaService coloniaService;

    @GetMapping
    public List<Colonia> getAllColonias() {
        return coloniaService.getAllColonias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Colonia> getColoniaById(@PathVariable Integer id) {
        Colonia colonia = coloniaService.getColoniaById(id);
        return colonia != null ? ResponseEntity.ok(colonia) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public Colonia createColonia(@RequestBody Colonia colonia) {
        return coloniaService.saveColonia(colonia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Colonia> updateColonia(@PathVariable Integer id, @RequestBody Colonia coloniaDetails) {
        Colonia colonia = coloniaService.getColoniaById(id);
        if (colonia != null) {
            colonia.setNombre(coloniaDetails.getNombre());
            return ResponseEntity.ok(coloniaService.saveColonia(colonia));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteColonia(@PathVariable Integer id) {
        coloniaService.deleteColonia(id);
        return ResponseEntity.ok().build();
    }
}
