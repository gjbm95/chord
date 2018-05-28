package com.Comandos;

import com.Entidades.Nodo;
import com.Entidades.NodoRF;

import java.io.OutputStream;
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
public class ConsultarFingerCommand extends BaseCommand{

    public static final String COMMAND_NAME ="listfinger";

    @Override
    public String obtenerNombreComando() {
        return COMMAND_NAME;
    }

    @Override
    public void ejecutar(String[] args, OutputStream out) {
        if (Nodo.getInstancia().getTabla()!=null) {
            Nodo nodo = Nodo.obtenerInstancia();
            System.out.println("Su tabla finger es");
            System.out.println("---------------------------------");
            int i = 1;
            for (Map.Entry<Integer, NodoRF> entry : nodo.getTabla().entrySet()) {
                System.out.println("i: " + entry.getKey() + " Value: " + entry.getValue().getHash().longValue());
            }
        }else {
            System.out.println("Usted es un fantasma!. Esta funcion no esta disponible");
        }
    }
}
