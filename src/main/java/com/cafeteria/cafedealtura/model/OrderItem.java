package com.cafeteria.cafedealtura.model;

public class OrderItem {
    private Long idCafe;
    private String nombre;
    private double precio;
    private int cantidad;
    private double subtotal;

    public OrderItem() {}

    public OrderItem(Long idCafe, String nombre, double precio, int cantidad) {
        this.idCafe = idCafe;
        this.nombre = nombre;
        this.precio = precio;
        this.cantidad = cantidad;
        this.subtotal = precio * cantidad;
    }

    public Long getIdCafe() {
        return idCafe;
    }

    public void setIdCafe(Long idCafe) {
        this.idCafe = idCafe;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }
}
