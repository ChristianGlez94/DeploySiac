package com.siac070.SIACProject.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "reportes_relacionados")
public class ReportesRelacionados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "notificacion_correo")
    private int notificacionCorreo;

    @Column(name = "id_usuarios_reporte")
    private Long idUsuariosReporte;

    @Column(name = "id_usuarios_trabajador")
    private Long idUsuariosTrabajador;

    //Relación ManyToOne con Reporte
    @ManyToOne
    @JoinColumn(name = "id_reportes", nullable = false) 
    private Reporte reporte;
 
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNotificacionCorreo() {
        return notificacionCorreo;
    }

    public void setNotificacionCorreo(int notificacionCorreo) {
        this.notificacionCorreo = notificacionCorreo;
    }

    public Long getIdUsuariosReporte() {
        return idUsuariosReporte;
    }

    public void setIdUsuariosReporte(Long idUsuariosReporte) {
        this.idUsuariosReporte = idUsuariosReporte;
    }

    public Long getIdUsuariosTrabajador() {
        return idUsuariosTrabajador;
    }

    public void setIdUsuariosTrabajador(Long idUsuariosTrabajador) {
        this.idUsuariosTrabajador = idUsuariosTrabajador;
    }

   
    public Reporte getReporte() {
        return reporte;
    }

    public void setReporte(Reporte reporte) {
        this.reporte = reporte;
    }
}


