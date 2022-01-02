package com.jnbulls.simaodt.Data.Entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName="odt_entity")
public class OdtEntity {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private int numeroOdt;
    private String numeroReclamo;
    private String fotoUrl;
    private String fecha;
    private String estado;

    public OdtEntity(int numeroOdt, String numeroReclamo, String fotoUrl, String fecha, String estado){
        this.numeroOdt = numeroOdt;
        this.numeroReclamo = numeroReclamo;
        this.fotoUrl = fotoUrl;
        this.fecha = fecha;
        this.estado = estado;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumeroOdt() {
        return numeroOdt;
    }

    public void setNumeroOdt(int numeroOdt) {
        this.numeroOdt = numeroOdt;
    }

    public String getNumeroReclamo() {
        return numeroReclamo;
    }

    public void setNumeroReclamo(String numeroReclamo) {
        this.numeroReclamo = numeroReclamo;
    }

    public String getFotoUrl() {
        return fotoUrl;
    }

    public void setFotoUrl(String fotoUrl) {
        this.fotoUrl = fotoUrl;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
