package com.Comandos;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Descargas;
import com.ControladoresRed.Mensaje;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;
import com.Entidades.Recurso;
import com.Utils.LoggerUtil;
import com.Utils.RespuestaUtils;
import com.Utils.SistemaUtil;
import static com.Utils.SistemaUtil.obtenerHora;

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
                             LoggerUtil.obtenerInstancia().Log("Buscando Recurso "+Nodo.getInstancia().getDireccion()+" tiempo: "+obtenerHora());
                             SistemaUtil.reportarTiempo(COMMAND_NAME, "inicio", new NodoRF(Nodo.getInstancia().getDireccion(),Nodo.getInstancia().getPuertopeticion())); 
            //Obtiene la IP y Descarga el archivo
            if (hash > Nodo.obtenerInstancia().getHash().longValue()) {
                Nodo.getInstancia().setSolicitante(true);
                Mensaje mensaje = new Mensaje("who", hash, Nodo.getInstancia(), nodo);
                ArrayList<Nodo> duenos = (ArrayList<Nodo>)new ConexionUtils().enviarMensaje(mensaje);
                if (!duenos.isEmpty()) {
                    LoggerUtil.obtenerInstancia().Log("Recurso Encontrado "+Nodo.getInstancia().getDireccion()+" tiempo: "+obtenerHora());
                    SistemaUtil.reportarTiempo(COMMAND_NAME, "final", new NodoRF(Nodo.getInstancia().getDireccion(),Nodo.getInstancia().getPuertopeticion()));
                    new Descargas(duenos, args[0],hash).start();
                    //EjecutarComando.linea("download " + duenos.getDireccion() + " " + duenos.getPuertopeticion() + " " + hash);
                } else {
                    if(!busquedaInterna(args[0],hash)){
                      System.out.println("Archivo no encontrado");
                      SistemaUtil.reportarTiempo(COMMAND_NAME, "final", new NodoRF(Nodo.getInstancia().getDireccion(),Nodo.getInstancia().getPuertopeticion()));
                    }
                }
            }else{
                Nodo.getInstancia().setSolicitante(true);
                NodoRF primero = (NodoRF)new ConexionUtils().enviarMensaje(new Mensaje("first", Fantasma.obtenerInstancia()));
                ArrayList <Nodo> duenos  = (ArrayList<Nodo>)new ConexionUtils().enviarMensaje(new Mensaje("who",hash,
                        Nodo.getInstancia(), primero));
                if (!duenos.isEmpty()) {
                    SistemaUtil.reportarTiempo(COMMAND_NAME, "final", new NodoRF(Nodo.getInstancia().getDireccion(),Nodo.getInstancia().getPuertopeticion()));
                    new Descargas(duenos, args[0],hash).start();
                   // EjecutarComando.linea("download " + duenos.getDireccion() + " " + duenos.getPuertopeticion() + " " + hash);
                } else {
                    if(!busquedaInterna(args[0],hash)){
                        SistemaUtil.reportarTiempo(COMMAND_NAME, "final", new NodoRF(Nodo.getInstancia().getDireccion(),Nodo.getInstancia().getPuertopeticion()));
                      System.out.println("Archivo no encontrado");
                    }
                }
                SistemaUtil.reportarTiempo(COMMAND_NAME, "final", new NodoRF(Nodo.getInstancia().getDireccion(),Nodo.getInstancia().getPuertopeticion()));
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
