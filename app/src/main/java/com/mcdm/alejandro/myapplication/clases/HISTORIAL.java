package com.mcdm.alejandro.myapplication.clases;

import java.util.ArrayList;

/**
 * Created by alejandro on 10/01/17.
 */

public class HISTORIAL {

    private Integer id;
    private String nombre;

    public HISTORIAL(){

    }

    public HISTORIAL(Integer id, String nombre, ArrayList<Integer> compras) {
        this.id = id;
        this.nombre = nombre;
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
}
