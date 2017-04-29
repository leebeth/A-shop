package com.tienda.a_shop.views;

/**
 * Created by Miguel on 19/10/2014.
 */
public class VentaActivity
{
    private int id;
    private String fecha;
    private String producto;
    private int cantidad;
    private int promocion;

    public VentaActivity(int id, String fecha, String producto, int cantidad, int promocion) {
        this.id = id;
        this.fecha = fecha;
        this.producto = producto;
        this.cantidad = cantidad;
        this.promocion = promocion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public int getPromocion() {
        return promocion;
    }

    public void setPromocion(int promocion) {
        this.promocion = promocion;
    }

    public String toString()
    {
        return id + " " + fecha + " " + producto + " " + cantidad + " " + promocion;
    }
}
