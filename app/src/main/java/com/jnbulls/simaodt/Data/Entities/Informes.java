package com.jnbulls.simaodt.Data.Entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName="informes")
public class Informes {
    @SerializedName("idinforme")
    @Expose
    @PrimaryKey ()
    @ColumnInfo(name = "idInforme")
    private int id;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    public Informes(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
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

    public String toString(){
        return descripcion;
    }
}

