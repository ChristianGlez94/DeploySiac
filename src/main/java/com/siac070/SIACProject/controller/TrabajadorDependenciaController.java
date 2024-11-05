package com.siac070.SIACProject.controller;

import com.siac070.SIACProject.dto.UsuarioDTO;
import com.siac070.SIACProject.model.CatDependencia;
import com.siac070.SIACProject.model.TrabajadorDependencia;
import com.siac070.SIACProject.service.CatDependenciaService;
import com.siac070.SIACProject.service.TrabajadorDependenciaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/trabajadores_dependencias")
public class TrabajadorDependenciaController {

    @Autowired
    private TrabajadorDependenciaService trabajadorDependenciaService;

    @Autowired
    private CatDependenciaService CatDependenciaService;


    @GetMapping
    public List<TrabajadorDependencia> getAllTrabajadores() {
        return trabajadorDependenciaService.getAllTrabajadores();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrabajadorDependencia> getTrabajadorById(@PathVariable Integer id) {
        TrabajadorDependencia trabajador = trabajadorDependenciaService.getTrabajadorById(id);
        return trabajador != null ? ResponseEntity.ok(trabajador) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Object> createTrabajador(@RequestBody TrabajadorDependencia trabajadorDependencia) {

        Map<String, Object> response = new HashMap<>();

        try {
            boolean savedTrabajador = trabajadorDependenciaService.buscarUsuarioDependencia(trabajadorDependencia);
            if (savedTrabajador == true) {
                trabajadorDependenciaService.saveTrabajador(trabajadorDependencia);
                response.put("message", "Trabajador relacionado a la dependencia correctamente");
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.put("message", "El trabajador ya existe en esta dependencia");
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.CONFLICT);
            }

        } catch (RuntimeException e) {
            response.put("message", "Ocurrio un error");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Object> updateTrabajador(@PathVariable Integer id,
            @RequestBody TrabajadorDependencia trabajadorDetails) {
        Map<String, Object> response = new HashMap<>();
        try {
            TrabajadorDependencia trabajador = trabajadorDependenciaService.getTrabajadorById(id);
            if (trabajador != null) {

                trabajador.setDependencia(trabajadorDetails.getDependencia());
                trabajador.setUsuario(trabajadorDetails.getUsuario());
                trabajadorDependenciaService.saveTrabajador(trabajador);
                response.put("message", "Trabajador relacionado a la dependencia correctamente");
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.OK);

            } else {
                response.put("message", "No se encontraron resultados");
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (RuntimeException e) {
            response.put("message", "Ocurrio un error");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrabajador(@PathVariable Integer id) {
        trabajadorDependenciaService.deleteTrabajador(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/dependencia/{idDependencia}")
    public ResponseEntity<List<UsuarioDTO>> getTrabajadoresByDependencia(@PathVariable Integer idDependencia) {
        CatDependencia catDependencia = CatDependenciaService.getCatDependenciaById(idDependencia);
        if(catDependencia == null){
            return ResponseEntity.notFound().build();
        }else{
            List<UsuarioDTO> trabajadores = trabajadorDependenciaService.getUsuariosByDependencia(idDependencia);
            return trabajadores != null ? ResponseEntity.ok(trabajadores) : ResponseEntity.notFound().build();
        }
    }
}
