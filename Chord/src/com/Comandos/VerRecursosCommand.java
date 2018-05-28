package com.Comandos;

import com.Entidades.Nodo;
import com.Entidades.Recurso;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

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
public class VerRecursosCommand extends BaseCommand {

    public static final String COMMAND_NAME="listresources";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public void ejecutar(String[] args, OutputStream out) {
        System.out.println("Sus recursos son:");
        System.out.println("--------------------------------------------------------");
        for(Recurso r: Nodo.obtenerInstancia().getRecursos()){
            System.out.println("Nombre: "+ r.getNombre() + " Hash:" + r.getHash());
        }

    }
}
