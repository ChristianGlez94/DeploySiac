package com.siac070.SIACProject.model;
 
import jakarta.persistence.*;
 
@Entity
@Table(name = "observaciones")
public class ObservacionesReporte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_reportes")
    private Long idReportes;

    @Column(name = "descripcion")
    private String descripcion;

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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
  
}
