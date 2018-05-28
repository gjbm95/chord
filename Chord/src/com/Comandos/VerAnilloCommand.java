package com.Comandos;

import com.Entidades.Fantasma;
import com.Entidades.NodoRF;

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
public class VerAnilloCommand extends BaseCommand {

    public static final String COMMAND_NAME ="listring";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public void ejecutar(String[] args, OutputStream out) {
        System.out.println("El anillo se compone por:  (Longitud: "+Fantasma.obtenerInstancia().getAnillo().size()+" )");
        System.out.println("-----------------------------------------------------------");
        int i =0;
        for (NodoRF nodo : Fantasma.obtenerInstancia().getAnillo()){
            System.out.println(i + "- Direccion: "+ nodo.getDireccion()+" Hash: "+ nodo.getHash()+" Puerto: "+nodo.getPuertopeticion());
        }
    }
}
