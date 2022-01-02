package com.jnbulls.simaodt.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "motivos")
public class Motivos {

    @PrimaryKey()
    @ColumnInfo(name = "idMotivo")
    @SerializedName("idmotivos_reparacion")
    @Expose
    private int idMotivos;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    public Motivos(int idMotivos, String descripcion) {
        this.idMotivos = idMotivos;
        this.descripcion = descripcion;
    }

    public int getIdMotivos() {
        return idMotivos;
    }

    public void setIdMotivos(int idMotivos) {
        this.idMotivos = idMotivos;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString(){
        return descripcion;
    }
}

