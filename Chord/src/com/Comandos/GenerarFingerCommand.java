package com.Comandos;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Mensaje;
import com.Entidades.Estadistica;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
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
public class GenerarFingerCommand extends BaseCommand {

    public static final String COMMAND_NAME = "generarFinger";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public synchronized void ejecutar(String[] args, OutputStream out) {
        Fantasma f = Fantasma.obtenerInstancia();
        try {
            if (!f.getAnillo().isEmpty()) {
                ArrayList<NodoRF> anillo = f.getAnillo();
                for (NodoRF nodo : anillo) {
                    Estadistica.add_tablas();
                    new ConexionUtils().enviarMensaje(new Mensaje("addtable", anillo, nodo));
                }
            }
        }catch(ConcurrentModificationException e){
        
        }
    }
}
