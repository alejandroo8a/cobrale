package com.mcdm.alejandro.myapplication.clases;

/**
 * Created by Tammy on 07/03/2017.
 */

public class lugarRopa {
    private Integer id;
    private String nombre;
    private boolean sincronizado;

    public lugarRopa(Integer id, String nombre, boolean sincronizado) {
        this.id = id;
        this.nombre = nombre;
        this.sincronizado = sincronizado;
    }

    public lugarRopa() {
    }

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

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }
}
