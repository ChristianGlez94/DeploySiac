package com.siac070.SIACProject.controller;

import java.io.IOException;

import java.io.File;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.siac070.SIACProject.model.CatalogoReporte;
import com.siac070.SIACProject.service.CatalogoReportesService;
import com.siac070.SIACProject.service.ImageResizerService;

@RestController
@CrossOrigin
public class CatalogoReporteController {

    @Autowired
    private CatalogoReportesService catalogoReportesService;

    @Autowired
    private ImageResizerService imageResizerService;
  
    @Value("${app.images.directory}")
    private String imagesDirectory;

    @PostMapping("/crear_catalogo_reportes")
    public ResponseEntity<Object> crearCatalogoReportes(
            @RequestParam("nombre") String nombreCatReporte,
            @RequestParam("abreviacion") String abreviacionCatReporte,
            @RequestParam("rutaImagen") MultipartFile rutaImagen) {
        Map<String, Object> response = new HashMap<>();
        String mensaje = "";
        String nombre = nombreCatReporte.strip();
        String abreviacion = abreviacionCatReporte.strip();
 

        // Validaciones de los campos
        if (nombre == null || nombre.isEmpty()) {
            mensaje = "El nombre del catálogo de reportes no puede estar vacío";
        } else if ((rutaImagen == null || rutaImagen.isEmpty()) && mensaje == "") {
            mensaje = "La imagen del catálogo de reportes no puede estar vacía";
        } else if ((abreviacion == null || abreviacion.isEmpty() && mensaje == "")) {
            mensaje = "La abreviación del catálogo de reportes no puede estar vacía";
        }else if (!catalogoReportesService.validarAbreviacionUnica(abreviacion)) {
            mensaje = "La abreviación del catálogo de reportes ya existe";
        }

        String contentType = rutaImagen.getContentType();
        if ((contentType == null ||
                (!contentType.equals("image/jpeg") &&
                        !contentType.equals("image/png") &&
                        !contentType.equals("image/jpg") &&
                        !contentType.equals("image/bmp")))
                && mensaje == "") {
            mensaje = "El archivo debe ser una imagen en formato JPEG, JPG, PNG o BMP";
        }

        if (!mensaje.isEmpty()) {
            response.put("message", mensaje);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {

            Path uploadDir;

            if (Paths.get(imagesDirectory).isAbsolute()) {
                uploadDir = Paths.get(imagesDirectory).normalize();
            } else {
                uploadDir = Paths.get(System.getProperty("user.dir"), imagesDirectory).normalize();
            }

            File directory = uploadDir.toFile();
            if (!directory.exists()) {
                directory.mkdirs(); // Crea el directorio si no existe
            }

            String extensionImagen = rutaImagen.getOriginalFilename()
                    .substring(rutaImagen.getOriginalFilename().lastIndexOf(".") + 1);

            String timestamp = String.valueOf(System.currentTimeMillis());
            String nombreArchivo = timestamp + "." + extensionImagen;

            File file = new File(uploadDir.toString(), nombreArchivo);
            rutaImagen.transferTo(file); // Guarda la imagen en el servidor

            // Redimensiona la imagen usando el servicio
            imageResizerService.resizeImage(file, file, 1.0, 40, 40);

            CatalogoReporte catalogoReporte = new CatalogoReporte();
            catalogoReporte.setNombre(nombre);

            catalogoReporte.setRutaImagen(nombreArchivo);
            catalogoReporte.setAbreviacion(abreviacion);
 
            boolean exito = catalogoReportesService.crearCatalogoReportes(catalogoReporte);

            if (exito) {
                response.put("message", "Catálogo de reportes creado con éxito");
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.CREATED);
            } else {
                response.put("message", "Error al crear el catálogo de reportes");
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            response.put("message", "Error al guardar la imagen");
            response.put("success", false);
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("message", "Error al crear el catálogo de reportes");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
 
    @PostMapping("/actualizar_catalogo_reportes/{id}")
    public ResponseEntity<Object> actualizarCatalogoReportes(
            @PathVariable Long id,
            @RequestParam("nombre") String nombre,
            @RequestParam("abreviacion") String abreviacion,
            @RequestParam(value = "rutaImagen", required = false) MultipartFile rutaImagen) {

        Map<String, Object> response = new HashMap<>();
        String mensaje = "";
        CatalogoReporte catalogoExistente = catalogoReportesService.obtenerCatalogoReportePorId(id);

        if (catalogoExistente == null) {
            response.put("message", "El catálogo no existe");
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
 
        if (nombre == null || nombre.isEmpty()) {
            mensaje = "El nombre del catálogo de reportes no puede estar vacío";
        } else if ((rutaImagen == null || rutaImagen.isEmpty()) && mensaje == "") {
            mensaje = "La imagen del catálogo de reportes no puede estar vacía";
        } else if ((abreviacion == null || abreviacion.isEmpty() && mensaje == "")) {
            mensaje = "La abreviación del catálogo de reportes no puede estar vacía";
        }else if (!catalogoReportesService.validarAbreviacionActualizacion(abreviacion, id)) {
            mensaje = "La abreviación ya existe en un catálogo de reportes";
        }
        String contentType = rutaImagen.getContentType();
        if ((contentType == null ||
                (!contentType.equals("image/jpeg") &&
                        !contentType.equals("image/png") &&
                        !contentType.equals("image/jpg") &&
                        !contentType.equals("image/bmp")))
                && mensaje == "") {
            mensaje = "El archivo debe ser una imagen en formato JPEG, JPG, PNG o BMP";
        }
        
        if(mensaje != "") {
            response.put("message", mensaje);
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            // Actualiza los campos de texto en el objeto existente
            catalogoExistente.setNombre(nombre);
            catalogoExistente.setAbreviacion(abreviacion);

            if (rutaImagen != null && !rutaImagen.isEmpty()) {
                // Usa el nombre de archivo existente
                String existingFileName = catalogoExistente.getRutaImagen();
                Path uploadDir = Paths.get(imagesDirectory).normalize();
                File file = new File(uploadDir.toString(), existingFileName);
                // Sobrescribe la imagen en el servidor
                rutaImagen.transferTo(file);
                // Redimensiona la imagen usando el servicio
                imageResizerService.resizeImage(file, file, 1.0, 40, 40);
                 
            }

            // Llama al servicio para actualizar el objeto en la base de datos
            boolean exito = catalogoReportesService.actualizarCatalogoReportes(id, catalogoExistente);

            if (exito) {
                response.put("message", "Catálogo de reportes actualizado con éxito");
                response.put("success", true);
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("message", "Error al actualizar el catálogo de reportes");
                response.put("success", false);
                return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (IOException e) {
            response.put("message", "Error al actualizar la imagen");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            response.put("message", "Error al actualizar el catálogo de reportes");
            response.put("success", false);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/eliminar_catalogo_reportes/{id}")
    public ResponseEntity<Object> eliminarCatalogoReportes(@PathVariable Long id) {
        boolean exito = catalogoReportesService.eliminarCatalogoReporte(id);
        Map<String, Object> response = new HashMap<>();
        if (exito) {
            response.put("message", "Catálogo de reportes eliminado con éxito");
            response.put("success", true);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            response.put("message", "Error al eliminar el catálogo de reportes");
            response.put("success", false);
        }
        response.put("success", false);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @GetMapping("/catalogo_reportes")
    public List<CatalogoReporte> obtenerCatalogoReportes() {
        return catalogoReportesService.obtenerCatalogoReportes();
    }

    @GetMapping("/catalogo_reportes/{id}")
    public ResponseEntity<CatalogoReporte> obtenerCatalogoReportePorId(@PathVariable Integer id) {
        CatalogoReporte catalogoReporte = catalogoReportesService.obtenerCatalogoReportePorId(id);
        if (catalogoReporte != null) {
            return ResponseEntity.ok(catalogoReporte);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
