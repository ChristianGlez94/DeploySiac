package com.siac070.SIACProject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "trabajadores_dependencias")
public class TrabajadorDependencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "id_usuarios")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_cat_dependencia")
    private CatDependencia dependencia;

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public CatDependencia getDependencia() {
        return dependencia;
    }

    public void setDependencia(CatDependencia dependencia) {
        this.dependencia = dependencia;
    }
}
