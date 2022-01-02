package com.jnbulls.simaodt.Data.Entities;

import android.util.Log;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity (tableName = "detalles_odt")
public class DetallesOdtEntity {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idDetalleOdt")
    private int id;
    private int relaInforme;
    private int relaZona;
    private int relaMotivo;
    private int numeroOdt;
    private String estado;
    private String listaMateriales;
    private String fecha;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRelaInforme() {
        return relaInforme;
    }

    public void setRelaInforme(int relaInforme) {
        this.relaInforme = relaInforme;
    }

    public int getRelaZona() {
        return relaZona;
    }

    public void setRelaZona(int relaZona) {
        this.relaZona = relaZona;
    }

    public int getRelaMotivo() {
        return relaMotivo;
    }

    public void setRelaMotivo(int relaMotivo) {
        this.relaMotivo = relaMotivo;
    }

    public int getNumeroOdt() {
        return numeroOdt;
    }

    public void setNumeroOdt(int numeroOdt) {
        this.numeroOdt = numeroOdt;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getListaMateriales() {
        return listaMateriales;
    }

    public void addListaMateriales(String listaMateriales){
        this.listaMateriales=this.listaMateriales+","+listaMateriales;
    }

    public void setListaMateriales(String listaMateriales) {
        this.listaMateriales = listaMateriales;
    }

    public void deleteListaMateriales(int position){
        String[] splitList = this.listaMateriales.split(",");
        StringBuilder newList = new StringBuilder();
        Log.d("Posicion", "parámetro: "+position);

        for(int i=0; i<splitList.length;i++){
            Log.d("Posicion: " + i, "Valor: "+splitList[i]);
            if (i == position) {
                continue;
            }
            if ((i==splitList.length-1)||(splitList.length==2)){
                newList.append(splitList[i]);
            }else {
                newList.append(splitList[i]).append(",");
            }
            Log.d("Lista parcial", i+ " : "+newList.toString() );
        }

        Log.d("Lista Final", "Lista: "+ newList.toString());
        listaMateriales=newList.toString().replaceAll(",$", "");
    }

    public String getFecha() {
        return fecha;
    }
    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
