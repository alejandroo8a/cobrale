package com.mcdm.alejandro.myapplication.clases;

/**
 * Created by alejandro on 29/12/16.
 */

public class DEBEN {

    private Integer id;
    private String nombre;
    private String fecha;
    private String resto;
    private Double total;

    public DEBEN() {
    }

    public DEBEN(Integer id,String nombre, String fecha, String resto, Double total) {
        this.id= id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.resto = resto;
        this.total = total;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getResto() {
        return resto;
    }

    public void setResto(String resto) {
        this.resto = resto;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
