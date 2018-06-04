/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.Entidades;

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
public class Estadistica {
    private static int nodos_estables;
    private static int nodos_caidos;
    private static int tablas_generadas; 
    private static int tiempo_tablas; 
    private static int descargas_exitosas; 
    
    public static void add_nodos(){
      nodos_estables++;
    }
    
    public static void add_caidos(){
       nodos_caidos++;
    }
    
    public static void add_tablas(){
       tablas_generadas++;
    }
    
    public static void add_exitosas(){
       descargas_exitosas++;
    }


    public static int getNodos_estables() {
        return nodos_estables;
    }

    public static void setNodos_estables(int nodos_estables) {
        Estadistica.nodos_estables = nodos_estables;
    }

    public static int getNodos_caidos() {
        return nodos_caidos;
    }

    public static void setNodos_caidos(int nodos_caidos) {
        Estadistica.nodos_caidos = nodos_caidos;
    }

    public static int getTablas_generadas() {
        return tablas_generadas;
    }

    public static void setTablas_generadas(int tablas_generadas) {
        Estadistica.tablas_generadas = tablas_generadas;
    }

    public static int getTiempo_tablas() {
        return tiempo_tablas;
    }

    public static void setTiempo_tablas(int tiempo_tablas) {
        Estadistica.tiempo_tablas = tiempo_tablas;
    }

    public static int getDescargas_exitosas() {
        return descargas_exitosas;
    }

    public static void setDescargas_exitosas(int descargas_exitosas) {
        Estadistica.descargas_exitosas = descargas_exitosas;
    }
    
    
    
}
