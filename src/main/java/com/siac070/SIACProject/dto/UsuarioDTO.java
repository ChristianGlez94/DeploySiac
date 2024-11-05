package com.siac070.SIACProject.dto;

import com.siac070.SIACProject.model.CatUsuario;
import com.siac070.SIACProject.model.CatEstatusUsuario;

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String telefono;
    private CatUsuario catUsuario;
    private CatEstatusUsuario catEstatusUsuario;

    // Constructor
    public UsuarioDTO(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, String correo, String telefono, CatUsuario catUsuario, CatEstatusUsuario catEstatusUsuario) {
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.catUsuario = catUsuario;
        this.catEstatusUsuario = catEstatusUsuario;
    }

    // Getters y setters
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
