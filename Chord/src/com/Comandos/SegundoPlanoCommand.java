package com.Comandos;

import com.Entidades.Nodo;

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
public class SegundoPlanoCommand extends AsyncCommand {

    public static final String COMMAND_NAME="secondplane";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public void executeOnBackground(String[] args, OutputStream out) {
        //while (true){
        //    if(Nodo.getInstancia().isCompartir()){
        //        //EjecutarComando.linea("share");
        //        Nodo.getInstancia().setCompartir(false);
        //    }
       // }
    }
}
