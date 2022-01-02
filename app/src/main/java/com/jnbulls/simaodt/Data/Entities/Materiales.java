package com.jnbulls.simaodt.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="materiales")
public class Materiales {
    @PrimaryKey()
    @ColumnInfo(name = "idMaterial")
    @SerializedName("idmateriales")
    @Expose
    private int idMateriales;
    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    public Materiales(int idMateriales, String descripcion) {
        this.idMateriales = idMateriales;
        this.descripcion = descripcion;
    }

    public int getIdMateriales() {
        return idMateriales;
    }

    public void setIdMateriales(int idMateriales) {
        this.idMateriales = idMateriales;
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
