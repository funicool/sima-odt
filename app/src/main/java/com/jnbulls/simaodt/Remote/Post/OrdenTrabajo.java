package com.jnbulls.simaodt.Remote.Post;

import java.io.File;
import java.util.GregorianCalendar;

public class OrdenTrabajo {
    private int orden_trabajo;
    private String nro_reclamo;
    private File foto;
    private GregorianCalendar fechahora;
    private String estado;

    public int getOrden_trabajo() {
        return orden_trabajo;
    }

    public void setOrden_trabajo(int orden_trabajo) {
        this.orden_trabajo = orden_trabajo;
    }

    public String getNro_reclamo() {
        return nro_reclamo;
    }

    public void setNro_reclamo(String nro_reclamo) {
        this.nro_reclamo = nro_reclamo;
    }

    public File getFoto() {
        return foto;
    }

    public void setFoto(File foto) {
        this.foto = foto;
    }

    public GregorianCalendar getFechahora() {
        return fechahora;
    }

    public void setFechahora(GregorianCalendar fechahora) {
        this.fechahora = fechahora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
