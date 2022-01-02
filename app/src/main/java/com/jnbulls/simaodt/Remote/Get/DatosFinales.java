package com.jnbulls.simaodt.Remote.Get;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jnbulls.simaodt.Data.Entities.*;
import java.util.List;

//Datos Finales es una clase que sirve para traer los datos de la API para llenar los Spinners
public class DatosFinales {
    @SerializedName("materiales")
    @Expose
    private List<Materiales> materiales = null;
    @SerializedName("almacenes")
    @Expose
    private List<Almacenes> almacenes = null;
    @SerializedName("zona")
    @Expose
    private List<Zonas> zonas = null;
    @SerializedName("informes")
    @Expose
    private List<Informes> informes = null;
    @SerializedName("motivos")
    @Expose
    private List<Motivos> motivos = null;
    @SerializedName("users")
    @Expose
    private List<Users> users = null;

    public List<Materiales> getMateriales() {
        return materiales;
    }

    public void setMateriales(List<Materiales> materiales) {
        this.materiales = materiales;
    }

    public List<Almacenes> getAlmacenes() {
        return almacenes;
    }

    public void setAlmacenes(List<Almacenes> almacenes) {
        this.almacenes = almacenes;
    }

    public List<Zonas> getZonas() {
        return zonas;
    }

    public void setZonas(List<Zonas> zona) {
        this.zonas = zona;
    }

    public List<Informes> getInformes() {
        return informes;
    }

    public void setInformes(List<Informes> informes) {
        this.informes = informes;
    }

    public List<Motivos> getMotivos() {
        return motivos;
    }

    public void setMotivos(List<Motivos> motivos) {
        this.motivos = motivos;
    }

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }
}
