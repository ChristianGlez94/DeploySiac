package com.siac070.SIACProject.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "comentarios_reporte")
public class ComentariosReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_reportes")
    private Long idReportes;

    @Column(name = "contenido")
    private String comentario;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "id_usuarios", nullable = false)
    private Long idUsuarios;

    @Column(name = "id_cat_estatus_comentarios", nullable = false)
    private Long idCatEstatusComentarios;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_usuarios", insertable = false, updatable = false)
    private Usuario usuario;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_cat_estatus_comentarios", insertable = false, updatable = false)
    private CatalogoEstadoComentarios catalogoEstadoComentarios;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_reportes", insertable = false, updatable = false)
    private Reporte reporte;
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdReportes() {
        return idReportes;
    }

    public void setIdReportes(Long idReportes) {
        this.idReportes = idReportes;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    @PrePersist
    public void setFechaCreacion() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getIdUsuarios() {
        return idUsuarios;
    }

    public void setIdUsuarios(Long idUsuarios) {
        this.idUsuarios = idUsuarios;
    }

    public Long getIdCatEstatusComentarios() {
        return idCatEstatusComentarios;
    }

    public void setIdCatEstatusComentarios(Long idCatEstatusComentarios) {
        this.idCatEstatusComentarios = idCatEstatusComentarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public CatalogoEstadoComentarios getCatalogoEstadoComentarios() {
        return catalogoEstadoComentarios;
    }

    public void setCatalogoEstadoComentarios(CatalogoEstadoComentarios catalogoEstadoComentarios) {
        this.catalogoEstadoComentarios = catalogoEstadoComentarios;
    }

    public String getNombreUsuario() {
        return usuario.getNombre();
    }

    public String getApellidoPaternoUsuario() {
        return usuario.getApellidoPaterno();
    }
 
    public String getApellidoMaternoUsuario() {
        return usuario.getApellidoMaterno();
    }

    public String getEstatusComentario() {
        return catalogoEstadoComentarios.getEstado();
    }
 
    public String getCalleNombre() {
        return reporte.getCalleNombre();  
    }

    public String getColoniaNombre() {
        return reporte.getColoniaNombre();  
    }

}
