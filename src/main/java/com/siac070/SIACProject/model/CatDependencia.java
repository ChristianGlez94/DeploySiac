package com.siac070.SIACProject.model;

import jakarta.persistence.*;

@Entity
@Table(name = "cat_dependencias")
public class CatDependencia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "nombre", length = 255)
    private String nombre;
   
    @Column(name = "contacto", length = 255)
    private String contacto;
    

    // Getters y Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
    }

    public String getContacto() {
        return contacto;
    }

   
}
