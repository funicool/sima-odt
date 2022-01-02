package com.jnbulls.simaodt.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "zonas")
public class Zonas {
    @PrimaryKey()
    @ColumnInfo(name = "idZona")
    @SerializedName("idzonas")
    @Expose
    private int idZonas;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    public Zonas(int idZonas, String nombre){
        this.idZonas=idZonas;
        this.nombre=nombre;
    }

    public int getIdZonas() {
        return idZonas;
    }

    public void setIdZonas(Integer idZonas) {
        this.idZonas = idZonas;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String toString(){
        return nombre;
    }
}
