package com.example.trabajopracticoinmobiliaria.modelo;

import java.io.Serializable;

public class Inmueble implements Serializable {

    private  int id;
    private String direccion;
    private String uso;
    private  String tipo;
    private int ambientes;
    private int superficie;
    private double valor;
    private String imagen;
    private boolean disponible;
    private  int propietarioId;
    private Propietario duenio;
    public Inmueble() {
    }

    public Inmueble(String direccion, int ambientes, String tipo, String uso, double precio, boolean disponible, String imagen) {
        this.direccion = direccion;
        this.ambientes = ambientes;
        this.tipo = tipo;
        this.uso = uso;
        this.valor = precio;
        this.disponible=disponible;
        this.imagen=imagen;
    }
    public Inmueble(int id,String direccion, int ambientes, String tipo, String uso, double valor, boolean disponible, String imagen) {
        this.direccion = direccion;
        this.ambientes = ambientes;
        this.tipo = tipo;
        this.uso = uso;
        this.valor = valor;
        this.disponible=disponible;
        this.imagen=imagen;
        this.id=id;
    }
    public Inmueble(String direccion, int ambientes, String tipo, String uso, double valor, boolean disponible) {
        this.direccion = direccion;
        this.ambientes = ambientes;
        this.tipo = tipo;
        this.uso = uso;
        this.valor = valor;
        this.disponible=disponible;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getAmbientes() {
        return ambientes;
    }

    public void setAmbientes(int ambientes) {
        this.ambientes = ambientes;
    }

    public String getUso() {
        return uso;
    }

    public void setUso(String uso) {
        this.uso = uso;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getPropietarioId() {
        return propietarioId;
    }

    public void setPropietarioId(int propietarioId) {
        this.propietarioId = propietarioId;
    }

    public Propietario getDuenio() {
        return duenio;
    }

    public void setDuenio(Propietario duenio) {
        this.duenio = duenio;
    }

    public int getSuperficie() {
        return superficie;
    }

    public void setSuperficie(int superficie) {
        this.superficie = superficie;
    }

}