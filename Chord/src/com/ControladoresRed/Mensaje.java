package com.ControladoresRed;


import com.Entidades.Miembro;

import java.io.Serializable;

/**
 * Universidad Catolica Andres Bello
 * Facultad de Ingenieria
 * Escuela de Ingenieria Informatica
 * Trabajo Especial de Grado
 * ----------------------------------
 * Tutor:
 * --------------
 * Wilmer Pereira
 *
 * Autores:
 * --------------
 * Garry Bruno
 * Carlos Valero
 */
public class Mensaje implements Serializable {

    private String funcion;
    private Object data;
    private Miembro origen;
    private Miembro destino;

    public Mensaje(String funcion, Object data, Miembro destino) {
        this.funcion = funcion;
        this.data = data;
        this.destino = destino;
    }

    public Mensaje(String funcion, Object data,Miembro origen, Miembro destino) {
        this.funcion = funcion;
        this.data = data;
        this.destino = destino;
        this.origen = origen;
    }
    public Mensaje (String funcion, Miembro destino){
        this.funcion=funcion;
        this.destino=destino;
        this.data=null;

    }

    public String getFuncion() {
        return funcion;
    }

    public void setFuncion(String funcion) {
        this.funcion = funcion;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Miembro getDestino() {
        return destino;
    }

    public void setDestino(Miembro destino) {
        this.destino = destino;
    }

    public Miembro getOrigen() {
        return origen;
    }

    public void setOrigen(Miembro origen) {
        this.origen = origen;
    }
}
