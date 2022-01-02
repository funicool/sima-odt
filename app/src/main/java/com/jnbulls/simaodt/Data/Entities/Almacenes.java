package com.jnbulls.simaodt.Data.Entities;
import androidx.room.Entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "almacenes")
public class Almacenes {

        @SerializedName("idalmacenes")
        @Expose
        private int idAlmacenes;
        @SerializedName("password")
        @Expose
        private String nombre;

        public int getIdalmacenes() {
            return idAlmacenes;
        }

        public void setIdalmacenes(int idalmacenes) {
            this.idAlmacenes = idalmacenes;
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
