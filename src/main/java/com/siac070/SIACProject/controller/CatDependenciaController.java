package com.siac070.SIACProject.controller;


import com.siac070.SIACProject.model.CatDependencia;
import com.siac070.SIACProject.service.CatDependenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/cat_dependencias")
public class CatDependenciaController {

    @Autowired
    private CatDependenciaService catDependenciaService;

    @GetMapping
    public List<CatDependencia> getAllCatDependencias() {
        return catDependenciaService.getAllCatDependencias();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatDependencia> getCatDependenciaById(@PathVariable Integer id) {
        CatDependencia catDependencia = catDependenciaService.getCatDependenciaById(id);
        return catDependencia != null ? ResponseEntity.ok(catDependencia) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public CatDependencia createCatDependencia(@RequestBody CatDependencia catDependencia) {
        return catDependenciaService.saveCatDependencia(catDependencia);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CatDependencia> updateCatDependencia(@PathVariable Integer id, @RequestBody CatDependencia catDependenciaDetails) {
        CatDependencia catDependencia = catDependenciaService.getCatDependenciaById(id);
        if (catDependencia != null) {
            catDependencia.setNombre(catDependenciaDetails.getNombre());
            catDependencia.setContacto(catDependenciaDetails.getContacto());
            return ResponseEntity.ok(catDependenciaService.saveCatDependencia(catDependencia));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatDependencia(@PathVariable Integer id) {
        catDependenciaService.deleteCatDependencia(id);
        return ResponseEntity.ok().build();
    }
}
