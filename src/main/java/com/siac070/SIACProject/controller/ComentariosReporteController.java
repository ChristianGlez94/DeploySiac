package com.siac070.SIACProject.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.siac070.SIACProject.model.ComentariosReporte;
import com.siac070.SIACProject.service.ComentariosReporteService;

@RestController
@CrossOrigin
public class ComentariosReporteController {
    @Autowired
    private ComentariosReporteService comentariosReporteService;

    @PostMapping("/crear_comentarios_reporte")
    public ResponseEntity<Object> crearComentariosReporte(@RequestBody ComentariosReporte comentariosReporte) {
        boolean exito = comentariosReporteService.crearComentariosReporte(comentariosReporte);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Comentario de reporte creado con éxito");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } else {
            response.put("message", "Error al crear el comentario de reporte");
            response.put("success", false);
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // Obtener comentarios por el ID del usuario
    @GetMapping("/comentarios_usuario/{idUsuarios}")
    public ResponseEntity<List<ComentariosReporte>> obtenerComentariosPorUsuario(@PathVariable Long idUsuarios) {
        List<ComentariosReporte> comentarios = comentariosReporteService.obtenerComentariosPorUsuario(idUsuarios);
        return comentarios.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(comentarios);
    }

    @GetMapping("/obtener_comentarios_reportes")
    public List<ComentariosReporte> obtenerTodosComentariosReportes() {
        return comentariosReporteService.obtenerTodosComentariosReportes();
    }

    @GetMapping("/comentarios_reporte/{idReporte}")
    public ResponseEntity<List<ComentariosReporte>> obtenerComentariosPorIdReportes(
            @PathVariable Long idReporte,
            @RequestBody(required = false) ComentariosReporte comentariosReporte) {

        List<ComentariosReporte> comentarios = comentariosReporteService.obtenerComentariosPorReporteYEstado(idReporte,
                comentariosReporte != null ? comentariosReporte.getIdCatEstatusComentarios() : null);

        return comentarios.isEmpty() ? ResponseEntity.notFound().build() : ResponseEntity.ok(comentarios);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleException(HttpMessageNotReadableException e) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Ocurrio un error");
        response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/eliminar_comentario/{id}")
    public ResponseEntity<Object> eliminarComentario(@PathVariable Long id) {
        boolean exito = comentariosReporteService.eliminarComentario(id);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Comentario eliminado con éxito");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error al eliminar el comentario");
            response.put("success", false);
        }
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
