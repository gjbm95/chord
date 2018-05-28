package com.Entidades;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
public class Nodo extends Miembro implements Serializable {
     private int puertoArchivo;
     private ArrayList<Recurso> cola = new ArrayList<Recurso>();
     private ArrayList<Recurso> recursos = new ArrayList<Recurso>();
     private HashMap<Integer,NodoRF> tablafinger = new HashMap<Integer,NodoRF>();
     private HashMap<Nodo,Long> tablaRecursos = new HashMap<Nodo,Long>();
     private static Nodo instancia;
     private Long enespera;
     private String redireccion;
     private boolean solicitante;
     private boolean compartir=false;

    private Nodo(){

    }

    public static Nodo obtenerInstancia(){
        if (instancia == null)
            instancia = new Nodo();
        return instancia;
    }

    public int getPuertoArchivo() {
        return puertoArchivo;
    }

    public void setPuertoArchivo(int puertoArchivo) {
        this.puertoArchivo = puertoArchivo;
    }

    public ArrayList<Recurso> getCola() {
        return cola;
    }

    public void setCola(ArrayList<Recurso> cola) {
        this.cola = cola;
    }

    public ArrayList<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(ArrayList<Recurso> recursos) {
        this.recursos = recursos;
    }

    public HashMap<Integer,NodoRF> getTabla() {
        return tablafinger;
    }

    public void setTabla(HashMap<Integer,NodoRF> tabla) {
        this.tablafinger = tabla;
    }

    public HashMap<Nodo, Long> getTablaRecursos() {
        return tablaRecursos;
    }

    public void setTablaRecursos(HashMap<Nodo, Long> tablaRecursos) {
        this.tablaRecursos = tablaRecursos;
    }

    public static Nodo getInstancia() {
        return instancia;
    }

    public static void setInstancia(Nodo instancia) {
        Nodo.instancia = instancia;
    }

    public String getRedireccion() {
        return redireccion;
    }

    public void setRedireccion(String redireccion) {
        this.redireccion = redireccion;
    }

    public boolean isSolicitante() {
        return solicitante;
    }

    public void setSolicitante(boolean solicitante) {
        this.solicitante = solicitante;
    }

    public boolean isCompartir() {
        return compartir;
    }

    public void setCompartir(boolean compartir) {
        this.compartir = compartir;
    }

    /**
     * Metodo que se encarga de devolver los datos de un nodo determinado dato el identificador
     * en hash de un archivo. Este revisa en la tabla finger quien es el nodo que es probable
     * que tenga el archivo.
     * @param archivohash aplicando funcion hash
     * @return none (si no encontro alguno) o la IP (si lo encuentra)
     */
    public NodoRF seleccionarNodo(Long archivohash){
        NodoRF respuesta = null;
        Nodo nodo = Nodo.obtenerInstancia();
        HashMap<Integer,NodoRF> tabla = nodo.getTabla();

        if (tabla!=null) {
            for (NodoRF item : tabla.values()) {
                if (archivohash < Math.abs(item.getHash().longValue())) {
                    respuesta = item;
                }
            }
            if (respuesta == null) {
                respuesta = tabla.get(tabla.size());
            }
        }else
            System.out.println("Su tabla finger no se ha generado");
        return respuesta;
    }

    public ArrayList<Nodo> tieneRecurso(Long archivohash){
       ArrayList<Nodo> respuesta = new ArrayList<Nodo>();

        for (Map.Entry<Nodo, Long> entry : this.getTablaRecursos().entrySet()) {
           if (entry.getValue().equals(archivohash))
            respuesta.add(entry.getKey());
        }
        return respuesta;
    }

    public Recurso buscarRecurso(Long archivohash){

        for (Recurso recurso : recursos){
            Long hash = recurso.getHash().longValue();

            if(archivohash.equals(hash)){
                return recurso;
            }

        }
        return null;
    }

    public Recurso buscarRecurso(String archivo){
        for (Recurso recurso : recursos){
            if(archivo.equals(recurso.getNombre()))
                return recurso;
        }
        return null;
    }

    public void agregarRecurso(Nodo nodo, Long valor){
        boolean existe = false;
        for (Map.Entry<Nodo, Long> entry : this.getTablaRecursos().entrySet()) {
            if ((entry.getKey().getDireccion().equals(nodo.getDireccion()))
                    &&(entry.getKey().getPuertopeticion()==(nodo.getPuertopeticion()))
                    &&(entry.getValue().equals(valor)))
                existe = true;
        }

        if (!existe)
        this.tablaRecursos.put(nodo,valor);
    }

    public HashMap<Integer, NodoRF> getTablafinger() {
        return tablafinger;
    }

    public void setTablafinger(HashMap<Integer, NodoRF> tablafinger) {
        this.tablafinger = tablafinger;
    }

    public Long getEnespera() {
        return enespera;
    }

    public void setEnespera(Long enespera) {
        this.enespera = enespera;
    }
}
