package com.example.trabajopracticoinmobiliaria.modelo;

import java.io.Serializable;

public class Inmueble implements Serializable {

    private  int id;
    private String direccion;
    private int ambientes;
    private String uso;
    private  String tipo;
    private double precio;
    private boolean estado;
    private String imagen;
    private  int propietarioId;
    private Propietario duenio;

    public Inmueble() {
    }

    public Inmueble(String direccion, int ambientes, String tipo, String uso, double precio, boolean estado, String imagen) {
        this.direccion = direccion;
        this.ambientes = ambientes;
        this.tipo = tipo;
        this.uso = uso;
        this.precio = precio;
        this.estado=estado;
        this.imagen=imagen;
    }
    public Inmueble(int id,String direccion, int ambientes, String tipo, String uso, double precio, boolean estado, String imagen) {
        this.direccion = direccion;
        this.ambientes = ambientes;
        this.tipo = tipo;
        this.uso = uso;
        this.precio = precio;
        this.estado=estado;
        this.imagen=imagen;
        this.id=id;
    }
    public Inmueble(String direccion, int ambientes, String tipo, String uso, double precio, boolean estado) {
        this.direccion = direccion;
        this.ambientes = ambientes;
        this.tipo = tipo;
        this.uso = uso;
        this.precio = precio;
        this.estado=estado;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public boolean getEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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
}