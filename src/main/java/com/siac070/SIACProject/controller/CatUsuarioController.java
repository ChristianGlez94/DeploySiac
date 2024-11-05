package com.siac070.SIACProject.controller;

import com.siac070.SIACProject.model.CatUsuario;
import com.siac070.SIACProject.service.CatUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/cat-usuarios")
public class CatUsuarioController {

    @Autowired
    private CatUsuarioService catUsuarioService;

    // Obtener todos los CatUsuarios
    @GetMapping
    public List<CatUsuario> getAllCatUsuarios() {
        return catUsuarioService.getAllCatUsuarios();
    }

    // Obtener CatUsuario por id
    @GetMapping("/{id}")
    public ResponseEntity<CatUsuario> getCatUsuarioById(@PathVariable Long id) {
        Optional<CatUsuario> catUsuario = catUsuarioService.getCatUsuarioById(id);
        return catUsuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear un nuevo CatUsuario
    @PostMapping
    public CatUsuario createCatUsuario(@RequestBody CatUsuario catUsuario) {
        return catUsuarioService.saveCatUsuario(catUsuario);
    }

    // Eliminar un CatUsuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCatUsuario(@PathVariable Long id) {
        catUsuarioService.deleteCatUsuario(id);
        return ResponseEntity.noContent().build();
    }
}
