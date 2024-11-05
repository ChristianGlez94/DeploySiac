package com.siac070.SIACProject.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "historial_reportes")  
public class HistorialReportes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_reportes")  
    private Long idReportes;

    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;

    @Column(name = "id_cat_estatus_reporte")  
    private Long idCatEstatusReporte;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "id_cat_estatus_reporte", insertable = false, updatable = false)
    private CatalogoEstatusReporte catalogoEstatusReporte;

    @ManyToOne
    @JoinColumn(name = "id_reportes", insertable = false, updatable = false) 
    private Reporte reporte;

    @Transient
    private String estatusReporte;

 
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

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    @PrePersist
    public void setFechaCreacion() {
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getIdCatEstatusReporte() {
        return idCatEstatusReporte;
    }

    public void setIdCatEstatusReporte(Long idCatEstatusReporte) {
        this.idCatEstatusReporte = idCatEstatusReporte;
    }

    public CatalogoEstatusReporte getCatalogoEstatusReporte() {
        return catalogoEstatusReporte;
    }

    public void setCatalogoEstatusReporte(CatalogoEstatusReporte catalogoEstatusReporte) {
        this.catalogoEstatusReporte = catalogoEstatusReporte;
    }

    public String getEstatusReporte() {
        return estatusReporte;
    }

    public void setEstatusReporte(String estatusReporte) {
        this.estatusReporte = estatusReporte;
    }

    @PostLoad
    public void postLoad(){
        if (catalogoEstatusReporte != null) {
            this.estatusReporte = catalogoEstatusReporte.getNombre();
        }
    }
}
