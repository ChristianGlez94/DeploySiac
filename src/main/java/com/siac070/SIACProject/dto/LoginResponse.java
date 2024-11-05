package com.siac070.SIACProject.dto;

import com.siac070.SIACProject.model.CatUsuario;
import com.siac070.SIACProject.model.CatEstatusUsuario;

public class LoginResponse {

    private Long id;
    private String nombre;
    private String apellidoPaterno;
    private String apellidoMaterno;
    private String correo;
    private String telefono;
    private CatUsuario catUsuario;
    private CatEstatusUsuario catEstatusUsuario;
    private String token;
    private String message;
    private Long dependenciaId; // Nuevo campo para el ID de la dependencia
    private String dependenciaNombre; // Nuevo campo para el nombre de la dependencia

    // Constructor
    public LoginResponse(Long id, String nombre, String apellidoPaterno, String apellidoMaterno, 
                         String correo, String telefono, CatUsuario catUsuario, 
                         CatEstatusUsuario catEstatusUsuario, String token, 
                         String message, Long dependenciaId, String dependenciaNombre) { // Añadir dependenciaId y dependenciaNombre
        this.id = id;
        this.nombre = nombre;
        this.apellidoPaterno = apellidoPaterno;
        this.apellidoMaterno = apellidoMaterno;
        this.correo = correo;
        this.telefono = telefono;
        this.catUsuario = catUsuario;
        this.catEstatusUsuario = catEstatusUsuario;
        this.token = token;
        this.message = message;
        this.dependenciaId = dependenciaId; // Asignar dependenciaId
        this.dependenciaNombre = dependenciaNombre; // Asignar dependenciaNombre
    }

    // Getters y setters para 'dependenciaId' y 'dependenciaNombre'
    public Long getDependenciaId() {
        return dependenciaId;
    }

    public void setDependenciaId(Long dependenciaId) {
        this.dependenciaId = dependenciaId;
    }

    public String getDependenciaNombre() {
        return dependenciaNombre;
    }

    public void setDependenciaNombre(String dependenciaNombre) {
        this.dependenciaNombre = dependenciaNombre;
    }

    // Otros getters y setters
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
