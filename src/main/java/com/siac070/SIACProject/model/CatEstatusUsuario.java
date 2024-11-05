package com.siac070.SIACProject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cat_estatus_usuarios")
public class CatEstatusUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "estatus", length = 255, nullable = false)
    private String estatus;

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }
}
