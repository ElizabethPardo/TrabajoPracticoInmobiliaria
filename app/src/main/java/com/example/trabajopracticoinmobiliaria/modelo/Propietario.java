package com.example.trabajopracticoinmobiliaria.modelo;

import java.io.Serializable;

public class Propietario implements Serializable {

    private int id;
    private  String nombre;
    private String apellido;
    private String dni;
    private String telefono;
    private String email;
    private String clave;
    private String avatar;

    private CambioClaveView camPass;
    public Propietario() {
    }

    public Propietario(int id, String nombre, String apellido, String dni, String telefono, String email, String clave, String avatar) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
        this.avatar = avatar;
    }

    public Propietario(int id, String nombre, String apellido, String dni,String telefono, String email, String clave) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
    }

    public Propietario(String nombre, String apellido, String dni,String telefono, String email, String clave) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.clave = clave;
    }

    public CambioClaveView getCamPass() {
        return camPass;
    }

    public void setCamPass(CambioClaveView camPass) {
        this.camPass = camPass;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Propietario(int id, String nombre, String apellido, String dni, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }


    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }
}
