package com.siac070.SIACProject.model;

import java.util.Date;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String contrasena;

    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaCreacion;

    private String telefono;

    @ManyToOne
    @JoinColumn(name = "id_cat_usuarios")
    private CatUsuario catUsuario;

    @ManyToOne
    @JoinColumn(name = "id_cat_estatus_usuario")
    private CatEstatusUsuario catEstatusUsuario;

    @PrePersist
    protected void onCreate() {
        this.fechaCreacion = new Date();
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public CatUsuario getCatUsuario() {
        return catUsuario;
    }

    public void setCatUsuario(CatUsuario catUsuario) {
        this.catUsuario = catUsuario;
    }

    public CatEstatusUsuario getCatEstatusUsuario() {
        return catEstatusUsuario;
    }

    public void setCatEstatusUsuario(CatEstatusUsuario catEstatusUsuario) {
        this.catEstatusUsuario = catEstatusUsuario;
    }
}
