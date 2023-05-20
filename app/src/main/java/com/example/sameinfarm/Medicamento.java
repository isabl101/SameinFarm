package com.example.sameinfarm;

import java.io.Serializable;

public class Medicamento implements Serializable {
    private String cod;
    private String nombre;
    private String dosis;
    private String mg;
    private String mes;
    private String cantidad;

    public Medicamento() {
        // Constructor vacío requerido por Firebase
    }

    public Medicamento(String cod, String nombre, String dosis, String mg, String mes, String cantidad) {
        this.cod = cod;
        this.nombre = nombre;
        this.dosis = dosis;
        this.mg = mg;
        this.mes = mes;
        this.cantidad = cantidad;
    }

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDosis() {
        return dosis;
    }

    public void setDosis(String dosis) {
        this.dosis = dosis;
    }

    public String getMg() {
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public String toString() {
        return "Medicamento{" +
                ", nombre='" + nombre + '\'' +
                ", dosis='" + dosis + '\'' +
                ", mg='" + mg + '\'' +
                ", mes='" + mes + '\'' +
                ", cantidad='" + cantidad + '\'' +
                '}';
    }
}

