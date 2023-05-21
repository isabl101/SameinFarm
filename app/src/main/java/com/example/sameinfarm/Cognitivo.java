package com.example.sameinfarm;

import java.io.Serializable;

public class Cognitivo implements Serializable  {
    private String fecha;
    private String situacion;
    private String pensamiento;
    private String emocion;
    private String accion;

    public Cognitivo() {
        // Constructor vacío requerido por Firebase
    }

    public Cognitivo(String fecha, String situacion, String pensamiento, String emocion, String accion) {
        this.fecha = fecha;
        this.situacion = situacion;
        this.pensamiento = pensamiento;
        this.emocion = emocion;
        this.accion = accion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getSituacion() {
        return situacion;
    }

    public void setSituacion(String situacion) {
        this.situacion = situacion;
    }

    public String getPensamiento() {
        return pensamiento;
    }

    public void setPensamiento(String pensamiento) {
        this.pensamiento = pensamiento;
    }

    public String getEmocion() {
        return emocion;
    }

    public void setEmocion(String emocion) {
        this.emocion = emocion;
    }

    public String getAccion() {
        return accion;
    }

    public void setAccion(String accion) {
        this.accion = accion;
    }


    @Override
    public String toString() {
        return "REGISTRO" +
                "\nFECHA: " + fecha
                ;
    }
}
