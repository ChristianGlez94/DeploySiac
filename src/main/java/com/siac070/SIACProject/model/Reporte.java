package com.siac070.SIACProject.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
 

@Entity
@Table(name = "reportes")
public class Reporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double coordenada_x;
    private Double coordenada_y;

    @Column(length = 255)
    private String ruta_imagen;

    @Lob
    private String descripcion;

    private Integer calificacion;

    private LocalDateTime fecha_creacion;

    @Column(length = 255)
    private String clave;

    @Column(name = "id_cat_reportes", nullable = true)
    private Integer id_cat_reportes;

    @Column(name = "localidades_id", nullable = true)
    private Integer localidades_id;

    @OneToMany(mappedBy = "reporte")
    private List<ReportesRelacionados> reportesRelacionados;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "localidades_id", insertable = false, updatable = false)
    private Localidad localidad;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_cat_reportes", insertable = false, updatable = false)
    private CatalogoReporte catalogoReporte;

    @OneToMany(mappedBy = "reporte")
    @OrderBy("fechaCreacion DESC") // Ordenamos por la fecha del historial, el más reciente primero.
    private List<HistorialReportes> historialReportes;

    @Transient
    private String coloniaNombre;
    @Transient
    private String calleNombre;
    @Transient
    private String catReporte;
    @Transient
    private String rutaImgCatalogoReporte;

    @Transient
    private int notificacionCorreo;
    @Transient
    private Long idUsuarioReporte;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getCoordenada_x() {
        return coordenada_x;
    }

    public void setCoordenada_x(Double coordenada_x) {
        this.coordenada_x = coordenada_x;
    }

    public Double getCoordenada_y() {
        return coordenada_y;
    }

    public void setCoordenada_y(Double coordenada_y) {
        this.coordenada_y = coordenada_y;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCalificacion() {
        return calificacion;
    }

    public void setCalificacion(Integer calificacion) {
        this.calificacion = calificacion;
    }

    @PrePersist
    protected void onCreate() {
        this.fecha_creacion = LocalDateTime.now();
    }

    public LocalDateTime getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(LocalDateTime fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public Integer getId_cat_reportes() {
        return id_cat_reportes;
    }

    public void setId_cat_reportes(Integer id_cat_reportes) {
        this.id_cat_reportes = id_cat_reportes;
    }

    public Integer getLocalidades_id() {
        return localidades_id;
    }

    public void setLocalidades_id(Integer localidades_id) {
        this.localidades_id = localidades_id;
    }

    public Integer getIdCatReportes() {
        return id_cat_reportes;
    }

    public void setIdCatReportes(Integer idCatReportes) {
        this.id_cat_reportes = idCatReportes;
    }

    public Integer getLocalidadesId() {
        return localidades_id;
    }

    public void setLocalidadesId(Integer localidades_id) {
        this.localidades_id = localidades_id;
    }

    public String getColoniaNombre() {
        return coloniaNombre;
    }

    public void setColoniaNombre(String coloniaNombre) {
        this.coloniaNombre = coloniaNombre;
    }

    public String getCalleNombre() {
        return calleNombre;
    }

    public void setCalleNombre(String calleNombre) {
        this.calleNombre = calleNombre;
    }
    
    public int getNotificacionCorreo() {
        return notificacionCorreo;
    }

    public void setNotificacionCorreo(int notificacionCorreo) {
        this.notificacionCorreo = notificacionCorreo;
    }

    public Long getIdUsuariosReporte() {
        return idUsuarioReporte;
    }

    public void setIdUsuariosReporte(Long idUsuariosReporte) {
        this.idUsuarioReporte = idUsuariosReporte;
    }

    public Localidad getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad localidad) {
        this.localidad = localidad;
    }

    public CatalogoReporte getCatalogoReporte() {
        return catalogoReporte;
    }

    public void setCatalogoReporte(CatalogoReporte catalogoReporte) {
        this.catalogoReporte = catalogoReporte;
    }

    public String getCatReporte() {
        return catReporte;
    }

    public void setCatReporte(String catReporte) {
        this.catReporte = catReporte;
    }

    public String getRutaImgCatalogoReporte() {
        return rutaImgCatalogoReporte;
    }

    public void setRutaImgCatalogoReporte(String rutaImgCatalogoReporte) {
        this.rutaImgCatalogoReporte = rutaImgCatalogoReporte;
    }

    public String getUltimoEstadoHistorialReporte() {
        if (historialReportes != null && !historialReportes.isEmpty()) {
            return historialReportes.get(0).getEstatusReporte();
        }
        return null;
    }

    public Long getUltimoEstadoHistorialReporteId() {
        if (historialReportes != null && !historialReportes.isEmpty()) {
            return historialReportes.get(0).getIdCatEstatusReporte();
        }
        return null;
    }

    @PostLoad
    private void postLoad() {
        if (this.catalogoReporte != null) {
            this.catReporte = this.catalogoReporte.getNombre() != null ? this.catalogoReporte.getNombre() : null;
        }
        this.rutaImgCatalogoReporte = this.catalogoReporte.getRutaImagen() != null
                ? this.catalogoReporte.getRutaImagen()
                : null;
        if (this.localidad != null) {
            this.coloniaNombre = this.localidad.getColonia() != null ? this.localidad.getColonia().getNombre() : null;
            this.calleNombre = this.localidad.getCalle() != null ? this.localidad.getCalle().getNombre() : null;
        }

        if(this.reportesRelacionados != null && !this.reportesRelacionados.isEmpty()){
            this.idUsuarioReporte = this.reportesRelacionados.get(0).getIdUsuariosReporte();
            this.notificacionCorreo = this.reportesRelacionados.get(0).getNotificacionCorreo();
        }

    }
}
