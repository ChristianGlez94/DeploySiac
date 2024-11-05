package com.siac070.SIACProject.controller;

import com.siac070.SIACProject.model.Reporte;
import com.siac070.SIACProject.service.ReporteService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/api/reportes")
public class ReporteController {

    @Autowired
    private ReporteService reporteService;

    @Value("${app.reports.directory}")
    private String imagesDirectoryReport;

    @GetMapping
    public ResponseEntity<List<Reporte>> getAllReportes(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null) {
            List<Reporte> reportes = reporteService.getAllReportes();
            return ResponseEntity.ok(reportes);
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Reporte> reportesPaginados = reporteService.getAllReportes(pageable);
        List<Reporte> reportes = reportesPaginados.getContent();
        if (reportes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Reporte> getReporteById(@PathVariable Long id) {
        Optional<Reporte> reporte = reporteService.getReporteById(id);
        return reporte.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Reporte createReporte(@RequestBody Reporte reporte) {
        return reporteService.saveReporte(reporte);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reporte> updateReporte(@PathVariable Long id, @RequestBody Reporte reporteDetails) {
        Optional<Reporte> reporte = reporteService.getReporteById(id);

        if (reporte.isPresent()) {
            Reporte updatedReporte = reporte.get();
            updatedReporte.setCoordenada_x(reporteDetails.getCoordenada_x());
            updatedReporte.setCoordenada_y(reporteDetails.getCoordenada_y());
            updatedReporte.setRuta_imagen(reporteDetails.getRuta_imagen());
            updatedReporte.setDescripcion(reporteDetails.getDescripcion());
            updatedReporte.setCalificacion(reporteDetails.getCalificacion());
            updatedReporte.setFecha_creacion(reporteDetails.getFecha_creacion());
            updatedReporte.setClave(reporteDetails.getClave());
            updatedReporte.setId_cat_reportes(reporteDetails.getId_cat_reportes());
            updatedReporte.setLocalidades_id(reporteDetails.getLocalidades_id());
            return ResponseEntity.ok(reporteService.saveReporte(updatedReporte));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReporte(@PathVariable Long id) {
        reporteService.deleteReporte(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping(value = "/crear_reporte", consumes = { "multipart/form-data" })
    public ResponseEntity<Object> crearReporte(
            @RequestParam("coordenada_x") double coordenadaX,
            @RequestParam("coordenada_y") double coordenadaY,
            @RequestParam("descripcion") String descripcion,
            @RequestParam("id_cat_reportes") int idCatReportes,
            @RequestParam("coloniaNombre") String coloniaNombre,
            @RequestParam("calleNombre") String calleNombre,
            @RequestParam(value = "notificacionCorreo", required = false) int notificacionCorreo,
            @RequestParam(value = "idUsuariosReporte", required = false) Long idUsuariosReporte,
            @RequestPart(value = "imagen", required = false) MultipartFile imagen) {

        Map<String, Object> response = new HashMap<>();

        try {

            if (imagen != null) {
                String mensaje = "";
                String contentType = imagen.getContentType();
                if ((contentType == null ||
                        (!contentType.equals("image/jpeg") &&
                                !contentType.equals("image/png") &&
                                !contentType.equals("image/jpg") &&
                                !contentType.equals("image/bmp")))
                        && mensaje == "") {
                    mensaje = "El archivo debe ser una imagen en formato JPEG, JPG, PNG o BMP";
                }

                if (mensaje != "") {
                    response.put("message", mensaje);
                    response.put("success", false);
                    return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
                }
            }

            Reporte reporte = new Reporte();
            reporte.setCoordenada_x(coordenadaX);
            reporte.setCoordenada_y(coordenadaY);
            reporte.setDescripcion(descripcion);
            reporte.setIdCatReportes(idCatReportes);
            reporte.setColoniaNombre(coloniaNombre);
            reporte.setCalleNombre(calleNombre);
            reporte.setNotificacionCorreo(notificacionCorreo);
            reporte.setIdUsuariosReporte(idUsuariosReporte);
            Long idReporte = reporteService.crearReporte(reporte, coloniaNombre, calleNombre, notificacionCorreo,
                    idUsuariosReporte);
            if (idReporte != null) {
                if (imagen != null && !imagen.isEmpty()) {
                    String nombreArchivo = reporteService.guardarImagenReporte(imagen, imagesDirectoryReport);
                    if (nombreArchivo != null) {
                        reporte.setRuta_imagen(nombreArchivo);
                        reporteService.saveReporte(reporte);
                    }
                }

                response.put("message", "Reporte creado con éxito");
                response.put("success", true);
                response.put("idreporte", idReporte);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.put("message", "Error al crear el reporte");
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }

        } catch (Exception e) {
            response.put("message", "Error al crear el reporte");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/agregar_calificacion/{id}")
    public ResponseEntity<Object> agregarCalificacion(@PathVariable Long id, @RequestBody Reporte calificacion) {
        boolean exito = reporteService.agregarCalificacion(id, calificacion.getCalificacion());
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Calificación agregada con éxito");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error al agregar la calificación");
        }
        response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PostMapping("/actualizar_reporte/{id}")
    public ResponseEntity<Object> actualizarReporte(@PathVariable Long id, @RequestBody Reporte reporteDetalles) {

        boolean exito = reporteService.actualizarReporte(id, reporteDetalles);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Reporte actualizado con éxito");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error al actualizar el reporte");
        }
        response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);

    }

  
    @GetMapping("/usuario/{idUsuarioReporte}")
    public ResponseEntity<List<Reporte>> obtenerReportesPorUsuario(
            @PathVariable Long idUsuarioReporte,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {

        Pageable pageable;

        
        if (page == null || size == null) {
            List<Reporte> reportes = reporteService.obtenerReportesPorUsuario(idUsuarioReporte);
            if (reportes.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(reportes);
        } else {
           
            pageable = PageRequest.of(page, size);
        }

      
        Page<Reporte> reportesPaginados = reporteService.obtenerReportesPorUsuario(idUsuarioReporte, pageable);
        List<Reporte> reportes = reportesPaginados.getContent();

        if (reportes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(reportes);
    }

    @GetMapping("/reporte_mes_actual")
    public ResponseEntity<List<Reporte>> obtenerReportesMesActual() {
        List<Reporte> reportes = reporteService.obtenerReportesMesActual();
        if (!reportes.isEmpty()) { // Verificar si la lista tiene elementos
            return ResponseEntity.ok(reportes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/usuarioCorreo")
    public ResponseEntity<List<Reporte>> obtenerReportesPorCorreo(@RequestBody Map<String, String> json) {
        String correo = json.get("correo");
        List<Reporte> reportes = reporteService.obtenerReportesPorCorreo(correo);
        if (reportes != null) {
            return ResponseEntity.ok(reportes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/buscar_reporte_clave")
    public ResponseEntity<Reporte> buscarReportePorClave(@RequestBody Map<String, String> json) {
        String clave = json.get("clave");
        List<Reporte> reporte = reporteService.buscarReportePorClave(clave);
        if (!reporte.isEmpty()) {
            return ResponseEntity.ok(reporte.get(0));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/usuarioTrabajador/{idUsuarioTrabajador}")
    public ResponseEntity<List<Reporte>> obtenerReportesPorUsuarioTrabajador(@PathVariable Long idUsuarioTrabajador) {
        List<Reporte> reportes = reporteService.obtenerReportesPorUsuarioTrabajador(idUsuarioTrabajador);
        if (reportes != null) {
            return ResponseEntity.ok(reportes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
