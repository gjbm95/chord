package com.Comandos;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Descargas;
import com.ControladoresRed.Mensaje;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;
import com.Entidades.Recurso;
import com.Utils.RespuestaUtils;

import java.io.OutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

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
public class BuscarRecursoCommand extends BaseCommand{

    public static final String COMMAND_NAME = "search";


    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public synchronized void ejecutar(String[] args, OutputStream out) {
        try {
            Long hash = RespuestaUtils.generarHash(args[0]).longValue();
            NodoRF nodo = Nodo.obtenerInstancia().seleccionarNodo(hash);
            //Obtiene la IP y Descarga el archivo
            if (hash > Nodo.obtenerInstancia().getHash().longValue()) {
                Nodo.getInstancia().setSolicitante(true);
                Mensaje mensaje = new Mensaje("who", hash, Nodo.getInstancia(), nodo);
                ArrayList<Nodo> duenos = (ArrayList<Nodo>) ConexionUtils.obtenerInstancia().enviarMensaje(mensaje);
                if (!duenos.isEmpty()) {
                    new Descargas(duenos, args[0],hash).start();
                    //EjecutarComando.linea("download " + duenos.getDireccion() + " " + duenos.getPuertopeticion() + " " + hash);
                } else {
                    if(!busquedaInterna(args[0],hash)){
                      System.out.println("Archivo no encontrado");
                    }
                }
            }else{
                Nodo.getInstancia().setSolicitante(true);
                NodoRF primero = (NodoRF) ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("first", Fantasma.obtenerInstancia()));
                ArrayList <Nodo> duenos  = (ArrayList<Nodo>) ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("who",hash,
                        Nodo.getInstancia(), primero));
                if (!duenos.isEmpty()) {
                    new Descargas(duenos, args[0],hash).start();
                   // EjecutarComando.linea("download " + duenos.getDireccion() + " " + duenos.getPuertopeticion() + " " + hash);
                } else {
                    if(!busquedaInterna(args[0],hash)){
                      System.out.println("Archivo no encontrado");
                    }
                }
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
    
    public boolean busquedaInterna(String nombre,Long hash){
      Recurso recurso = Nodo.getInstancia().buscarRecurso(nombre);
      ArrayList<Nodo> lista = new ArrayList<Nodo>();
      lista.add(Nodo.getInstancia());
      if (recurso != null) {
        new Descargas(lista,nombre,hash).start();
        return true; 
      }
      return false; 
    }
}
