package com.jnbulls.simaodt.UI.Adapters;

import com.jnbulls.simaodt.Data.Entities.Materiales;

public class MaterialesItem {
    private int id;
    private String descripcion;
    private int cantidad;

    public MaterialesItem(int id, String descripcion, int cantidad){
        this.id = id;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String makeString(){
        return id+"_"+cantidad;
    }


}
