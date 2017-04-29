package com.mcdm.alejandro.myapplication.clases;

/**
 * Created by AlejandroMissael on 11/03/2017.
 */

public class historial_card {

    private String fecha;
    private Double total;
    private Double resto;
    private Integer id;
    private Boolean tipo;

    public historial_card() {
    }

    public historial_card(String fecha, Double total, Double resto) {
        this.fecha = fecha;
        this.total = total;
        this.resto = resto;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public Double getResto() {
        return resto;
    }

    public void setResto(Double resto) {
        this.resto = resto;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getTipo() {
        return tipo;
    }

    public void setTipo(Boolean tipo) {
        this.tipo = tipo;
    }
}
