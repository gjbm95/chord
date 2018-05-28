package com.Comandos;

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
public abstract class AsyncCommand extends BaseCommand {


    /**
     * Da inicio a la ejecución de un hilo.
     * @param args
     * @param out
     */
    public void ejecutar(final String[] args, final OutputStream out) {
        new Thread(new Runnable() {
            public void run() {
                executeOnBackground(args, out);
            }
        }).start();
    }

    /**
     * Permite crear un metodo abstracto que al implementarse se ejecutará un hilo en paralelo con
     * la ejecucion del programa principal
     * @param args
     * @param out
     */
    public abstract void executeOnBackground(String[] args, OutputStream out);
}
