package com.Comandos;

import com.ControladoresRed.ConexionUtils;
import com.ControladoresRed.Mensaje;
import com.Entidades.Fantasma;
import com.Entidades.Nodo;
import com.Entidades.NodoRF;
import com.Entidades.Recurso;

import java.io.OutputStream;

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
public class InformarRecursosCommand extends AsyncCommand {

    public static final String COMMAND_NAME="share";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }


    @Override
    public void executeOnBackground(String[] args, OutputStream out) {
        for(Recurso recurso : Nodo.getInstancia().getRecursos()) {
            if (recurso.getHash().longValue() > Nodo.obtenerInstancia().getHash().longValue()) {
                NodoRF node = Nodo.obtenerInstancia().seleccionarNodo(recurso.getHash().longValue());
                //Obtiene la IP y Descarga el archivo
                Nodo.getInstancia().setSolicitante(true);
                ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("resource", recurso.getHash(),
                        Nodo.getInstancia(), node));
            }
            else
            {
                Nodo.getInstancia().setSolicitante(true);
                NodoRF primero = (NodoRF) ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("first"
                        , Fantasma.obtenerInstancia()));
                ConexionUtils.obtenerInstancia().enviarMensaje(new Mensaje("resource", recurso.getHash(),
                        Nodo.getInstancia(), primero));
            }

        }
    }
}
