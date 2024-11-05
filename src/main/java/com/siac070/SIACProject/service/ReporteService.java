package com.siac070.SIACProject.service;

import com.siac070.SIACProject.model.Calle;
import com.siac070.SIACProject.model.CatalogoReporte;
import com.siac070.SIACProject.model.Colonia;
import com.siac070.SIACProject.model.Localidad;
import com.siac070.SIACProject.model.Reporte;

import com.siac070.SIACProject.repository.ReporteRepository;
import com.siac070.SIACProject.repository.ReportesRelacionadosRepository;
import com.siac070.SIACProject.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.io.File;
import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class ReporteService {

    @Autowired
    private ReporteRepository reporteRepository;

    @Autowired
    private ReportesRelacionadosRepository reportesRelacionadosRepository;

    @Autowired
    private ColoniaService coloniaService;

    @Autowired
    private CalleService calleService;

    @Autowired
    private LocalidadService localidadService;

    @Autowired
    private ReportesRelacionadosService reportesRelacionadosService;

    @Autowired
    private HistorialReportesService historialReportesService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CatalogoReportesService catalogoReportesService;

    public List<Reporte> getAllReportes() {
        return reporteRepository.findAll();
    }

    public Page<Reporte> getAllReportes(Pageable pageable) {
        return reporteRepository.findAll(pageable);
    }

    public Optional<Reporte> getReporteById(Long id) {
        return reporteRepository.findById(id);
    }

    public Reporte saveReporte(Reporte reporte) {
        return reporteRepository.save(reporte);
    }

    public void deleteReporte(Long id) {
        reporteRepository.deleteById(id);
    }

    public boolean eliminarReporteAnonimo(Long id) {
        try {
            if (reporteRepository.existsById(id)) {
                reporteRepository.deleteById(id);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {

            return false;
        }
    }

    public boolean actualizarReporte(Long id, Reporte reporteDetalles) {
        try {
            Optional<Reporte> reporteExistente = reporteRepository.findById(id);
            if (reporteExistente.isPresent()) {
                Reporte reporte = reporteExistente.get();
                reporte.setCoordenada_x(reporteDetalles.getCoordenada_x());
                reporte.setCoordenada_y(reporteDetalles.getCoordenada_y());
                reporte.setRuta_imagen(reporteDetalles.getRuta_imagen());
                reporte.setDescripcion(reporteDetalles.getDescripcion());
                reporte.setIdCatReportes(reporteDetalles.getIdCatReportes());
                reporte.setLocalidadesId(reporteDetalles.getLocalidadesId());
                reporteRepository.save(reporte);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public boolean agregarCalificacion(Long id, int calificacion) {
        try {
            Optional<Reporte> reporteExistente = reporteRepository.findById(id);
            if (reporteExistente.isPresent()) {
                Reporte reporte = reporteExistente.get();
                reporte.setCalificacion(calificacion);
                reporteRepository.save(reporte);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }

    public List<Reporte> obtenerTodosLosReportes() {
        return reporteRepository.findAll();
    }

    @Transactional
    public Long crearReporte(Reporte reporte, String coloniaNombre, String calleNombre, int notificacionCorreo,
            Long idUsuarioReporte) {
        try {
            Colonia colonia = coloniaService.buscarGuardarColonia(coloniaNombre);
            Calle calle = calleService.buscarGuardarCalle(calleNombre);
            Localidad localidad = localidadService.buscarOCrearLocalidad(calle, colonia);
            reporte.setLocalidadesId(localidad.getId());
            reporte.setDescripcion(reporte.getDescripcion().strip());
            reporteRepository.save(reporte);

            Long ultimoReporteId = reporte.getId();
            Integer id_cat_reportes = reporte.getIdCatReportes();
            CatalogoReporte catalogoReporte = catalogoReportesService.obtenerCatalogoReportePorId(id_cat_reportes);

            String clave = catalogoReporte.getAbreviacion() != null ? catalogoReporte.getAbreviacion() : "CLAVE";
            reporte.setClave(clave + "-" + ultimoReporteId);
            reporteRepository.save(reporte);

            reportesRelacionadosService.guardarReporteRelacionado(idUsuarioReporte, reporte, notificacionCorreo);
            historialReportesService.guardarHistorialReporte(reporte.getId());
            return reporte.getId();
        } catch (Exception e) {
            return null;
        }
    }
    public List<Reporte> obtenerReportesPorUsuario(Long idUsuarioReporte) {
        return reportesRelacionadosRepository.findReportesByIdUsuarioReporte(idUsuarioReporte);
    }
    
    public Page<Reporte> obtenerReportesPorUsuario(Long idUsuarioReporte, Pageable pageable) {
        return reportesRelacionadosRepository.findReportesByIdUsuarioReporte(idUsuarioReporte, pageable);
    }
    
    

    public List<Reporte> obtenerReportesMesActual() {
        LocalDate primerDiaDelMesActual = LocalDate.now().withDayOfMonth(1);
        return reporteRepository.findReportesMesActual(primerDiaDelMesActual);
    }

    public List<Reporte> obtenerReportesPorCorreo(String correo) {
        Long idUsuario = usuarioRepository.findIdByCorreo(correo);

        if (idUsuario != null) {
            List<Reporte> reportes = reportesRelacionadosRepository.findReportesByIdUsuarioReporte(idUsuario);
            if (reportes.size() > 0) {
                return reportes;
            } else {
                return null;
            }
        } else {
            return null;
        }

    }

    public List<Reporte> buscarReportePorClave(String clave) {
        return reporteRepository.findReporteByClave(clave.strip());
    }

    public List<Reporte> obtenerReportesPorUsuarioTrabajador(Long idUsuarioTrabajador) {
        return reportesRelacionadosRepository.findReportesByIdUsuarioTrabajador(idUsuarioTrabajador);
    }

    public String guardarImagenReporte(MultipartFile rutaImagen, String imagesDirectory) {
        try {
            // Determinar el directorio de almacenamiento
            Path uploadDir = Paths.get(imagesDirectory).isAbsolute()
                    ? Paths.get(imagesDirectory).normalize()
                    : Paths.get(System.getProperty("user.dir"), imagesDirectory).normalize();
          
            // Crear el directorio si no existe
            File directory = uploadDir.toFile();
            if (!directory.exists()) {
                directory.mkdirs();
            }

            // Obtener la extensión y generar un nombre de archivo único
            String extensionImagen = rutaImagen.getOriginalFilename()
                    .substring(rutaImagen.getOriginalFilename().lastIndexOf(".") + 1);
            String nombreArchivo = UUID.randomUUID().toString() + "." + extensionImagen;

            // Guardar el archivo en el directorio especificado
            File file = new File(uploadDir.toString(), nombreArchivo);
            rutaImagen.transferTo(file);
            
            return nombreArchivo;
            
        } catch (IOException e) {
            return null;
        }
    }

}