package com.siac070.SIACProject.controller;

import com.siac070.SIACProject.model.CatEstatusUsuario;
import com.siac070.SIACProject.service.CatEstatusUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/cat-estatus-usuarios")
public class CatEstatusUsuarioController {

    @Autowired
    private CatEstatusUsuarioService catEstatusUsuarioService;

    // Obtener todos los CatEstatusUsuarios
    @GetMapping
    public List<CatEstatusUsuario> getAllCatEstatusUsuarios() {
        return catEstatusUsuarioService.getAllCatEstatusUsuarios();
    }

    // Obtener CatEstatusUsuario por id
    @GetMapping("/{id}")
    public ResponseEntity<CatEstatusUsuario> getCatEstatusUsuarioById(@PathVariable Long id) {
        Optional<CatEstatusUsuario> catEstatusUsuario = catEstatusUsuarioService.getCatEstatusUsuarioById(id);
        return catEstatusUsuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo CatEstatusUsuario
    @PostMapping
    public CatEstatusUsuario createCatEstatusUsuario(@RequestBody CatEstatusUsuario catEstatusUsuario) {
        return catEstatusUsuarioService.saveCatEstatusUsuario(catEstatusUsuario);
    }

    // Eliminar un CatEstatusUsuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatEstatusUsuario(@PathVariable Long id) {
        catEstatusUsuarioService.deleteCatEstatusUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
