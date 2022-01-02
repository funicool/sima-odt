package com.jnbulls.simaodt.Remote.Post;

import java.util.HashMap;

public class DetallesOdt {
    private int informe;
    private int zona;
    private int motivo;
    private int odt;
    private String estado;
    private HashMap<Integer, Integer> materiales;

    public DetallesOdt(){
        materiales=new HashMap<Integer, Integer>();
    }

    public int getInforme() {
        return informe;
    }

    public void setInforme(int informe) {
        this.informe = informe;
    }

    public int getOdt() {
        return odt;
    }

    public void setOdt(int odt) {
        this.odt = odt;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getZona() {
        return zona;
    }

    public void setZona(int zona) {
        this.zona = zona;
    }

    public int getMotivo() {
        return motivo;
    }

    public void setMotivo(int motivo) {
        this.motivo = motivo;
    }

    public HashMap<Integer, Integer> getMateriales() {
        return materiales;
    }

    public void setMateriales(HashMap<Integer, Integer> materiales) {
        this.materiales = materiales;
    }

    public void addMateriales(int key, int value){
        materiales.put(key, value);
    }

    public void removeMateriales(int key){
        materiales.remove(key);
    }
}
