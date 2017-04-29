package com.mcdm.alejandro.myapplication.clases;

/**
 * Created by alejandro on 8/12/16.
 */

public class ventas {
    private Integer idVenta;
    private Integer idCliente;
    private Integer idProductos;
    private Integer idPagos;
    private String fechaVenta;
    private String prendasTotal;
    private String plazo;
    private String diaSemana;
    private double total;
    private boolean pagado;
    private boolean sincronizado;


    public ventas(){

    }

    public ventas(Integer idVenta, Integer idCliente, Integer idProductos, Integer idPagos, String fechaVenta, String prendasTotal, String plazo, String diaSemana, double total, boolean pagado, boolean sincronizado) {
        this.idVenta = idVenta;
        this.idCliente = idCliente;
        this.idProductos = idProductos;
        this.idPagos = idPagos;
        this.fechaVenta = fechaVenta;
        this.prendasTotal = prendasTotal;
        this.plazo = plazo;
        this.diaSemana = diaSemana;
        this.total = total;
        this.pagado = pagado;
        this.sincronizado = sincronizado;
    }

    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public Integer getIdProductos() {
        return idProductos;
    }

    public void setIdProductos(Integer idProductos) {
        this.idProductos = idProductos;
    }

    public Integer getIdPagos() {
        return idPagos;
    }

    public void setIdPagos(Integer idPagos) {
        this.idPagos = idPagos;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getPrendasTotal() {
        return prendasTotal;
    }

    public void setPrendasTotal(String prendasTotal) {
        this.prendasTotal = prendasTotal;
    }

    public String getPlazo() {
        return plazo;
    }

    public void setPlazo(String plazo) {
        this.plazo = plazo;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public boolean isPagado() {
        return pagado;
    }

    public void setPagado(boolean pagado) {
        this.pagado = pagado;
    }

    public boolean isSincronizado() {
        return sincronizado;
    }

    public void setSincronizado(boolean sincronizado) {
        this.sincronizado = sincronizado;
    }
}
