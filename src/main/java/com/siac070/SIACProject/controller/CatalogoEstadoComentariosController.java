package com.siac070.SIACProject.controller;

import java.util.HashMap;
import java.util.List;
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

import com.siac070.SIACProject.model.CatalogoEstadoComentarios;
import com.siac070.SIACProject.service.CatalogoEstadoComentariosService;

@RestController
@CrossOrigin
public class CatalogoEstadoComentariosController {

    @Autowired
    private CatalogoEstadoComentariosService catalogoEstadoComentariosService;

    @PostMapping("/crear_catalogo_estado_comentarios")
    public ResponseEntity<Object> crearCatalogoEstadoComentarios(@RequestBody CatalogoEstadoComentarios catalogoEstadoComentarios) {
        boolean exito = catalogoEstadoComentariosService.crearCatalogoEstadoComentarios(catalogoEstadoComentarios);
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
    @PostMapping("/actualizar_catalogo_estado_comentarios/{id}")
    public ResponseEntity<Object> actualizarCatalogoEstadoComentarios(@RequestBody CatalogoEstadoComentarios catalogoEstadoComentarios, @PathVariable Long id) {
        boolean exito = catalogoEstadoComentariosService.actualizarCatalogoEstadoComentarios(id, catalogoEstadoComentarios);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Catálogo actualizado correctamente");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error al actualizar el catálogo");
            response.put("success", false);
        }
        response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    ///eliminar catalogo
    @DeleteMapping("/eliminar_catalogo_estado_comentarios/{id}")
    public ResponseEntity<Object> eliminarCatalogoEstadoComentarios(@PathVariable Long id) {
        boolean exito = catalogoEstadoComentariosService.eliminarCatalogoEstadoComentarios(id);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Catálogo eliminado correctamente");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error al eliminar el catálogo");
            response.put("success", false);
        }
        response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/obtener_catalogo_estado_comentarios")
    public List<CatalogoEstadoComentarios> obtenerCatalogoEstadoComentarios() {
        return catalogoEstadoComentariosService.obtenerCatalogoEstadoComentarios();
    }
}
